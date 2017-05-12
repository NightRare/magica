package nz.ac.aut.ense701.gameModel;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

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

        IDataManager dataManager = null;
        try {
            dataManager = JsonProcessor.make(occupantsFilePath, 
                    occupantsMapFilePath, occupantsPoolFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<Occupant, Sound> soundMap = new HashMap<>();

        Set<Occupant> faunasAndPredators = dataManager.getAllOccupantTemplates();
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
