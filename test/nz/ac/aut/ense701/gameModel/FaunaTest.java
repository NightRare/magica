package nz.ac.aut.ense701.gameModel;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 *
 * @author AS
 * @version 2011
 */
public class FaunaTest {
    
    public FaunaTest() {
    }

    /**
     * Test of getStringRepresentation method, of class Fauna.
     */
    @Test
    public void testGetStringRepresentation() {
        Island island = new Island(5,5);
        Position position = new Position(island, 4,4);
        Fauna instance = new Fauna(position, "Oystercatcher", " A nesting oystercatcher", "",
                "", new HashMap<>());
        String expResult = "F";
        String result = instance.getStringRepresentation();
        assertEquals(expResult, result);
    }
    
}
