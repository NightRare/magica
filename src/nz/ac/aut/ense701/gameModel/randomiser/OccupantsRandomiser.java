package nz.ac.aut.ense701.gameModel.randomiser;

import nz.ac.aut.ense701.gameModel.Occupant;

import java.util.*;

import static nz.ac.aut.ense701.gameModel.utils.OccupantsDuplicator.duplicatMulti;

/**
 *
 *
 * @author Yuan
 */
public class OccupantsRandomiser {

    /**
     *
     * Default settings:
     * 1. random seed is the currentTimeMillis
     * 2. recurssion = 0
     * 3. maintainOccupantTypesPercentage = true;
     * 4. doubleOccupantsPercentage = 0.0
     * 5. allowSameOccupantsOnOnePosition = false;
     *
     * @param length
     * @param allOccupantInstances
     */
    public OccupantsRandomiser(int length, List<Occupant> allOccupantInstances) {
        Objects.requireNonNull(allOccupantInstances);

        if(length < 1)
            throw new IllegalArgumentException("The length of a island cannot be less than 1");

        this.length = length;
        this.allOccupantInstances = allOccupantInstances;

        random = new Random(System.currentTimeMillis());
        recursion = 0;
        maintainOccupantTypesPercentage = true;
        doubleOccupantsPercentage = 0.0;
        allowSameOccupantsOnOnePosition = false;
    }

    //region ACCESSORS

    public int getRecurssionIndex() {
        return recursion;
    }

    public double getDoubleOccupantsPercentage() {
        return doubleOccupantsPercentage;
    }

    //endregion

    //region MODIFIERS

    public void setRecurssionIndex(int recurssionIndex) {
        if(recurssionIndex < 0)
            throw new IllegalArgumentException("Recurssion index cannot be negative.");

        int deepestRecurssion = 0;
        int tempLength = length;

        while(tempLength % 2 == 0 && recurssionIndex > 0) {
            tempLength /= 2;
            recurssionIndex--;
            deepestRecurssion++;
        }

        this.recursion = deepestRecurssion;
    }

    public void ignoreOccupantTypesPercentage() {
        this.maintainOccupantTypesPercentage = false;
    }

    public void setRandom(Random random) {
        Objects.requireNonNull(random);
        this.random = random;
    }

    public void setDoubleOccupantsPercentage(double doubleOccupantsPercentage) {
        if(doubleOccupantsPercentage < 0.0 || doubleOccupantsPercentage > 1.0)
            throw new IllegalArgumentException(
                    "Double occupants percentage must between 0.0 and 1.0 (both inclusive).");
        this.doubleOccupantsPercentage = doubleOccupantsPercentage;
    }

    public void allowSameOccupantsOnOnePosition() {
        allowSameOccupantsOnOnePosition = true;
    }

    //endregion

    public Set<Occupant>[][] distributeOccupantsRandomly() {
        List<Occupant> occupants = new ArrayList<>(allOccupantInstances);
        Set<Occupant>[][] oMap = new HashSet[length][length];

        Map<Integer, Set<Occupant>> distOccUnits = distributeOccupantsRecursively(length, occupants, recursion);

        for(int r = 0; r < oMap.length; r++) {
            for(int c = 0; c < oMap[0].length; c++) {
                int index = r * length + c;
                oMap[r][c] = distOccUnits.get(index);
                if(oMap[r][c] == null)
                    oMap[r][c] = new HashSet<>();
            }
        }

        return oMap;
    }

    //region PRIVATE STUFF

    private int length;

    private List<Occupant> allOccupantInstances;

    private int recursion;

    private boolean maintainOccupantTypesPercentage;

    private double doubleOccupantsPercentage;

    private boolean allowSameOccupantsOnOnePosition;

    private Random random;

