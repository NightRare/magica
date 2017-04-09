/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 * @author Sam
 */
public class ScalingAssistantTest {
    
    public ScalingAssistantTest() {
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
     * Test of getScale method, of class ScalingAssistant.
     */
    @Test
    public void testSetAndGetScale() {
        System.out.println("setAndGetScale");
        ScalingAssistant instance = ScalingAssistant.getScalingAssistant();
        instance.setScale(120);
        int expResult = 120;
        int result = instance.getScale();
        assertEquals(expResult, result);
    }

    /**
     * Test of scale method, of class ScalingAssistant.
     */
    @Test
    public void testScale() {
        System.out.println("scale");
        int input = 200;
        ScalingAssistant instance = ScalingAssistant.getScalingAssistant();
        int expResult = 240;
        
        // check that NOT scaled before scale called
        int result = instance.scale(input);
        assertFalse(result == expResult);
        
        // check that IS scaled after scale called
        instance.setScale(120);
        result = instance.scale(input);
        assertEquals(expResult, result);
    }
}
