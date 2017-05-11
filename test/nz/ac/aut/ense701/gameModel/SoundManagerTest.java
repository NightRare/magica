package nz.ac.aut.ense701.gameModel;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.newdawn.slick.Sound;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jing on 2017/5/11.
 */
public class SoundManagerTest {

    private static Map<Occupant, Sound> soundMap;

    @Before
    public void setUp() throws Exception {
        soundMap = new HashMap<>();
    }

    @After
    public void tearDown() throws Exception {
        soundMap = null;
    }

    /**
     * Test SoundLoader method with null input.
     */
    @Test(expected = NullPointerException.class)
    public void testNullDataManagerInput() {
        soundMap = SoundManager.SoundLoader(null, null);
    }

    /**
     * Test SoundLoader method with empty file paths.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testEmptyInput() {
        soundMap = SoundManager.SoundLoader("", "");
    }


    /**
     * Test whether the SoundLoader method generates a valid sound map,
     * which contains only faunas and predators.
     */
    @Test
    public void testValidSoundMap() {

        soundMap = SoundManager.SoundLoader("data/Occupants.json",
                "data/OccupantsMap.json");

        boolean soundMapIsValid = false;

        soundMap.keySet().removeIf(animal -> animal instanceof Fauna || animal instanceof Predator);
        if (soundMap.isEmpty()) {
            soundMapIsValid = true;
        }
        Assert.assertTrue(soundMapIsValid);
    }


    public SoundManagerTest() {
    }
}
