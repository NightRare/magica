/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.ense701.gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JOptionPane;
import nz.ac.aut.ense701.gameModel.Game;

/**
 *
 * @author Vince
 */
public class ShortcutListener implements KeyListener{

    private Game game;
    
    public ShortcutListener(Game game) {
        this.game = game;
    }
    
    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e) { 
        switch(e.getKeyCode()) {
            case KeyEvent.VK_C:
            {
                collectItem();
            }
            case KeyEvent.VK_T:
            {
                tagKiwi();
            }
        }        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            int result = JOptionPane.showConfirmDialog(null, 
                    "Do you want to exit?", "Exit the game.", 
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
            
            if(result == JOptionPane.OK_OPTION)
                System.exit(0);
        }
        
    }
    
    private void collectItem() {
        // temporarily set to one of the occupants in the square
        Object obj = (game.getOccupantsPlayerPosition())[0];
        
        game.collectItem(obj);
    }
    
    private void tagKiwi() {
        game.countKiwi();
    }
}
