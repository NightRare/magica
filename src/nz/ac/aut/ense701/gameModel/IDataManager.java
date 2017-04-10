/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.ense701.gameModel;

import java.util.Set;

/**
 * This interface processes the requests for persistent data.
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
    
}
