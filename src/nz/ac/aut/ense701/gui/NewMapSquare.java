/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.ense701.gui;

import java.awt.Color;
import java.awt.image.BufferedImage;
import nz.ac.aut.ense701.gameModel.Game;
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
    
    public BufferedImage water, scrub, wetland, forest, sand, player = null;

    public NewMapSquare(Game game, int row, int column) {
        this.game = game;
        this.row = row;
        this.column = column;
        
        textureLoad();
        initialiseOrRefresh();
    }

    /**
     * Initialises the NewMapSquare or Refreshes it to sync with model
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
            texture = image;
                
            if(game.hasPlayer(row, column)){
                texture = player;
            }
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
    
    
    
}
