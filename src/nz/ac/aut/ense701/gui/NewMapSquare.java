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
public class NewMapSquare {

    private Game game;
    private int row;
    private int column;

    private BufferedImage texture;

    
    private Color tileColour;
    private String label;

    
    public BufferedImage water, scrub, wetland, forest, sand, player, black, grey = null;
    public BufferedImage animal, food, tool, hazard;

    public NewMapSquare(Game game, int row, int column) {
        this.game = game;
        this.row = row;
        this.column = column;
        
        textureLoad();
        loadOccupantImage();
        initialiseOrRefresh();
    }

    /**
     * Initializes the NewMapSquare or Refreshes it to sync with model
     */
    
     public void textureLoad(){
        
        //Get Images # 5
        BufferedImageLoader loader = new BufferedImageLoader();
            try{
            water = loader.loadImage("/resource/images/tile_water.png");
            scrub = loader.loadImage("/resource/images/tile_scrub.png");
            wetland = loader.loadImage("/resource/images/tile_wetland.png");
            forest = loader.loadImage("/resource/images/tile_forest.png");
            sand = loader.loadImage("/resource/images/tile_sand.png");
            player = loader.loadImage("/resource/images/player_01.png");
            black = loader.loadImage("/resource/images/black.png");
            grey = loader.loadImage("/resource/images/grey.png");
            }
            catch(Exception e){
                e.printStackTrace();
            }
    }
     
     public void loadOccupantImage(){
        
        BufferedImageLoader loader = new BufferedImageLoader();
            try{
            animal = loader.loadImage("/resource/images/animal.png");
            food = loader.loadImage("/resource/images/food.png");
            tool = loader.loadImage("/resource/images/tool.png");
            hazard = loader.loadImage("/resource/images/hazard.png");
            }
            catch(Exception e){
                e.printStackTrace();
            }
    }
    
    public void initialiseOrRefresh() {
        Terrain terrain = game.getTerrain(row, column);

        boolean squareVisible = game.isVisible(row, column);
        boolean squareExplored = game.isExplored(row, column);
        
        Color colour;
        
        BufferedImage image;
        

        switch (terrain) {
                       
            case SAND:
                image = sand;
                break;
            case FOREST:
                image = forest;
                break;
            case WETLAND:
                image = wetland;
                break;
            case SCRUB:
                image = scrub;
                break;
            case WATER:
                image = water;
                break;
            default:
                image = water;
                break;
        }

        // This code needs to be changed eventually once colours are moved away from
     
        
        if (squareExplored || squareVisible) {
            
            label = game.getOccupantStringRepresentation(row,column);
            
            
            if ( squareVisible && !squareExplored ) 
            {
              
            }
            texture = image; //Sets up appropriate textures for the map
            
            
            if(game.hasPlayer(row, column)){
                texture = player; //Sets up Player Icon to the current location of the player.
            } 
            if(game.getState()==GameState.LOST){
                texture = grey; } //When the player dies, then it reverts all textures that was used to grey.

        } else {
            
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

    public Color getColour() {
        return tileColour;
    }
    
     public BufferedImage getTexture(){
        return texture;
    }
    
    public BufferedImage getOccupantImage(String label){
        BufferedImage img = null;
        switch(label){
            case "F":
                img = animal;
                break;
            case "K":
                img = animal;
                break;
            case "P":
                img = animal;
                break;
            case "H":
                img = hazard;
                break;
            case "T":
                img = tool;
                break;
            case "E":
                img = food;
                break;
        }
        return img;
    }
    
}
