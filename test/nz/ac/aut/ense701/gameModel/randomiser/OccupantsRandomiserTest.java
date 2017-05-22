package nz.ac.aut.ense701.gameModel.randomiser;

import nz.ac.aut.ense701.gameModel.*;
import nz.ac.aut.ense701.gameModel.utils.OccupantsDuplicator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by Yuan on 2017/5/12.
 */
public class OccupantsRandomiserTest {

    private OccupantsRandomiser or;

    private List<Occupant> allOccupantInstances;

    private int length;

    @Before
    public void setUp() {

        allOccupantInstances = new ArrayList<>();
        Occupant[] kiwis = OccupantsDuplicator.duplicateMulti(
                new Kiwi(null, "Kiwi", "Kiwi desc"), 8);
        Occupant[] predators = OccupantsDuplicator.duplicateMulti(
                new Predator(null, "Cat", "Cat desc"), 8);
        Occupant[] faunae = OccupantsDuplicator.duplicateMulti(
                new Fauna(null, "Tui", "Tui desc"), 8);
        Occupant[] hazards = OccupantsDuplicator.duplicateMulti(
                new Hazard(null, "Fall", "Fall desc", 0.5), 8);
        allOccupantInstances.addAll(Arrays.asList(kiwis));
        allOccupantInstances.addAll(Arrays.asList(predators));
        allOccupantInstances.addAll(Arrays.asList(faunae));
        allOccupantInstances.addAll(Arrays.asList(hazards));

        length = 16;
        or = new OccupantsRandomiser(length, new ArrayList<>(allOccupantInstances));
    }

    @After
    public void tearDown() {
        or = null;
        allOccupantInstances = null;
    }

    @Test
    public void testSetRecursionIndex_valid() {
        or.setRecursionIndex(2);
        assertEquals(2, or.getRecursionIndex());
    }

    @Test
    public void testSetRecursionIndex_negativeIndex() {
        try {
            or.setRecursionIndex(-1);
        } catch (IllegalArgumentException e) {
            // pass test
            return;
        }
        fail();
    }

    @Test
    public void testSetRecursionIndex_indexBiggerThanMax() {
        // length is 16, therefore the biggest index should be 4
        or.setRecursionIndex(4);
        assertEquals(4, or.getRecursionIndex());

        // should not exceed the max
        or.setRecursionIndex(5);
        assertEquals(4, or.getRecursionIndex());
    }

    @Test
    public void setDoubleOccupantsPercentage_valid() {
        or.setDoubleOccupantsPercentage(0.5);
        assertTrue(0.5 == or.getDoubleOccupantsPercentage());
    }

    @Test
    public void setDoubleOccupantsPercentage_illegalArguments() {
        boolean fail = true;
        try {
            or.setDoubleOccupantsPercentage(-0.1);
        } catch (IllegalArgumentException e) {
            fail = false;
        }

        try {
            or.setDoubleOccupantsPercentage(1.1);
        } catch (IllegalArgumentException e) {
            fail = fail || false;
        }
        assertFalse(fail);
    }

