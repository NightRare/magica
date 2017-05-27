/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.ense701.gui;

import java.awt.Color;
import java.awt.image.BufferedImage;
import nz.ac.aut.ense701.gameModel.Game;
import nz.ac.aut.ense701.gameModel.GameState;
import nz.ac.aut.ense701.gameModel.Player;
import nz.ac.aut.ense701.gameModel.Terrain;

/**
 * Replaces GridSquarePanel
 * @author Sam
 */
public class MapSquare {

    private Game game;
    private int row;
    private int column;

    private BufferedImage texture;

    
    private Color tileColour;
    private String label;

    
    public BufferedImage visible, water, scrub, wetland, forest, sand, dark, fog = null;
    public BufferedImage animal, food, tool, hazard;

    private AssetManager assetManager;
    
    
    public MapSquare(Game game, int row, int column)  {
        this.assetManager = AssetManager.getAssetManager();
        this.game = game;
        this.row = row;
        this.column = column;
        
        textureLoad();
        loadOccupantImage();
        initialiseOrRefresh();
    }

    /**
     * Initializes the MapSquare or Refreshes it to sync with model
     */
    
     public void textureLoad(){
        //Get Images # 5
        visible = assetManager.getVisible();
        scrub = assetManager.getScrub();
        wetland = assetManager.getWetland();
        forest = assetManager.getForest();
        sand = assetManager.getSand();
        dark = assetManager.getDark();
        fog = assetManager.getFog();
    }
     
     public void loadOccupantImage(){
        animal = assetManager.getAnimal();
        food = assetManager.getFood();
        tool = assetManager.getTool();
        hazard = assetManager.getHazard();
    }
    
    public void initialiseOrRefresh() {
        textureLoad();
        loadOccupantImage();
        
        Terrain terrain = game.getTerrain(row, column);

        boolean squareVisible = game.isVisible(row, column);
        boolean squareExplored = game.isExplored(row, column);
        
        if (squareExplored || squareVisible) {
            //shows occupants
            label = game.getOccupantStringRepresentation(row,column);
            //shows the drawn map
            texture = visible;
            //a fog shows when the player hasn't explored certain squares
            if (squareVisible && !squareExplored){ texture = fog; }
            //When the player dies, then it reverts all textures that was used to dark.
            if(game.getState()==GameState.LOST){ texture = dark; } 
        } else {
            texture = dark;
            label = "";
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

//    public Color getColour() {
//        return tileColour;
//    }
    
     public BufferedImage getTexture(){
        return texture;
    }
    
    public BufferedImage getOccupantIcon(String label){
        BufferedImage img = null;
        if (label.length() == 0) return img;
        if(label.matches("[FKP]+")) img = animal;
        if(label.matches("H")) img = hazard;
        if(label.contains("T")) img = tool;
        if(label.contains("E")) img = food;
        return img;
    }
    
}
