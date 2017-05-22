package nz.ac.aut.ense701.gameModel.randomiser;

import nz.ac.aut.ense701.gameModel.Occupant;

import java.util.*;

/**
 * A randomiser for randomly distribute occupants on a square map.
 * <p>
 * Supports recursive distribution. If recursion index is set to 1, then occupants
 * will be divided into 4 parts each of which will be distributed randomly on a quarter-sized
 * sub map. This can be used to distribute occupants more evenly over the map.
 *
 * @author Yuan
 */
public class OccupantsRandomiser {

    /**
     * Initialise an OccupantsRandomiser.
     * <p>
     * Default settings:
     * <p>random seed is the currentTimeMillis</p>
     * <p>recurssion = 0</p>
     * <p>maintainOccupantTypesPercentage = true</p>
     * <p>doubleOccupantsPercentage = 0.0</p>
     * <p>allowSameOccupantsOnOnePosition = false</p>
     * <p>resideRull: always return true</p>
     *
     * @param length               the length of the square map
     * @param allOccupantInstances the list of all occupant instances
     */
    public OccupantsRandomiser(int length, List<Occupant> allOccupantInstances) {
        Objects.requireNonNull(allOccupantInstances);

        if (length < 1)
            throw new IllegalArgumentException("The length of a island cannot be less than 1");

        this.length = length;
        this.allOccupantInstances = allOccupantInstances;

        random = new Random(System.currentTimeMillis());
        recursion = 0;
        maintainOccupantTypesPercentage = true;
        doubleOccupantsPercentage = 0.0;
        resideRull = (existedOccupants, candidate) -> true;
    }

    //region ACCESSORS

    /**
     * Gets the recursion index.
     *
     * @return the recursion index
     */
    public int getRecursionIndex() {
        return recursion;
    }

    /**
     * Gets the probability of two occupants reside in one square (position).
     *
     * @return the double-occupants percentage
     */
    public double getDoubleOccupantsPercentage() {
        return doubleOccupantsPercentage;
    }

    //endregion

    //region MODIFIERS

    /**
     * Set the recursion index. If the given index exceeds the possible max index, then the max will be
     * set automatically.
     * <p>
     * If n>0, and the length of the map can be exactly divided by 2^n while cannot be exactly divided by 2^(n+1)
     * , then n is the possible max recursion index.
     *
     * @param recurssionIndex the recursion index
     * @throws IllegalArgumentException if recursionIndex < 0;
     */
    public void setRecursionIndex(int recurssionIndex) {
        if (recurssionIndex < 0)
            throw new IllegalArgumentException("Recurssion index cannot be negative.");

        int deepestRecurssion = 0;
        int tempLength = length;

        while (tempLength % 2 == 0 && recurssionIndex > 0) {
            tempLength /= 2;
            recurssionIndex--;
            deepestRecurssion++;
        }

        this.recursion = deepestRecurssion;
    }

    /**
     * Sets not to maintain the proportion of different types of occupants when recursively distribute
     * occupants.
     */
    public void ignoreOccupantTypesPercentage() {
        this.maintainOccupantTypesPercentage = false;
    }

    /**
     * Sets the core randomiser.
     *
     * @param random the random engine
     * @throws NullPointerException if random is {@code null}
     */
    public void setRandom(Random random) {
        Objects.requireNonNull(random);
        this.random = random;
    }

    /**
     * Sets the probability of two occupants reside in one square (position).
     *
     * @param doubleOccupantsPercentage the probability
     * @throws IllegalArgumentException if 0.0 < doubleOccupantsPercentage < 1.0
     */
    public void setDoubleOccupantsPercentage(double doubleOccupantsPercentage) {
        if (doubleOccupantsPercentage < 0.0 || doubleOccupantsPercentage > 1.0)
            throw new IllegalArgumentException(
                    "Double occupants percentage must between 0.0 and 1.0 (both inclusive).");
        this.doubleOccupantsPercentage = doubleOccupantsPercentage;
    }

    /**
     * Sets the reside rule.
     *
     * @param resideRull the reside rule for occupants
     */
    public void setResideRull(ResideRull resideRull) {
        Objects.requireNonNull(resideRull);
        this.resideRull = resideRull;
    }
    //endregion

    /**
     * Distributes a list of occupants on a square map.
     *
     * @return the array representing the map holding all the Occupant bundles (units)
     */
    public Set<Occupant>[][] distributeOccupantsRandomly() {
        List<Occupant> occupants = new ArrayList<>(allOccupantInstances);
        Set<Occupant>[][] oMap = new HashSet[length][length];

        Map<Integer, Set<Occupant>> distOccUnits = distributeOccupantsRecursively(length, occupants, recursion);

        for (int r = 0; r < oMap.length; r++) {
            for (int c = 0; c < oMap[0].length; c++) {
                int index = r * length + c;
                oMap[r][c] = distOccUnits.get(index);
                if (oMap[r][c] == null)
                    oMap[r][c] = new HashSet<>();
            }
        }

        return oMap;
    }

