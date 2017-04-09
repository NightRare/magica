/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.ense701.gui;

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
public class GlobalsTest {
    
    public GlobalsTest() {
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
     * Test of rowToY method, of class Globals.
     */
    @Test
    public void testRowToY() {
        System.out.println("rowToY");
        int row = 5;
        int expResult = 400;
        int result = Globals.rowToY(row);
        assertEquals(expResult, result);
    }

    /**
     * Test of colToX method, of class Globals.
     */
    @Test
    public void testColToX() {
        System.out.println("colToX");
        int col = 5;
        int expResult = 400;
        int result = Globals.colToX(col);
        assertEquals(expResult, result);
    }

    /**
     * Test of getSquareHeight method, of class Globals.
     */
    @Test
    public void testGetSquareHeight() {
        System.out.println("getSquareHeight");
        int expResult = 80;
        int result = Globals.getSquareHeight();
        assertEquals(expResult, result);
    }

    /**
     * Test of getSquareWidth method, of class Globals.
     */
    @Test
    public void testGetSquareWidth() {
        System.out.println("getSquareWidth");
        int expResult = 80;
        int result = Globals.getSquareWidth();
        assertEquals(expResult, result);
    }
    
}
