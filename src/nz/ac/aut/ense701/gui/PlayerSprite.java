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
 *
 * @author Sam
 */
public class PlayerSprite {
    private Player player;
    private BufferedImage sprite;
    private Game game;
    
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
    
    private int getXPosition() {
        return GUIConfigs.colToX(player.getPosition().getColumn());
    }
    
    private int getYPosition() {
        return GUIConfigs.rowToY(player.getPosition().getRow());
    }
    
    public void render(Graphics2D g2d) {
        g2d.drawImage(sprite, null, xLocation, yLocation);
    }
    
    public void tick() {
        player = game.getPlayer();
//        xLocation = getXPosition();
//        yLocation = getYPosition();
        updateX();
        updateY();
    }
    
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
    }
    
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
    }
}
