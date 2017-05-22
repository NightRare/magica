/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.ense701.gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Objects;
import javax.swing.JOptionPane;
import org.lwjgl.openal.AL;

/**
 *
 * @author Vince
 */
public class ShortcutListener implements KeyListener{

    private ClickListener mouseActions;
    
    /**
     * Initialises a ShortcutListener which provides a more convenient way to perform
     * certain types of actions.
     * 
     * @param mouseActions the ClickListener in which all the interactive actions
     *        are defined and implemented
     */
    public ShortcutListener(ClickListener mouseActions) {
        Objects.requireNonNull(mouseActions);
        this.mouseActions = mouseActions;
    }
    
    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e) { 
        switch(e.getKeyCode()) {
            case KeyEvent.VK_C:
            {
                mouseActions.performCollecting();
                break;
            }
            case KeyEvent.VK_T:
            {
                mouseActions.performTagging();
                break;
            }
        }        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            int result = JOptionPane.showConfirmDialog(null, 
                    "Do you want to exit?", "Exit the game.", 
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
            
            if(result == JOptionPane.OK_OPTION){
                 AL.destroy();
                System.exit(0);
            }           
        }        
    }
}
