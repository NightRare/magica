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
    
    private BufferedImage taggedBubble, trappedBubble;
    
    private boolean kiwiCounted = false;
    private boolean predatorTrapped = false;
    private static int displayTime = 50;
    
    
    
    public GameNotification(Game game) {
        this.game = game;
        taggedBubble = AssetManager.getAssetManager().getTaggedBubble();
        trappedBubble = AssetManager.getAssetManager().getTrappedBubble();
    }
    
    
    
    private int getBubblePositionOffset(){
        //reminder: do something when player is near side panel
        return GUIConfigs.colToX(player.getPosition().getColumn()-2);
    }
    
    private int getPlayerPosition_Y() {
        return GUIConfigs.rowToY(player.getPosition().getRow());
    }
    
    public void render(Graphics2D g2d) {
        //g2d.drawImage(taggedBubble, null, playerLocationX, playerLocationY);
        System.out.println(kiwiCounted);
        if(kiwiCounted){
            g2d.drawImage(taggedBubble, null, getBubblePositionOffset(),getPlayerPosition_Y());
        }
        if(predatorTrapped){
            g2d.drawImage(trappedBubble,null,getBubblePositionOffset(),getPlayerPosition_Y());
        }
    }
    
    public void update(){
        taggedBubble = AssetManager.getAssetManager().getTaggedBubble();
        trappedBubble = AssetManager.getAssetManager().getTrappedBubble();
        player = game.getPlayer();
        notificationCounter();
    }
    
    private void notificationCounter(){
        if(kiwiCounted || predatorTrapped){
            displayTime--;
            if(displayTime == 0){
                kiwiCounted = false;
                predatorTrapped = false;
                displayTime = 50;
            }
        }
        
    }
    
    public void kiwiCounted(){
        this.kiwiCounted = true;
    }
    
    public void predatorTrapped(){
        this.predatorTrapped = true;
    }
    
    
}
