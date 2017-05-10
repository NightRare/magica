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
 * @author DonnaCello
 */
public class GameNotification {
    
    
    private final Game game;
    private Player player;
    
    private final ScalingAssistant scaleAssist;
    private BufferedImage taggedBubble, trappedBubble;
    
    
    private int playerLocationX;
    private int playerLocationY;
    private int bubblePositionOffset;
    private boolean kiwiCounted = false;
    private static int displayTime = 50;
    
    
    
    public GameNotification(Game game) {
        this.game = game;
        player = game.getPlayer();
        playerLocationX = getPlayerPosition_X();
        playerLocationY = getPlayerPosition_Y();
        bubblePositionOffset = getBubblePositionOffset();
        scaleAssist = ScalingAssistant.getScalingAssistant();
        taggedBubble = AssetManager.getAssetManager().getTaggedBubble();
        trappedBubble = AssetManager.getAssetManager().getTrappedBubble();
    }
    
    private int getPlayerPosition_X() {
        return GUIConfigs.colToX(player.getPosition().getColumn());
    }
    
    private int getPlayerPosition_Y() {
        return GUIConfigs.rowToY(player.getPosition().getRow());
    }
    
    private int getBubblePositionOffset(){
        //reminder: do something when player is near side panel
        return GUIConfigs.colToX(player.getPosition().getColumn()-2);
    }
    
    public void render(Graphics2D g2d) {
        //g2d.drawImage(taggedBubble, null, playerLocationX, playerLocationY);
        if(kiwiCounted){
            g2d.drawImage(taggedBubble, null, getBubblePositionOffset(),getPlayerPosition_Y());
        }
        
        
        
    }
    
    public void update(){
        taggedBubble = AssetManager.getAssetManager().getTaggedBubble();
        trappedBubble = AssetManager.getAssetManager().getTrappedBubble();
        notificationCounter();
    }
    
    private void notificationCounter(){
        if(kiwiCounted){
            displayTime--;
            if(displayTime == 0){
                kiwiCounted = false;
                displayTime = 50;
            }
        }
        
    }
    
    public void kiwiCounted(){
        this.kiwiCounted = true;
    }
    
    
}
