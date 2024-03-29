/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.ense701.gameModel;

import java.io.FileReader;
import java.io.Reader;
import java.util.*;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.io.IOException;

import nz.ac.aut.ense701.gameModel.jsonModels.*;

import static nz.ac.aut.ense701.gameModel.utils.OccupantsDuplicator.duplicateMulti;
import static nz.ac.aut.ense701.gameModel.utils.OccupantsDuplicator.duplicate;

/**
 * A JSON Implementation of IDataManager.
 *
 * @author Yuan
 */
public class JsonProcessor implements IDataManager {

    private Map<String, Occupant> occupantsDictionary;
    private List<JsonOccupantsPosition> jsonOccupantsMap;
    private List<Occupant> allOccupantInstances;

    private JsonProcessor(Map<String, Occupant> occupantsDictionary,
                          List<JsonOccupantsPosition> jsonOccupantsMap, List<Occupant> allOccupantInstances) {
        this.occupantsDictionary = occupantsDictionary;
        this.jsonOccupantsMap = jsonOccupantsMap;
        this.allOccupantInstances = allOccupantInstances;
    }

    /**
     * Static factory of JsonProcessor.
     *
     * @param occupantsFilePath     the file path of Occupants.json
     * @param occupantsMapFilePath  the file path of OccupantsMap.json
     * @param occupantsPoolFilePath the file path of OccupantsPool.json
     * @return an instance of IDataManager.
     * @throws IllegalArgumentException if any of the arguments is {@code null} or empty.
     * @throws IllegalStateException    if the read data from local file failed; or if the data integrity of any of
     *                                  the Json files is corrupted; currently this only supports non-primitive type fileds.
     */
    public static IDataManager make(String occupantsFilePath, String occupantsMapFilePath,
                                    String occupantsPoolFilePath) {
        if (occupantsFilePath == null || occupantsMapFilePath == null
                || occupantsFilePath.isEmpty() || occupantsMapFilePath.isEmpty())
            throw new IllegalArgumentException(
                    "File paths for persistent data cannot be null or empty.");

        Gson gson = new Gson();
        Map<String, Occupant> od = loadOccupantsDictionary(gson, occupantsFilePath);
        List<JsonOccupantsPosition> jom = loadOccupantsPosition(gson, occupantsMapFilePath);

        if (od == null || jom == null)
            throw new IllegalStateException("Cannot read data from local storage.");

        List<Occupant> aoi = loadAllOccupantInstances(gson, od, occupantsPoolFilePath);
        if (aoi == null)
            throw new IllegalStateException("Cannot read data from local storage.");

        return new JsonProcessor(od, jom, aoi);
    }