    private Map<Integer, Set<Occupant>> distributeOccupantsRecursively(
            int length, List<Occupant> occupants, int recursion) {

        if(recursion == 0)
            return distributeRandomly(bundleOccupants(occupants), length * length);

        Map<Integer, Set<Occupant>> oMap = new HashMap<>();
        List<List<Occupant>> subMapOccupants = divideOccupants(occupants);
        Map<Integer, Set<Occupant>>[] subMaps = new Map[4];
        int subLength = length / 2;
        recursion--;

        for(int i = 0; i < subMaps.length; i++) {
            subMaps[i] = distributeOccupantsRecursively(subLength, subMapOccupants.get(i), recursion);
        }

        // counters[0] for upper left sub map, [2] for lower left sub map, etc.
        int[] counters = new int[4];
        for(int i = 0; i < counters.length; i++) {
            counters[i] = 0;
        }

        // mapping sub map to the super map
        for(int i = 0; i < length * length; i++) {
            // upper maps
            if(i < subLength * length) {
                // left map
                if(i % length < subLength) {
                    oMap.put(i, subMaps[0].get(counters[0]++));
                }
                else {
                    oMap.put(i, subMaps[1].remove(counters[1]++));
                }
            }
            // lower maps
            else {
                // left map
                if(i % length < subLength) {
                    oMap.put(i, subMaps[2].remove(counters[2]++));
                }
                else {
                    oMap.put(i, subMaps[3].remove(counters[3]++));
                }
            }
        }
        return oMap;
    }

    private <T> Map<Integer, T> distributeRandomly(List<T> residents, int numOfSeats) {
        if(residents.size() > numOfSeats)
            throw new IllegalArgumentException("The amount of residents cannot exceed the number of seats.");

        Map<Integer, T> dist = new HashMap<>();
        List<Integer> seats = new ArrayList<>(numOfSeats);
        for(int i = 0; i < numOfSeats; i++) {
            seats.add(i);
            dist.put(i, null);
        }

        while(!residents.isEmpty()) {
            Integer seat = seats.remove(random.nextInt(seats.size()));
            dist.put(seat, residents.remove(0));
        }

        return dist;
    }

    private List<List<Occupant>> divideOccupants(List<Occupant> occupants) {

        List<List<Occupant>> subMapOccupants = new ArrayList<>();
        for(int i = 0; i < 4; i++) {
            subMapOccupants.add(new ArrayList<>());
        }

        int index = 0;
        while(!occupants.isEmpty()) {
            int i = index % subMapOccupants.size();
            Occupant o = null;
            if(maintainOccupantTypesPercentage) {
                o = occupants.remove(0);
            }
            else {
                o = occupants.remove(random.nextInt(occupants.size()));
            }
            subMapOccupants.get(i).add(o);
            index++;
        }

        Collections.shuffle(subMapOccupants, random);
        return subMapOccupants;
    }

    private List<Set<Occupant>> bundleOccupants(List<Occupant> occupants) {
        List<Set<Occupant>> occUnits = new ArrayList<>();

        while(!occupants.isEmpty()) {
            Occupant firstOccupant = occupants.remove(0);
            Occupant secondOccupant = null;

            //if need to double up occupants
            if(random.nextDouble() < doubleOccupantsPercentage
                    && !occupants.isEmpty()
                    && (allowSameOccupantsOnOnePosition || hasDifferentOccupantNames(occupants)) ) {


                // if the name of second occupant is same as the first one
                // then pick the second again
                do {
                    secondOccupant = occupants.get(random.nextInt(occupants.size()));
                } while (secondOccupant.getName().equals(firstOccupant.getName()));
                occupants.remove(secondOccupant);
            }

            Set<Occupant> occSet = new HashSet<>();
            occSet.add(firstOccupant);
            if(secondOccupant != null)
                occSet.add(secondOccupant);
            occUnits.add(occSet);
        }

        return occUnits;
    }

    private boolean hasDifferentOccupantNames(List<Occupant> occupants) {

        Occupant first = occupants.get(0);
        for(Occupant o : occupants) {
            if(!o.getName().equals(first.getName()))
                return true;
        }
        return false;
    }
    //endregion
}
