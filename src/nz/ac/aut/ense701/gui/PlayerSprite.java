/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.ense701.gui;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import nz.ac.aut.ense701.gameModel.Game;
import nz.ac.aut.ense701.gameModel.Player;

/**
 * Represents the player sprite
 * @author Sam
 */
public class PlayerSprite {
    private Player player;
    private BufferedImage sprite;
    private Game game;
    
    // location represents location to be drawn
    private int xLocation;
    private int yLocation;
    
    private static final int SPEED = 5;
    private ScalingAssistant sA;
    
    
    public PlayerSprite(Game game) {
        this.game = game;
        this.player = game.getPlayer();
        this.sprite = AssetManager.getAssetManager().getPlayer();
        this.xLocation = getXPosition();
        this.yLocation = getYPosition();
        this.sA = ScalingAssistant.getScalingAssistant();
    }
    
    // position refers to the underlying position in the game model
    private int getXPosition() {
        return GUIConfigs.colToX(player.getPosition().getColumn());
    }
    
    // position refers to the underlying position in the game model
    private int getYPosition() {
        return GUIConfigs.rowToY(player.getPosition().getRow());
    }
    
    /**
     * Render Method for drawing player sprite onto Graphics 2D
     **/
    public void render(Graphics2D g2d) {
        g2d.drawImage(sprite, null, xLocation, yLocation);
    }
    
    /**
     * Tick method for updating data associated with player sprite
     */
    public void tick() {
        this.sprite = AssetManager.getAssetManager().getPlayer();
        player = game.getPlayer();
        updateX();
        updateY();
    }
    
    /**
     * Updates the X location to be drawn at
     */
    private void updateX() {
        int x = xLocation;
        int diff = getXPosition() - xLocation;
        int direction = (diff != 0) ? diff / ((int) Math.abs((double) diff)) : 0;
        int speed = sA.scale(SPEED);
        if (speed < Math.abs(diff)) {
            xLocation += speed * direction;
        } else {
            xLocation += diff;
        }
        
        // to avoid strange effect where sprite can lag too far behind actual location
        if ((double) Math.abs(diff) > GUIConfigs.getSquareWidth() * 1.5) {
            xLocation = getXPosition();
        }
    }
    
    /**
     * Updates the Y location to be drawn at
     */
    private void updateY() {
        int y = yLocation;
        int diff = getYPosition() - yLocation;
        int direction = (diff != 0) ? diff / ((int) Math.abs((double) diff)): 0;
        int speed = sA.scale(SPEED);
        if (speed < Math.abs(diff)) {
            yLocation += speed * direction;
        } else {
            yLocation += diff;
        }
        
        // To avoid strange effect where sprite can lag too far behind the actual location
        if ((double) Math.abs(diff) > GUIConfigs.getSquareHeight() * 1.5) {
            yLocation = getYPosition(); 
        }
    }
}