    /**
     * {@inheritDoc}
     *
     * @param position the position which the occupants reside in
     * @return
     */
    @Override
    public Set<Occupant> getOccupantsInPosition(Position position) {
        if (position == null)
            throw new IllegalArgumentException("The position cannot be null.");

        Set<Occupant> occupants = new HashSet();
        for (JsonOccupantsPosition jop : jsonOccupantsMap) {
            //better to change the equals() method of Position and Island to compare later
            if (jop.position.getRow() != position.getRow()
                    || jop.position.getColumn() != position.getColumn())
                continue;

            for (String name : jop.occupants) {
                // clone from a "prototype"
                Occupant o = duplicate(occupantsDictionary.get(name));
                o.setPosition(position);
                occupants.add(o);
            }
        }
        return occupants;
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public Set<Occupant> getAllOccupantPrototypes() {
        Set<Occupant> prototypes = new HashSet();
        for (Occupant o : occupantsDictionary.values()) {
            prototypes.add(duplicate(o));
        }
        return prototypes;
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public List<Occupant> getAllOccupantInstances() {
        List<Occupant> all = new ArrayList<>();
        for (Occupant o : allOccupantInstances) {
            all.add(duplicate(o));
        }
        return all;
    }

    //region PRIVATE STUFF

    // make a dictionary of all the types of Occupants so that they can be got by
    // names.
    private static Map<String, Occupant> makeOccupantDictionary(JsonOccupants occupantTypes) {
        Map<String, Occupant> occupantsDictionary = new HashMap();
        List<Occupant> allOccupants = new LinkedList();
        allOccupants.addAll(occupantTypes.food);
        allOccupants.addAll(occupantTypes.faunae);
        allOccupants.addAll(occupantTypes.hazards);
        allOccupants.addAll(occupantTypes.kiwis);
        allOccupants.addAll(occupantTypes.predators);
        allOccupants.addAll(occupantTypes.tools);

        for (Occupant o : allOccupants) {
            requireDataIntegerity(o);
            occupantsDictionary.put(o.getName(), o);
        }

        return occupantsDictionary;
    }

    /**
     * Checks whether the deserialised JsonOccupants contains non-nullable fields
     *
     * @param occupantTypes
     */
    private static void requireDataIntegrity(JsonOccupants occupantTypes) {
        Objects.requireNonNull(occupantTypes.food, "did not include \"food\"");
        Objects.requireNonNull(occupantTypes.faunae, "did not include \"faunae\"");
        Objects.requireNonNull(occupantTypes.tools, "did not include \"tools\"");
        Objects.requireNonNull(occupantTypes.hazards, "did not include \"hazards\"");
        Objects.requireNonNull(occupantTypes.predators, "did not include \"predators\"");
        Objects.requireNonNull(occupantTypes.kiwis, "did not include \"kiwis\"");
    }

    /**
     * Checks whether any of the deserialised JsonOccupantsPositions contains
     * non-nullable fields
     *
     * @param occupantsPosition
     */
    private static void requireDataIntegrity(JsonOccupantsPosition occupantsPosition) {
        Objects.requireNonNull(occupantsPosition.occupants, "did not include \"occupants\"");
        Objects.requireNonNull(occupantsPosition.position, "did not include \"position\"");
    }

    /**
     * Checks whether any of the deserialised Occupants contains
     * non-nullable fields
     *
     * @param occupant
     */
    private static void requireDataIntegerity(Occupant occupant) {
        if (occupant.getName() == null || occupant.getName().isEmpty())
            throw new NullPointerException("did not include \"name\"");
        if (occupant.getDescription() == null || occupant.getDescription().isEmpty())
            throw new NullPointerException(
                    "did not include \"description\" for " + occupant.getName());
    }

    private static void requireDataIntegerity(JsonOccupantsPool occupantsPool) {
        Objects.requireNonNull(occupantsPool.food, "did not include \"food\"");
        Objects.requireNonNull(occupantsPool.faunae, "did not include \"faunae\"");
        Objects.requireNonNull(occupantsPool.tools, "did not include \"tools\"");
        Objects.requireNonNull(occupantsPool.hazards, "did not include \"hazards\"");
        Objects.requireNonNull(occupantsPool.predators, "did not include \"predators\"");
        Objects.requireNonNull(occupantsPool.kiwis, "did not include \"kiwis\"");
    }

    private static Map<String, Occupant> loadOccupantsDictionary(Gson gson, String occupantsFilePath) {
        JsonOccupants jo;
        try (Reader reader = new FileReader(occupantsFilePath)) {
            jo = gson.fromJson(reader, JsonOccupants.class);
            requireDataIntegrity(jo);
        } catch (NullPointerException ex) {
            throw new IllegalStateException(occupantsFilePath + " " + ex.getMessage());
        } catch (IOException e) {
            return null;
        }

        Map<String, Occupant> od;
        try {
            od = makeOccupantDictionary(jo);
        } catch (NullPointerException ex) {
            throw new IllegalStateException("Occupant data damaged. " + ex.getMessage());
        }

        return od;
    }

    private static List<JsonOccupantsPosition> loadOccupantsPosition(Gson gson, String occupantsMapFilePath) {
        LinkedList<JsonOccupantsPosition> jom;
        try (Reader reader = new FileReader(occupantsMapFilePath)) {
            Type dataType = new TypeToken<LinkedList<JsonOccupantsPosition>>() {
            }.getType();
            jom = gson.fromJson(reader, dataType);
            for (JsonOccupantsPosition jop : jom) {
                requireDataIntegrity(jop);
            }
        } catch (NullPointerException ex) {
            throw new IllegalStateException(occupantsMapFilePath + " " + ex.getMessage());
        } catch (IOException e) {
            return null;
        }
        return jom;
    }

    private static List<Occupant> loadAllOccupantInstances(Gson gson, Map<String, Occupant> occupantsDictionary,
                                                           String occupantsPoolFilePath) {
        JsonOccupantsPool pool;

        try (Reader reader = new FileReader(occupantsPoolFilePath)) {
            pool = gson.fromJson(reader, JsonOccupantsPool.class);
            requireDataIntegerity(pool);
        } catch (IOException e) {
            return null;
        } catch (NullPointerException e) {
            throw new IllegalStateException(occupantsPoolFilePath + " " + e.getMessage());
        }

        // TODO add integrity check
        List<Occupant> allOccupantInstances = new ArrayList<>();

        Map<String, Integer> eachType = null;
        while ((eachType = getNextOccupantType(pool)) != null) {
            eachType.forEach((s, i) -> {
                Occupant[] typeInstances = duplicateMulti(occupantsDictionary.get(s), i);
                allOccupantInstances.addAll(Arrays.asList(typeInstances));
            });
            eachType.clear();
        }

        return allOccupantInstances;
    }

    private static Map<String, Integer> getNextOccupantType(JsonOccupantsPool pool) {
        if (!pool.food.isEmpty())
            return pool.food;
        if (!pool.tools.isEmpty())
            return pool.tools;
        if (!pool.faunae.isEmpty())
            return pool.faunae;
        if (!pool.kiwis.isEmpty())
            return pool.kiwis;
        if (!pool.predators.isEmpty())
            return pool.predators;
        if (!pool.hazards.isEmpty())
            return pool.hazards;
        return null;
    }

    //endregion
}
