/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.ense701.gui;

import java.awt.Canvas;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JFrame;
import nz.ac.aut.ense701.gameModel.Game;

/**
 * Replacement User Interface
 * 
 * @author Sam
 */
public class NewUI {
    
    private JFrame frame;
    private Canvas canvas;
    
    private JFrame testFrame;
    
    public NewUI(Game game) {
        createDisplay();
        
        KiwiCountUI oldUI = new KiwiCountUI(game);
        oldUI.setVisible(true);
    }
    
    /**
     * Creates the frame and populates it
     */
    private void createDisplay() {
        frame = new JFrame(Globals.TITLE);
        frame.setSize(Globals.width, Globals.height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        
        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(Globals.width, Globals.height));
        canvas.setMaximumSize(new Dimension(Globals.width, Globals.height));
        canvas.setMinimumSize(new Dimension(Globals.width, Globals.height));
        
        // add mouse listeners and keypressed listeners here
        
        frame.add(canvas);
        frame.pack();
        frame.setVisible(true);
    }

    public Canvas getCanvas() {
        return canvas;
    }
    
}
