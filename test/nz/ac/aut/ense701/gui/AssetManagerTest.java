package nz.ac.aut.ense701.gui;

import java.awt.image.BufferedImage;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


/**
 *
 * @author tommy
 */
public class AssetManagerTest {
    public AssetManagerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getOccupantPortrait method with null input.
     */
    @Test (expected = IllegalArgumentException.class)
    public void getOccupantPortrait_test_null_input() {
        AssetManager am = AssetManager.getAssetManager();
        BufferedImage bf = am.getOccupantPortrait(null);
    }
    
    /**
     * Test of getOccupantPortrait method with empty string.
     */
    @Test (expected = IllegalArgumentException.class)
    public void getOccupantPortrait_test_empty_string() {
        AssetManager am = AssetManager.getAssetManager();
        BufferedImage bf = am.getOccupantPortrait("");
    }
    
    /**
     * Test of getOccupantPortrait method with a string which is an incorrect input.
     */
    @Test
    public void getOccupantPortrait_test_incorrect_input() {
        AssetManager am = AssetManager.getAssetManager();
        //The correct input in this test should be "Kiwi".
        BufferedImage bf = am.getOccupantPortrait("kiwi");
        assertNull(bf);
    }
    
    
    /**
     * Test of getOccupantPortrait method with a proper input.
     */
    @Test
    public void getOccupantPortrait_test_good_input() {
        AssetManager am = AssetManager.getAssetManager();
        BufferedImage bf = am.getOccupantPortrait("Kiwi");
        assertNotNull(bf);
    }
}
