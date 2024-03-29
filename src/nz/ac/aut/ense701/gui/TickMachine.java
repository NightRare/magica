    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.ense701.gui;

import java.util.ArrayList;

/**
 * Responsible for making updates required before rendering each frame
 * 
 * @author Sam
 */
public class TickMachine {

    GameLoop loop;

    public TickMachine(GameLoop loop) {
        this.loop = loop;
    }

    public void tick() {
        
        // Updates each map square to reflect any changes in Game Model
        ArrayList<MapSquare> squareList = loop.getMapSquareList();
        for (MapSquare square : squareList) {
            square.initialiseOrRefresh();
        }
        
        //update side panel
        loop.getSidePanel().checkStats();
        
        //update player sprite
        loop.getPlayerSprite().tick();
        
        //update notifications
        loop.getGame().getNotification().update();
    }
}
