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
public class GUIConfigsTest {
    
    public GUIConfigsTest() {
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
     * Test of rowToY method, of class GUIConfigs.
     */
    @Test
    public void testRowToY() {
        System.out.println("rowToY");
        int row = 5;
        int expResult = 250;
        int result = GUIConfigs.rowToY(row);
        assertEquals(expResult, result);
    }

    /**
     * Test of colToX method, of class GUIConfigs.
     */
    @Test
    public void testColToX() {
        System.out.println("colToX");
        int col = 5;
        int expResult = 550;
        int result = GUIConfigs.colToX(col);
        assertEquals(expResult, result);
    }

    /**
     * Test of getSquareHeight method, of class GUIConfigs.
     */
    @Test
    public void testGetSquareHeight() {
        System.out.println("getSquareHeight");
        int expResult = 50;
        int result = GUIConfigs.getSquareHeight();
        assertEquals(expResult, result);
    }

    /**
     * Test of getSquareWidth method, of class GUIConfigs.
     */
    @Test
    public void testGetSquareWidth() {
        System.out.println("getSquareWidth");
        int expResult = 50;
        int result = GUIConfigs.getSquareWidth();
        assertEquals(expResult, result);
    }

    /**
     * Test of getSidePanelWidth method, of class GUIConfigs.
     */
    @Test
    public void testGetSidePanelWidth() {
        System.out.println("getSidePanelWidth");
        int expResult = 300;
        int result = GUIConfigs.getSidePanelWidth();
        assertEquals(expResult, result);
    }

    /**
     * Test of boardWidth method, of class GUIConfigs.
     */
    @Test
    public void testBoardWidth() {
        System.out.println("boardWidth");
        double expResult = 276.0;
        double result = GUIConfigs.boardWidth();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of boardHeight method, of class GUIConfigs.
     */
    @Test
    public void testBoardHeight() {
        System.out.println("boardHeight");
        double expResult = 382.0;
        double result = GUIConfigs.boardHeight();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of boardOffsetX method, of class GUIConfigs.
     */
    @Test
    public void testBoardOffsetX() {
        System.out.println("boardOffsetX");
        double expResult = 12.0;
        double result = GUIConfigs.boardOffsetX();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of boardOffsetY method, of class GUIConfigs.
     */
    @Test
    public void testBoardOffsetY() {
        System.out.println("boardOffsetY");
        double expResult = 12.0;
        double result = GUIConfigs.boardOffsetY();
        assertEquals(expResult, result, 0.0);
    }
    
}
