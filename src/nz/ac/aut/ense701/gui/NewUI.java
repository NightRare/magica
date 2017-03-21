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
 *
 * @author Sam
 */
public class NewUI {
    
    private JFrame frame;
    private Canvas canvas;
    
    private JFrame testFrame;
    
    public NewUI(Game game) {
        //converter
        //scaler
        
        createDisplay();
        
        KiwiCountUI oldUI = new KiwiCountUI(game);
        oldUI.setVisible(true);
    }
    
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
        
        
//        // test second frame in case we need to place inventory/display panel in separate frame (I have not experimented with adding to original frame)
//        testFrame = new JFrame("Testing Second Frame");
//        testFrame.setSize(Globals.width/3, Globals.height);
//        testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        testFrame.setResizable(false);
////        testFrame.setLocationRelativeTo(null);
//        
//        
//        JButton randomButton = new JButton();
//        randomButton.setText("RANDOM BUTTON!!");
//        testFrame.add(randomButton);
//
//        testFrame.setVisible(true);
    }

    public Canvas getCanvas() {
        return canvas;
    }
    
}
