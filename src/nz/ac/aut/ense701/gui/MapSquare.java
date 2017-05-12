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

    
    public BufferedImage empty, water, scrub, wetland, forest, sand, black, grey = null;
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
        empty = assetManager.getEmpty();
        scrub = assetManager.getScrub();
        wetland = assetManager.getWetland();
        forest = assetManager.getForest();
        sand = assetManager.getSand();
        black = assetManager.getBlack();
        grey = assetManager.getGrey();
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
        
        //Color colour;
        
        //BufferedImage image;
        

//        switch (terrain) {
//                       
//            case SAND:
//                image = sand;
//                break;
//            case FOREST:
//                image = forest;
//                break;
//            case WETLAND:
//                image = wetland;
//                break;
//            case SCRUB:
//                image = scrub;
//                break;
//            case WATER:
//                image = water;
//                break;
//            default:
//                image = water;
//                break;
//        }

        // This code needs to be changed eventually once colours are moved away from
     
        
        if (squareExplored || squareVisible) {
            
            label = game.getOccupantStringRepresentation(row,column);
            //texture = empty;
            
            if (squareVisible && !squareExplored) 
            {
              //texture = empty;
            }
            //texture = empty; //Sets up appropriate textures for the map
            
            if(game.getState()==GameState.LOST){
                //texture = grey; 
            } //When the player dies, then it reverts all textures that was used to grey.

        } else {
            
            //texture = black;
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
        if(label.matches("T")) img = tool;
        if(label.matches("E")) img = food;
        return img;
    }
    
}
