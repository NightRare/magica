/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.ense701.gameModel.jsonModels;

import java.util.List;
import nz.ac.aut.ense701.gameModel.*;

/**
 * A structured class for reading data from Occupants.json.
 * 
 * @author Vince
 */
public class JsonOccupants {
    
    private List<Food> food;
    private List<Tool> tools;
    private List<Fauna> faunae;
    private List<Kiwi> kiwis;
    private List<Predator> predators;
    private List<Hazard> hazards;
    
    public JsonOccupants(List<Food> food, List<Tool> tools, List<Fauna> faunae,
        List<Kiwi> kiwis, List<Predator> predators, List<Hazard> hazards) {
        this.food = food;
        this.tools = tools;
        this.faunae = faunae;
        this.kiwis = kiwis;
        this.predators = predators;
        this.hazards = hazards;
    }
    
    public List<Food> food() {
        return food;
    }
    
    public List<Tool> tools() {
        return tools;
    }
    
    public List<Fauna> faunae() {
        return faunae;
    }
    
    public List<Kiwi> kiwis() {
        return kiwis;
    }
    
    public List<Predator> predators() {
        return predators;
    }
    
    public List<Hazard> hazards() {
        return hazards;
    }
}
