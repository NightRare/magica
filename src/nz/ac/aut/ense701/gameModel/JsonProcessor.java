/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.ense701.gameModel;

import java.io.FileReader;
import java.io.Reader;
import java.util.List;
import java.util.Set;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import nz.ac.aut.ense701.gameModel.jsonModels.*;

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
     *          is corrupted.
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
                Occupant o = cloneOccupant(occupantsDictionary.get(name));
                o.setPosition(position);
                occupants.add(o);
            }
        }
        return occupants;
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
    
    // a method to deep clone an Occupant object
    private static Occupant cloneOccupant(Occupant protoType) {
        
        Position position = protoType.getPosition();
        String name = protoType.getName();
        String description = protoType.getDescription();
        
        if(protoType instanceof Hazard) {
            Hazard hazard = (Hazard) protoType;
            return new Hazard(position, name, description, hazard.getImpact());
        }
        
        if(protoType instanceof Item) {
            Item item = (Item) protoType;
            double weight = item.getWeight();
            double size = item.getSize();
            
            if(protoType instanceof Food) {
                Food food = (Food) protoType;
                return new Food(position, name, description, weight, size, food.getEnergy());
            }
            
            if(protoType instanceof Tool) {
                return new Tool(position, name, description, weight, size);
            }
            
            throw new IllegalArgumentException("An Item (abstract class) object cannot "
                    + "be cloned.");
        }
        
        if(protoType instanceof Fauna) {
            
            if(protoType instanceof Kiwi) {
                return new Kiwi(position, name, description);
            }
            
            if(protoType instanceof Predator) {
                return new Predator(position, name, description);
            }
            
            return new Fauna(position, name, description);
        }
        
        throw new IllegalArgumentException("An Occupant (abstract class) object "
                + "cannot be cloned.");
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
     * @param occupantsPosition 
     */
    private static void requireDataIntegerity(Occupant occupant) {
        if(occupant.getName() == null || occupant.getName().isEmpty())
            throw new NullPointerException("did not include \"name\"");
        if(occupant.getDescription() == null || occupant.getDescription().isEmpty())
            throw new NullPointerException(
                    "did not include \"description\" for " + occupant.getName());
        
        if(occupant instanceof Item) {
            Item item = (Item) occupant;
            Objects.requireNonNull(item.getWeight(), 
                    "did not include \"weight\" for " + occupant.getName());
            Objects.requireNonNull(item.getSize(), 
                    "did not include \"size\" for " + occupant.getName());
            
            if(item instanceof Food) {
                Food food = (Food) item;
                Objects.requireNonNull(food.getEnergy(), 
                        "did not include \"energy\" for " + occupant.getName());
            }
        }
        
        if(occupant instanceof Hazard) {
            Hazard hazard = (Hazard) occupant;
            Objects.requireNonNull(hazard.getImpact(), 
                    "did not include \"impact\" for " + occupant.getName());
        }
    }
}
