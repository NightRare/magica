/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.ense701.gui;

import java.util.ArrayList;
import nz.ac.aut.ense701.gameModel.Game;

/**
 *
 * @author Sam
 */
public class TickMachine {

    GameLoop loop;

    public TickMachine(GameLoop loop) {
        this.loop = loop;
    }

    public void tick() {
        
        ArrayList<NewMapSquare> squareList = loop.getMapSquareList();

        for (NewMapSquare square : squareList) {
            square.initialiseOrRefresh();
        }
        
    }
}
