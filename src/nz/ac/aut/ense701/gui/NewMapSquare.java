/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.ense701.gui;

import java.awt.Color;
import nz.ac.aut.ense701.gameModel.Game;
import nz.ac.aut.ense701.gameModel.Terrain;

/**
 *
 * @author Sam
 */
public class NewMapSquare {

    private Game game;
    private int row;
    private int column;

    // New Fields needed
    // recommend getting a field for the background texture (image object of some sort...buffered image?)
    // get a list of buffered images in the order they are to be drawn. This way the RenderingEngine can grab the list and draw them in order.
    // Temp Fields
    private Color colour;
    private String label;

    public NewMapSquare(Game game, int row, int column) {
        this.game = game;
        this.row = row;
        this.column = column;
        
        initialiseOrRefresh();
    }

    public void initialiseOrRefresh() {
        Terrain terrain = game.getTerrain(row, column);

        boolean squareVisible = game.isVisible(row, column);
        boolean squareExplored = game.isExplored(row, column);

        switch (terrain) {
            case SAND:
                colour = Color.YELLOW;
                break;
            case FOREST:
                colour = Color.GREEN;
                break;
            case WETLAND:
                colour = Color.BLUE;
                break;
            case SCRUB:
                colour = Color.DARK_GRAY;
                break;
            case WATER:
                colour = Color.CYAN;
                break;
            default:
                colour = Color.LIGHT_GRAY;
                break;
        }

        // This code needs to be changed eventually once colours are moved away from
        if (squareExplored || squareVisible) {
            
            label = new String(game.getOccupantStringRepresentation(row,column));
            
            if ( squareVisible && !squareExplored ) 
            {
                // When explored the colour is brighter
                colour = new Color(Math.min(255, colour.getRed()   + 128), 
                                  Math.min(255, colour.getGreen() + 128), 
                                  Math.min(255, colour.getBlue()  + 128));
            }
        } else {
            
            // This needs to assign string to "" normally
            label = game.getOccupantStringRepresentation(row,column);
        }
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public String getLabel() {
        return label;
    }

    public Color getColour() {
        return colour;
    }
    
    
}
