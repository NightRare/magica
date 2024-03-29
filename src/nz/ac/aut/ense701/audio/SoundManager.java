package nz.ac.aut.ense701.audio;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import nz.ac.aut.ense701.gameModel.Food;
import nz.ac.aut.ense701.gameModel.Hazard;
import nz.ac.aut.ense701.gameModel.IDataManager;
import nz.ac.aut.ense701.gameModel.JsonProcessor;
import nz.ac.aut.ense701.gameModel.Occupant;
import nz.ac.aut.ense701.gameModel.Tool;

/**
 * Generate sound clips for faunas and predators.
 * Created by Jing on 2017/5/10.
 */
public class SoundManager {

    public static Map<Occupant, Sound> SoundLoader(String occupantsFilePath, 
            String occupantsMapFilePath, String occupantsPoolFilePath) {

        Objects.requireNonNull(occupantsFilePath, "occupantsFilePath cannot be null");
        Objects.requireNonNull(occupantsMapFilePath, "occupantsMapFilePath cannot be null");
        if (occupantsFilePath.isEmpty() ||occupantsMapFilePath.isEmpty()) {
            throw new IllegalArgumentException("File path cannot be empty.");
        }

        IDataManager dataManager = JsonProcessor.make(occupantsFilePath,
                occupantsMapFilePath, occupantsPoolFilePath);

        Map<Occupant, Sound> soundMap = new HashMap<>();

        Set<Occupant> faunasAndPredators = dataManager.getAllOccupantPrototypes();
        faunasAndPredators.removeIf(occupant -> occupant instanceof Food || occupant instanceof Tool
                || occupant instanceof Hazard);

        if (faunasAndPredators.isEmpty()) {
            throw new IllegalStateException("There's no animal for the SoundLoader method.");
        }

        for (Occupant o : faunasAndPredators) {
            try {
                soundMap.put(o, new Sound("sound/" + o.getName() + ".ogg"));
            } catch (SlickException e) {
                e.printStackTrace();
            }
        }
        return soundMap;
    }

    private SoundManager() {
    }
}
