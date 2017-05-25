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
    private final ScalingAssistant sA;
    private final AssetManager assetMan;
    
    // tick counter for sprite switch, only counts while moving
    private int ticksElapsed;
    private final int TICKS_PER_ALTERNATION = 10; // 10 seems ok...like the player is stepping between spaces. 2 and 3 seemed ok too though, but are potentially too fast for such a subtle change
    private boolean alternate;
    
    public PlayerSprite(Game game) {
        this.game = game;
        this.player = game.getPlayer();
        this.assetMan = AssetManager.getAssetManager();
        this.sprite = assetMan.getPlayer();
        this.xLocation = getXPosition();
        this.yLocation = getYPosition();
        this.sA = ScalingAssistant.getScalingAssistant();
        this.ticksElapsed = 0;
        this.alternate = false;
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
        this.sprite = (alternate) ? assetMan.getPlayerAlt(): assetMan.getPlayer();
        player = game.getPlayer();
        // if either x or y are being updated then inMotion true and ticks elapsed is updated
        boolean mX = updateX();
        boolean mY = updateY();
        if (mX||mY) walk();
    }
    
    /**
     * Switches the sprite to give impression of walking
     */
    public void walk() {
        ticksElapsed ++;
        if (ticksElapsed > TICKS_PER_ALTERNATION) {
            alternate = !alternate;
            ticksElapsed = 0;
        }
    }
    
    /**
     * Updates the X location to be drawn at. Returns true if updated.
     */
    private boolean updateX() {
        int diff = getXPosition() - xLocation;
        if (diff == 0) return false;
        int direction = diff / ((int) Math.abs((double) diff));
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
        return true;
    }
    
    /**
     * Updates the Y location to be drawn at. Returns true if updated
     */
    private boolean updateY() {
        int diff = getYPosition() - yLocation;
        if (diff == 0) return false;
        int direction = diff / ((int) Math.abs((double) diff));
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
        return true;
    }
}
