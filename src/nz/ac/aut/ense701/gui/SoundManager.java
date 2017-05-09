package nz.ac.aut.ense701.gui;

import nz.ac.aut.ense701.gameModel.*;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Jing on 2017/5/10.
 */
public class SoundManager {

    private static Map<String, Sound> SoundClipsMap;
    private static IDataManager dataManager;

    private SoundManager() {
    }

    ;

    public static Map<String, Sound> SoundLoader() {
        SoundClipsMap = new HashMap<>();
        try {
            dataManager = JsonProcessor.make("data/Occupants.json", "data/OccupantsMap.json");
        } catch (IOException ex) {
            Logger.getLogger(AssetManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        Set<Occupant> faunasAndPredators;
        faunasAndPredators = dataManager.getAllOccupantTemplates();
        faunasAndPredators.removeIf(occupant -> occupant instanceof Food || occupant instanceof Tool
                || occupant instanceof Hazard);

        for (Occupant o : faunasAndPredators) {
            try {
                SoundClipsMap.put(o.getName(), new Sound("sound/" + o.getName() + ".ogg"));
            } catch (SlickException e) {
                e.printStackTrace();
            }
        }

        return SoundClipsMap;
    }

}
