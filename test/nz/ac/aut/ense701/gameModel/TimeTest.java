/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.ense701.gameModel;

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
public class TimeTest {
    
    public TimeTest() {
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
     * Test of tick method, of class Time.
     */
    @Test
    public void testTick() {
        System.out.println("tick");
        Time instance = new Time();
        instance.tick();
        instance.tick();
        assertEquals(2, instance.turn);
    }

    /**
     * Test of dayOrNight method, of class Time.
     * Test that light level is day at turn 0
     */
    @Test
    public void testDayOrNightZero() {
        System.out.println("dayOrNightZero");
        Time instance = new Time();
        
        //Time already at turn 0
        
        LightLevel expResult = LightLevel.DAY;
        LightLevel result = instance.dayOrNight();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of dayOrNight method, of class Time.
     * Test that light level is day at turn 5
     */
    @Test
    public void testDayOrNightFive() {
        System.out.println("dayOrNightFive");
        Time instance = new Time();
        
        // Tick till instance.turn = 5
        for (int t = 0; t < 5; t++) {
            instance.tick();
        }
        assertEquals(5, instance.turn);
        
        LightLevel expResult = LightLevel.DAY;
        LightLevel result = instance.dayOrNight();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of dayOrNight method, of class Time.
     * Test that light level is day at turn 11
     */
    @Test
    public void testDayOrNightEleven() {
        System.out.println("dayOrNightEleven");
        Time instance = new Time();
        
        // Tick till instance.turn = 11
        for (int t = 0; t < 11; t++) {
            instance.tick();
        }
        assertEquals(11, instance.turn);
        
        LightLevel expResult = LightLevel.DAY;
        LightLevel result = instance.dayOrNight();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of dayOrNight method, of class Time.
     * Test that light level is day at turn 12
     */
    @Test
    public void testDayOrNightTwelve() {
        System.out.println("dayOrNightTwelve");
        Time instance = new Time();
        
        // Tick till instance.turn = 12
        for (int t = 0; t < 12; t++) {
            instance.tick();
        }
        assertEquals(12, instance.turn);
        
        LightLevel expResult = LightLevel.NIGHT;
        LightLevel result = instance.dayOrNight();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of dayOrNight method, of class Time.
     * Test that light level is day at turn 17
     */
    @Test
    public void testDayOrNightSeventeen() {
        System.out.println("dayOrNightSeventeen");
        Time instance = new Time();
        
        // Tick till instance.turn = 17
        for (int t = 0; t < 17; t++) {
            instance.tick();
        }
        assertEquals(17, instance.turn);
        
        LightLevel expResult = LightLevel.NIGHT;
        LightLevel result = instance.dayOrNight();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of dayOrNight method, of class Time.
     * Test that light level is day at turn 23
     */
    @Test
    public void testDayOrNightTwentyThree() {
        System.out.println("dayOrNightTwentyThree");
        Time instance = new Time();
        
        // Tick till instance.turn = 23
        for (int t = 0; t < 23; t++) {
            instance.tick();
        }
        assertEquals(23, instance.turn);
        
        LightLevel expResult = LightLevel.NIGHT;
        LightLevel result = instance.dayOrNight();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of dayOrNight method, of class Time.
     * Test that light level is day at turn 30
     * This is to test that as the turn passes 24 the day starts again
     */
    @Test
    public void testDayOrNightThirty() {
        System.out.println("dayOrNightThirty");
        Time instance = new Time();
        
        // Tick till instance.turn = 30
        for (int t = 0; t < 30; t++) {
            instance.tick();
        }
        assertEquals(30, instance.turn);
        
        LightLevel expResult = LightLevel.DAY;
        LightLevel result = instance.dayOrNight();
        assertEquals(expResult, result);
    }
}
