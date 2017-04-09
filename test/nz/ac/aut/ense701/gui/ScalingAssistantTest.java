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
     * Test of getScalingAssistant method, of class ScalingAssistant.
     */
    @Test
    public void testGetScalingAssistant() {
        System.out.println("getScalingAssistant");
        ScalingAssistant expResult = null;
        ScalingAssistant result = ScalingAssistant.getScalingAssistant();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setScale method, of class ScalingAssistant.
     */
    @Test
    public void testSetScale() {
        System.out.println("setScale");
        int scale = 0;
        ScalingAssistant instance = null;
        instance.setScale(scale);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getScale method, of class ScalingAssistant.
     */
    @Test
    public void testGetScale() {
        System.out.println("getScale");
        ScalingAssistant instance = null;
        int expResult = 0;
        int result = instance.getScale();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of scale method, of class ScalingAssistant.
     */
    @Test
    public void testScale() {
        System.out.println("scale");
        int input = 0;
        ScalingAssistant instance = null;
        int expResult = 0;
        int result = instance.scale(input);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getScaledImage method, of class ScalingAssistant.
     */
    @Test
    public void testGetScaledImage() {
        System.out.println("getScaledImage");
        BufferedImage src = null;
        int percentage = 0;
        ScalingAssistant instance = null;
        BufferedImage expResult = null;
        BufferedImage result = instance.getScaledImage(src, percentage);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
