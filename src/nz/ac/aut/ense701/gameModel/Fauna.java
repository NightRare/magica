
package nz.ac.aut.ense701.gameModel;


import java.util.Map;

/**
 * Fauna at this point represents any species that is not a kiwi or a predator on the island.
 * If we need additional endangered species this class should have descendant classes created.
 * 
 * @author AS
 * @version July 2011
 */
public class Fauna extends Occupant
{
    private String link;
    /**
     * Constructor for objects of class Endangered
     * @param pos the position of the kiwi
     * @param name the name of the kiwi
     * @param description a longer description of the kiwi
     */
    public Fauna(Position pos, String name, String description) 
    {
        super(pos, name, description);
    } 
    
    /**
     * Constructor for objects of class Endangered
     * @param pos the position of the kiwi
     * @param name the name of the kiwi
     * @param description a longer description of the kiwi
     * @param portrait image file path of a certain animal
     * @param link a URL of Department of Conservation
     */
    
    public Fauna(Position pos, String name, String description, String portrait, String link, Map<Terrain, Double> habitats)
    {
        super(pos, name, description, portrait, habitats);
        this.link = link;
    } 
    
    public String getLink() {
        return link;
    }
    
 
    @Override
    public String getStringRepresentation() 
    {
          return "F";
    }    
}
