/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.ense701.gameModel;


import java.util.List;
import java.util.Set;

/**
 * This interface processes the requests for persistent data. All the data got from
 * the IDataManager should be a deep clone of the data persists in IDataManager instance.
 * 
 * @author Yuan
 */
public interface IDataManager {
    
    /**
     * Get a set of occupants reside in a given position.
     * 
     * @param position the position which the occupants reside in
     * @return the set of occupants.
     * @throws IllegalArgumentException if position is {@code null}.
     */
    public Set<Occupant> getOccupantsInPosition(Position position);
    
    /**
     * Get all the Occupant prototype objects which are defined in
     * data/Occupants.json. Changes to the objects in the set won't affect the
     * original set.
     * 
     * @return a set of Occupant prototypes.
     */
    public Set<Occupant> getAllOccupantPrototypes();

    /**
     * Get all the Occupant instances in which same type of occupants are ordered together.
     *
     * @return the list of all Occupant instances.
     */
    public List<Occupant> getAllOccupantInstances();
}
