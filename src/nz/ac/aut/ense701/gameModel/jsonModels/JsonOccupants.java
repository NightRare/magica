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
 * @author Yuan
 */
public class JsonOccupants {
    
    public final List<Food> food;
    public final List<Tool> tools;
    public final List<Fauna> faunae;
    public final List<Kiwi> kiwis;
    public final List<Predator> predators;
    public final List<Hazard> hazards;
    
    public JsonOccupants(List<Food> food, List<Tool> tools, List<Fauna> faunae,
        List<Kiwi> kiwis, List<Predator> predators, List<Hazard> hazards) {
        this.food = food;
        this.tools = tools;
        this.faunae = faunae;
        this.kiwis = kiwis;
        this.predators = predators;
        this.hazards = hazards;
    }
}