    /**
     * Default settings:
     * <p>random seed is the currentTimeMillis</p>
     * <p>recurssion = 0</p>
     * <p>maintainOccupantTypesPercentage = true</p>
     * <p>doubleOccupantsPercentage = 0.0</p>
     * <p>resideRull: always return true</p>
     */
    @Test
    public void testDistributeOccupantsRandomly_defaultSettingAllDistributed() {
        Set<Occupant>[][] map = or.distributeOccupantsRandomly();

        List<Set<Occupant>> mapList = new ArrayList<>();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                mapList.add(map[i][j]);
            }
        }

        // test all occupants are distributed
        for (Occupant occ : allOccupantInstances) {
            boolean contains = false;
            for (Set<Occupant> unit : mapList) {
                // should no double occupants
                assertTrue(unit.size() <= 1);

                contains = unit.contains(occ);
                if (contains) break;
            }
            assertTrue(contains);
        }
    }

    @Test
    public void testDistributeOccupantsRandomly_setRandomSeed() {
        Set<Occupant>[][] firstMap = or.distributeOccupantsRandomly();
        or.setRandom(new Random(50));
        Set<Occupant>[][] secondMap = or.distributeOccupantsRandomly();
        or.setRandom(new Random(50));
        Set<Occupant>[][] thirdMap = or.distributeOccupantsRandomly();

        // first map should be different from the second
        boolean different = false;
        for (int i = 0; i < firstMap.length; i++) {
            for (int j = 0; j < firstMap.length; j++) {
                for(Occupant o : firstMap[i][j]) {
                    if(!secondMap[i][j].contains(o)) {
                        different = true;
                        break;
                    }
                }
            }
        }
        assertTrue(different);

        // the second map should be same as the third
        different = false;
        for (int i = 0; i < firstMap.length; i++) {
            for (int j = 0; j < firstMap.length; j++) {
                for(Occupant o : thirdMap[i][j]) {
                    if(!secondMap[i][j].contains(o)) {
                        different = true;
                        break;
                    }
                }
            }
        }
        assertFalse(different);
    }

    @Test
    public void testDistributeOccupantsRandomly_recursion() {
        or.setRecursionIndex(1);

        Set<Occupant>[][] map = or.distributeOccupantsRandomly();
        int halfLen = length / 2;

        // 0 for upper-left, 1 for upper-right, 2 for lower-left, 3 for lower-right
        Map<Class, Integer> upLeft = new HashMap<>();
        Map<Class, Integer> upRight = new HashMap<>();
        Map<Class, Integer> lowLeft = new HashMap<>();
        Map<Class, Integer> lowRight = new HashMap<>();

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                //upper
                if (i < halfLen) {
                    //left
                    if(j < halfLen) {
                        countClass(upLeft, map[i][j]);
                    }
                    //right
                    else {
                        countClass(upRight, map[i][j]);
                    }
                }
                //lower
                else {
                    //left
                    if(j < halfLen) {
                        countClass(lowLeft, map[i][j]);
                    }
                    //right
                    else {
                        countClass(lowRight, map[i][j]);
                    }
                }
            }
        }

        // check whether distributed evenly

        int kiwis = upLeft.get(Kiwi.class);
        int predators = upLeft.get(Predator.class);
        int faunae = upLeft.get(Fauna.class);
        int hazards = upLeft.get(Hazard.class);

        assertClassCount(upRight, kiwis, predators, faunae, hazards);
        assertClassCount(lowLeft, kiwis, predators, faunae, hazards);
        assertClassCount(lowRight, kiwis, predators, faunae, hazards);
    }

    @Test
    public void testDistributeOccupantsRandomly_doubleOccupants() {
        or.setDoubleOccupantsPercentage(0.5);

        Set<Occupant>[][] map = or.distributeOccupantsRandomly();
        boolean hasDouble = false;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                if(map[i][j].size() == 2) {
                    hasDouble = true;
                    break;
                }
            }
            if(hasDouble) break;
        }
        assertTrue(hasDouble);
    }

    @Test
    public void testDistributeOccupantsRandomly_testResideRull() {
        or.setDoubleOccupantsPercentage(0.99);
        // the reside rule decides that no double occupants allowed at any circumstance
        or.setResideRull((existedOccupants, candidate) -> false);

        Set<Occupant>[][] map = or.distributeOccupantsRandomly();
        boolean hasDouble = false;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                if(map[i][j].size() == 2) {
                    hasDouble = true;
                    break;
                }
            }
            if(hasDouble) break;
        }
        assertFalse(hasDouble);
    }


    private void countClass(Map<Class, Integer> counter, Set<Occupant> unit) {
        for(Occupant o : unit) {
            if(counter.containsKey(o.getClass())) {
                counter.put(o.getClass(), counter.get(o.getClass()) + 1);
            }
            else {
                counter.put(o.getClass(), 1);
            }
        }
    }

    private void assertClassCount(Map<Class, Integer> counter, int kiwis, int predators,
                                  int faunae, int hazards) {
        assertTrue(kiwis == counter.get(Kiwi.class));
        assertTrue(predators == counter.get(Predator.class));
        assertTrue(faunae == counter.get(Fauna.class));
        assertTrue(hazards == counter.get(Hazard.class));
    }
}