    public interface ResideRull {

        /**
         * Checks whether the candidate occupant may reside with the existed occupants in the same square.
         *
         * @param existedOccupants occupants already in the square
         * @param candidate        candidate occupant
         * @return true if candidate may reside with the existed occupants
         */
        public boolean mayResideTogether(List<Occupant> existedOccupants, Occupant candidate);
    }

    //region PRIVATE STUFF

    private int length;

    private List<Occupant> allOccupantInstances;

    private int recursion;

    private boolean maintainOccupantTypesPercentage;

    private double doubleOccupantsPercentage;

    private Random random;

    private ResideRull resideRull;

    private Map<Integer, Set<Occupant>> distributeOccupantsRecursively(
            int length, List<Occupant> occupants, int recursion) {

        if (recursion == 0)
            return distributeRandomly(bundleOccupants(occupants), length * length);

        Map<Integer, Set<Occupant>> oMap = new HashMap<>();
        List<List<Occupant>> subMapOccupants = divideOccupants(occupants);
        Map<Integer, Set<Occupant>>[] subMaps = new Map[4];
        int subLength = length / 2;
        recursion--;

        for (int i = 0; i < subMaps.length; i++) {
            subMaps[i] = distributeOccupantsRecursively(subLength, subMapOccupants.get(i), recursion);
        }

        // counters[0] for upper left sub map, [2] for lower left sub map, etc.
        int[] counters = new int[4];
        for (int i = 0; i < counters.length; i++) {
            counters[i] = 0;
        }

        // mapping sub map to the super map
        for (int i = 0; i < length * length; i++) {
            // upper maps
            if (i < subLength * length) {
                // left map
                if (i % length < subLength) {
                    oMap.put(i, subMaps[0].get(counters[0]++));
                } else {
                    oMap.put(i, subMaps[1].remove(counters[1]++));
                }
            }
            // lower maps
            else {
                // left map
                if (i % length < subLength) {
                    oMap.put(i, subMaps[2].remove(counters[2]++));
                } else {
                    oMap.put(i, subMaps[3].remove(counters[3]++));
                }
            }
        }
        return oMap;
    }

    private <T> Map<Integer, T> distributeRandomly(List<T> residents, int numOfSeats) {
        if (residents.size() > numOfSeats)
            throw new IllegalArgumentException("The amount of residents cannot exceed the number of seats.");

        Map<Integer, T> dist = new HashMap<>();
        List<Integer> seats = new ArrayList<>(numOfSeats);
        for (int i = 0; i < numOfSeats; i++) {
            seats.add(i);
            dist.put(i, null);
        }

        while (!residents.isEmpty()) {
            Integer seat = seats.remove(random.nextInt(seats.size()));
            dist.put(seat, residents.remove(0));
        }

        return dist;
    }

    private List<List<Occupant>> divideOccupants(List<Occupant> occupants) {

        List<List<Occupant>> subMapOccupants = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            subMapOccupants.add(new ArrayList<>());
        }

        int index = 0;
        while (!occupants.isEmpty()) {
            int i = index % subMapOccupants.size();
            Occupant o = null;
            if (maintainOccupantTypesPercentage) {
                o = occupants.remove(0);
            } else {
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

        while (!occupants.isEmpty()) {
            Occupant firstOccupant = occupants.remove(0);
            Occupant secondOccupant = null;

            //if need to double up occupants
            if (random.nextDouble() < doubleOccupantsPercentage
                    && !occupants.isEmpty()
                    && hasLegalCandidate(firstOccupant, occupants)) {


                // if the name of second occupant is same as the first one
                // then pick the second again
                do {
                    secondOccupant = occupants.get(random.nextInt(occupants.size()));
                } while (!resideRull.mayResideTogether(Arrays.asList(firstOccupant), secondOccupant));
                occupants.remove(secondOccupant);
            }

            Set<Occupant> occSet = new HashSet<>();
            occSet.add(firstOccupant);
            if (secondOccupant != null)
                occSet.add(secondOccupant);
            occUnits.add(occSet);
        }

        return occUnits;
    }

    private boolean hasDifferentOccupantNames(Occupant first, List<Occupant> occupants) {
        for (Occupant o : occupants) {
            if (!o.getName().equals(first.getName()))
                return true;
        }
        return false;
    }

    private boolean hasLegalCandidate(Occupant first, List<Occupant> occupants) {
        List<Occupant> existed = new ArrayList<>();
        existed.add(first);
        for (Occupant o : occupants) {
            if (resideRull.mayResideTogether(existed, o))
                return true;
        }
        return false;
    }
    //endregion
}
