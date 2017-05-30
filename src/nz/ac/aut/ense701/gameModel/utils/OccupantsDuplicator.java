package nz.ac.aut.ense701.gameModel.utils;

import nz.ac.aut.ense701.gameModel.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * An occupants duplicator which produces deep clone of an Occupant instance.
 * <p>
 * Created by Yuan on 2017/5/10.
 */
public class OccupantsDuplicator {

    /**
     * Return a deep clone of the given Occupant.
     *
     * @param prototypes the template to be duplicated from
     * @return the duplicate Occupant
     * @throws NullPointerException if template is {@code null}
     */
    public static Occupant duplicate(Occupant prototypes) {
        Objects.requireNonNull(prototypes);

        Position position = prototypes.getPosition();
        String name = prototypes.getName();
        String description = prototypes.getDescription();
        String portrait = prototypes.getPortrait();
        Map<Terrain, Double> habitats = duplicateHabitats(prototypes);

        if (prototypes instanceof Hazard) {
            Hazard hazard = (Hazard) prototypes;
            return new Hazard(position, name, description, portrait, hazard.getImpact(), habitats);
        }

        if (prototypes instanceof Item) {
            Item item = (Item) prototypes;
            double weight = item.getWeight();
            double size = item.getSize();

            if (prototypes instanceof Food) {
                Food food = (Food) prototypes;
                return new Food(position, name, description, portrait, weight, size, food.getEnergy(), habitats);
            }

            if (prototypes instanceof Tool) {
                return new Tool(position, name, description, portrait, weight, size, habitats);
            }

            throw new IllegalArgumentException("An Item (abstract class) object cannot "
                    + "be cloned.");
        }

        if (prototypes instanceof Fauna) {

            String link = ((Fauna) prototypes).getLink();

            if (prototypes instanceof Kiwi) {
                return new Kiwi(position, name, description, portrait, link, habitats);
            }

            if (prototypes instanceof Predator) {
                return new Predator(position, name, description, portrait, link, habitats);
            }

            return new Fauna(position, name, description, portrait, link, habitats);
        }

        throw new IllegalArgumentException("An Occupant (abstract class) object "
                + "cannot be cloned.");
    }

    /**
     * Return a list of deep clones of the given Occupant. The list does not include the template itself.
     *
     * @param prototypes the template to be duplicated from
     * @param amount the amount of duplicates
     * @return the duplicate list of Occupants
     * @throws NullPointerException if template is {@code null}
     * @throws IllegalArgumentException if amount < 1
     */
    public static Occupant[] duplicateMulti(Occupant prototypes, int amount) {
        Objects.requireNonNull(prototypes);
        if (amount < 1)
            throw new IllegalArgumentException("The amount must be a positive integer.");

        Occupant[] duplicates = new Occupant[amount];

        for (int i = 0; i < duplicates.length; i++) {
            duplicates[i] = duplicate(prototypes);
        }

        return duplicates;
    }

    private static Map<Terrain, Double> duplicateHabitats(Occupant prototypes) {
        Map<Terrain, Double> dup = new HashMap<>();
        for(Terrain t : Terrain.values()) {
            dup.put(t, prototypes.getHabitatProbability(t));
        }
        return dup;
    }

}
