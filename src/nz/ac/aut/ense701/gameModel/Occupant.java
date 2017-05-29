package nz.ac.aut.ense701.gameModel;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Abstract base class for occupants that inhabit Kiwi Island.
 *
 * @author AS
 * @version 2.0 - October 2011 - AS - added toString
 */
public abstract class Occupant {
    private Position position;
    private final String name;
    private final String description;
    private final String portrait; //image file path
    private final Map<Terrain, Double> habitats;

    /**
     * Construct an occupant for a known position & name.
     *
     * @param position    the position of the occupant
     * @param name        the name of the occupant
     * @param description a longer description
     */
    public Occupant(Position position, String name, String description) {
        this.position = position;
        this.name = name;
        this.description = description;
        this.portrait = "";
        this.habitats = new HashMap();
    }

    /**
     * Construct an occupant for a known position & name.
     *
     * @param position    the position of the occupant
     * @param name        the name of the occupant
     * @param description a longer description
     * @param habitats    a dictionary indicating the percentage of this occupant emerges on a certain type of terrain
     */
    public Occupant(Position position, String name, String description, String portrait, Map<Terrain, Double> habitats) {
        Objects.requireNonNull(habitats);

        this.position = position;
        this.name = name;
        this.description = description;
        this.portrait = portrait;
        this.habitats = habitats;
    }

    /**
     * Returns the position of the occupant.
     *
     * @return the position of the occupant
     */
    public Position getPosition() {
        return this.position;
    }

    /**
     * Changes the position of the occupant.
     *
     * @param newPosition the new position
     */
    public void setPosition(Position newPosition) {
        this.position = newPosition;
    }

    /**
     * Gets the occupant's name.
     *
     * @return the name of the occupant
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the description for the item.
     *
     * @return the description
     */
    public String getDescription() {
        return this.description;
    }

    public String getPortrait() {
        return this.portrait;
    }

    /**
     * Gets the probability of this occupant resides in a certain type of terrain.
     *
     * @param terrain the terrain type
     * @return the probability in the range between 0.0 to 1.0 (both inclusively).
     */
    public double getHabitatProbability(Terrain terrain) {
        if(habitats != null && habitats.containsKey(terrain)) {
            return habitats.get(terrain);
        }
        return 0.0;
    }

    /**
     * Returns the occupant's name for display.
     *
     * @return the occupant's name
     */
    @Override
    public String toString() {
        return getName();
    }


    /**
     * Gets a string representation of the occupant.
     * Used for interpretation of file content
     *
     * @return the string representation of the occupant
     */
    public abstract String getStringRepresentation();


}
