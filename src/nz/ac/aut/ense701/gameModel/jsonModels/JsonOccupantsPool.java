package nz.ac.aut.ense701.gameModel.jsonModels;

import nz.ac.aut.ense701.gameModel.*;

import java.util.Map;

/**
 * Created by Yuan on 2017/5/11.
 */
public class JsonOccupantsPool {
    public final Map<String, Integer> food;
    public final Map<String, Integer> tools;
    public final Map<String, Integer> faunae;
    public final Map<String, Integer> kiwis;
    public final Map<String, Integer> predators;
    public final Map<String, Integer> hazards;

    public JsonOccupantsPool(Map<String, Integer> food, Map<String, Integer> tools, Map<String, Integer> faunae,
                             Map<String, Integer> kiwis, Map<String, Integer> predators, Map<String, Integer> hazards) {
        this.food = food;
        this.tools = tools;
        this.faunae = faunae;
        this.kiwis = kiwis;
        this.predators = predators;
        this.hazards = hazards;
    }
}
