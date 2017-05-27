package nz.ac.aut.ense701.gameModel.utils;

import nz.ac.aut.ense701.gameModel.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by Yuan on 2017/5/13.
 */
public class OccupantsDuplicatorTest {

    private Hazard hazard;
    private Food food;
    private Tool tool;
    private Fauna fauna;
    private Predator predator;
    private Kiwi kiwi;

    @Mock
    private Position mockPosition;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        Map<Terrain, Double> habitats = new HashMap<>();
        habitats.put(Terrain.FOREST, 0.3);
        habitats.put(Terrain.SAND, 0.2);
        habitats.put(Terrain.SCRUB, 0.5);
        habitats.put(Terrain.WATER, 0.0);
        habitats.put(Terrain.WETLAND, 0.0);

        hazard = new Hazard(mockPosition, "H", "h", "hPortrait", 0.5, habitats);
        food = new Food(mockPosition, "F", "f", "fPortrait", 1.0, 1.0, 20.0, habitats);
        tool = new Tool(mockPosition, "T", "t", "tPortrait", 2.0, 2.0, habitats);
        fauna = new Fauna(mockPosition, "Fa", "fa", "faPortrait", "faLink", habitats);
        predator = new Predator(mockPosition, "P", "p", "pPortrait", "pLink", habitats);
        kiwi = new Kiwi(mockPosition, "K", "k", "kPortrait", "kLink", habitats);
    }

    @After
    public void tearDown() {
        mockPosition = null;
        hazard = null;
        food = null;
        tool = null;
        fauna = null;
        kiwi = null;
        predator = null;
    }

    @Test
    public void testDuplicate_hazard() {
        Occupant duplicate = OccupantsDuplicator.duplicate(hazard);
        // fields should equal while should be different objects
        assertOccupantsEqual(hazard, duplicate);
        assertFalse(hazard == duplicate);
    }

    @Test
    public void testDuplicate_food() {
        Occupant duplicate = OccupantsDuplicator.duplicate(food);
        // fields should equal while should be different objects
        assertOccupantsEqual(food, duplicate);
        assertFalse(food == duplicate);
    }

    @Test
    public void testDuplicate_tool() {
        Occupant duplicate = OccupantsDuplicator.duplicate(tool);
        // fields should equal while should be different objects
        assertOccupantsEqual(tool, duplicate);
        assertFalse(tool == duplicate);
    }

    @Test
    public void testDuplicate_fauna() {
        Occupant duplicate = OccupantsDuplicator.duplicate(fauna);
        // fields should equal while should be different objects
        assertOccupantsEqual(fauna, duplicate);
        assertFalse(fauna == duplicate);
    }

    @Test
    public void testDuplicate_predator() {
        Occupant duplicate = OccupantsDuplicator.duplicate(predator);
        // fields should equal while should be different objects
        assertOccupantsEqual(predator, duplicate);
        assertFalse(predator == duplicate);
    }

    @Test
    public void testDuplicate_kiwi() {
        Occupant duplicate = OccupantsDuplicator.duplicate(kiwi);
        // fields should equal while should be different objects
        assertOccupantsEqual(kiwi, duplicate);
        assertFalse(kiwi == duplicate);
    }

    @Test
    public void testDuplicateMulti_nonPositiveAmount() {
        try {
            OccupantsDuplicator.duplicateMulti(hazard, 0);

            fail();
        } catch (IllegalArgumentException e) {
            // pass test
        }
    }

    @Test
    public void testDuplicateMulti_food() {
        Occupant[] duplicates = OccupantsDuplicator.duplicateMulti(food, 5);

        for(Occupant o : duplicates) {
            assertEquals(duplicates.length, 5);
            assertOccupantsEqual(food, o);
            assertFalse(food == o);
        }
    }

    @Test
    public void testDuplicateMulti_tool() {
        Occupant[] duplicates = OccupantsDuplicator.duplicateMulti(tool, 5);

        for(Occupant o : duplicates) {
            assertEquals(duplicates.length, 5);
            assertOccupantsEqual(tool, o);
            assertFalse(tool == o);
        }
    }

    @Test
    public void testDuplicateMulti_fauna() {
        Occupant[] duplicates = OccupantsDuplicator.duplicateMulti(fauna, 5);

        for(Occupant o : duplicates) {
            assertEquals(duplicates.length, 5);
            assertOccupantsEqual(fauna, o);
            assertFalse(fauna == o);
        }
    }

    @Test
    public void testDuplicateMulti_predator() {
        Occupant[] duplicates = OccupantsDuplicator.duplicateMulti(predator, 5);

        for(Occupant o : duplicates) {
            assertEquals(duplicates.length, 5);
            assertOccupantsEqual(predator, o);
            assertFalse(predator == o);
        }
    }

    @Test
    public void testDuplicateMulti_kiwi() {
        Occupant[] duplicates = OccupantsDuplicator.duplicateMulti(kiwi, 5);

        for(Occupant o : duplicates) {
            assertEquals(duplicates.length, 5);
            assertOccupantsEqual(kiwi, o);
            assertFalse(kiwi == o);
        }
    }

    @Test
    public void testDuplicateMulti_hazard() {
        Occupant[] duplicates = OccupantsDuplicator.duplicateMulti(hazard, 5);

        for(Occupant o : duplicates) {
            assertEquals(duplicates.length, 5);
            assertOccupantsEqual(hazard, o);
            assertFalse(hazard == o);
        }
    }

    private void assertOccupantsEqual(Occupant expected, Occupant actual) {

        assertEquals(expected.getClass(), actual.getClass());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getPortrait(), actual.getPortrait());
        assertEquals(expected.getPosition(), actual.getPosition());

        for(Terrain t : Terrain.values()) {
            assertTrue(expected.getHabitatProbability(t) == actual.getHabitatProbability(t));
        }

        if(expected instanceof Hazard) {
            assertTrue(((Hazard) expected).getImpact() == ((Hazard) actual).getImpact());
        }

        if(expected instanceof Fauna) {
            assertEquals(((Fauna) expected).getLink(), ((Fauna) actual).getLink());
        }

        if(expected instanceof Item) {
            assertTrue(((Item) expected).getWeight() == ((Item) actual).getWeight());
            assertTrue(((Item) expected).getSize() == ((Item) actual).getSize());

            if(expected instanceof Food) {
                assertTrue(((Food) expected).getEnergy() == ((Food) actual).getEnergy());
            }
        }
    }

}
