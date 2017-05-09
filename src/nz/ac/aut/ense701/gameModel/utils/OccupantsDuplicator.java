package nz.ac.aut.ense701.gameModel.utils;

import nz.ac.aut.ense701.gameModel.*;

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
     * @param template the template to be duplicated from
     * @return the duplicate Occupant
     * @throws NullPointerException if template is {@code null}
     */
    public static Occupant duplicate(Occupant template) {
        Objects.requireNonNull(template);

        Position position = template.getPosition();
        String name = template.getName();
        String description = template.getDescription();
        String portrait = template.getPortrait();

        if (template instanceof Hazard) {
            Hazard hazard = (Hazard) template;
            return new Hazard(position, name, description, portrait, hazard.getImpact());
        }

        if (template instanceof Item) {
            Item item = (Item) template;
            double weight = item.getWeight();
            double size = item.getSize();

            if (template instanceof Food) {
                Food food = (Food) template;
                return new Food(position, name, description, portrait, weight, size, food.getEnergy());
            }

            if (template instanceof Tool) {
                return new Tool(position, name, description, portrait, weight, size);
            }

            throw new IllegalArgumentException("An Item (abstract class) object cannot "
                    + "be cloned.");
        }

        if (template instanceof Fauna) {

            String link = ((Fauna) template).getLink();

            if (template instanceof Kiwi) {
                return new Kiwi(position, name, description, portrait, link);
            }

            if (template instanceof Predator) {
                return new Predator(position, name, description, portrait, link);
            }

            return new Fauna(position, name, description, portrait, link);
        }

        throw new IllegalArgumentException("An Occupant (abstract class) object "
                + "cannot be cloned.");
    }

    /**
     * Return a list of deep clones of the given Occupant.
     *
     * @param template the template to be duplicated from
     * @param amount the amount of duplicates
     * @return the duplicate list of Occupants
     * @throws NullPointerException if template is {@code null}
     * @throws IllegalArgumentException if amount < 1
     */
    public static Occupant[] duplicatMulti(Occupant template, int amount) {
        Objects.requireNonNull(template);
        if (amount < 1)
            throw new IllegalArgumentException("The amount must be a positive integer.");

        Occupant[] duplicates = new Occupant[amount];

        for (int i = 0; i < duplicates.length; i++) {
            duplicates[i] = duplicate(template);
        }

        return duplicates;
    }

}
