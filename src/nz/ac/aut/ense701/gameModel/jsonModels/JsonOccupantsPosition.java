/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.ense701.gameModel.jsonModels;

import java.util.List;
import nz.ac.aut.ense701.gameModel.*;

/**
 * A structured class for reading data from OccupantsMap.json.
 * @author Vince
 */
public class JsonOccupantsPosition {
    
    public final Position position;
    public final List<String> occupants;
    
    public JsonOccupantsPosition(Position position, List<String> occupants) {
        this.position = position;
        this.occupants = occupants;
    }
}
