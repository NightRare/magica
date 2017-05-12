/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.ense701.gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import nz.ac.aut.ense701.gameModel.Game;
import nz.ac.aut.ense701.gameModel.MoveDirection;

/**
 *
 * @author Sam
 */
public class NavigationKeyListener implements KeyListener{
    
    Game game;
    GameLoop loop;
    
    public NavigationKeyListener(Game game, GameLoop loop) {
        this.game = game;
    }
    
    /**
     * Moves the player
     * @param direction the direction to move the player. 
     */
    private void move(MoveDirection direction){
        loop.getSidePanel().clearInfoOccupant(); // hack fix for info occupant being retained
        if (this.game.isPlayerMovePossible(direction)) {
            game.playerMove(direction);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        
        switch (key) {
            case KeyEvent.VK_W:
                move(MoveDirection.NORTH);
                break;
            case KeyEvent.VK_A: 
                move(MoveDirection.WEST);
                break;
            case KeyEvent.VK_S: 
                move(MoveDirection.SOUTH);
                break;
            case KeyEvent.VK_D: 
                move(MoveDirection.EAST);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    
}
