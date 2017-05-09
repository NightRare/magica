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

import static nz.ac.aut.ense701.gameModel.utils.OccupantsDuplicator.duplicate;

/**
 * A JSON Implementation of IDataManager.
 * 
 * @author Yuan
 */
public class JsonProcessor implements IDataManager{

    private Map<String, Occupant> occupantsDictionary;
    private List<JsonOccupantsPosition> jsonOccupantsMap;
    
    private JsonProcessor(Map<String, Occupant> occupantsDictionary, 
            List<JsonOccupantsPosition> jsonOccupantsMap) {        
        this.occupantsDictionary = occupantsDictionary;
        this.jsonOccupantsMap = jsonOccupantsMap;
    }
    
    /**
     * Static factory of JsonProcessor.
     * 
     * @param occupantsFilePath the file path of Occupants.json
     * @param occupantsMapFilePath the file path of OccupantsMap.json
     * @return an instance of IDataManager.
     * @throws IllegalArgumentException if any of the arguments is {@code null} or empty.
     * @throws IOException if IO errors happen when reading files of the given paths.
     * @throws IllegalStateException if the data integrity of any of the Json files 
     *          is corrupted; currently this only supports non-primitive type fileds.
     */
    public static IDataManager make(String occupantsFilePath, String occupantsMapFilePath) 
            throws IOException {
        if(occupantsFilePath == null || occupantsMapFilePath == null
                || occupantsFilePath.isEmpty() || occupantsMapFilePath.isEmpty())
            throw new IllegalArgumentException(
                    "File paths for persistent data cannot be null or empty.");
        
        Gson gson = new Gson();
        JsonOccupants jo;
        try(Reader reader = new FileReader(occupantsFilePath)) {
            jo = gson.fromJson(reader, JsonOccupants.class);
            requireDataIntegrity(jo);
        } catch (NullPointerException ex) {
            throw new IllegalStateException(occupantsFilePath + " " + ex.getMessage());
        }
        
        LinkedList<JsonOccupantsPosition> jom;
        try(Reader reader = new FileReader(occupantsMapFilePath)) {            
            Type dataType = new TypeToken<LinkedList<JsonOccupantsPosition>>(){}.getType();
            jom = gson.fromJson(reader, dataType);
            for(JsonOccupantsPosition jop : jom) {
                requireDataIntegrity(jop);
            }
        } catch (NullPointerException ex) {
            throw new IllegalStateException(occupantsMapFilePath + " " + ex.getMessage());
        } 
        
        Map<String, Occupant> od;
        try {
            od = makeOccupantDictionary(jo);
        } catch (NullPointerException ex) {
            throw new IllegalStateException("Occupant data damaged. " + ex.getMessage());
        }

        return new JsonProcessor(od, jom);
    }
    
    @Override
    public Set<Occupant> getOccupantsInPosition(Position position) {
        if(position == null)
            throw new IllegalArgumentException("The position cannot be null.");
        
        Set<Occupant> occupants = new HashSet();
        for(JsonOccupantsPosition jop : jsonOccupantsMap) {
        //better to change the equals() method of Position and Island to compare later
            if(jop.position.getRow() != position.getRow()
                    || jop.position.getColumn() != position.getColumn())
                continue;
            
            for(String name : jop.occupants) {
                // clone from a "prototype"
                Occupant o = duplicate(occupantsDictionary.get(name));
                o.setPosition(position);
                occupants.add(o);
            }
        }
        return occupants;
    }
    
    @Override
    public Set<Occupant> getAllOccupantTemplates() {
        Set<Occupant> templates = new HashSet();
        for(Occupant o : occupantsDictionary.values()) {
            templates.add(duplicate(o));
        }
        return templates;
    }

    @Override
    public Map<Occupant, Integer> getAllOccupantTemplatesWithCount() {
        Map<String, Integer> nameCounts = new HashMap<>();
        Map<Occupant, Integer> occCount = new HashMap<>();
        for(JsonOccupantsPosition jop : jsonOccupantsMap) {
            for (String occName : jop.occupants) {
                if(nameCounts.containsKey(occName)) {
                    nameCounts.put(occName, nameCounts.get(occName) + 1);
                }
                else {
                    nameCounts.put(occName, 1);
                }
            }
        }

        for(Map.Entry<String, Integer> nCount : nameCounts.entrySet()) {
            occCount.put(occupantsDictionary.get(nCount.getKey()), nCount.getValue());
        }

        return occCount;
    }

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
        
        for(Occupant o : allOccupants) {
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
        if(occupant.getName() == null || occupant.getName().isEmpty())
            throw new NullPointerException("did not include \"name\"");
        if(occupant.getDescription() == null || occupant.getDescription().isEmpty())
            throw new NullPointerException(
                    "did not include \"description\" for " + occupant.getName());
    }
}
