/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.ense701.gui;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import nz.ac.aut.ense701.gameModel.Game;

/**
 * Replacement User Interface
 * 
 * @author Sam
 */
public class GUI {
    
    private JFrame frame;
    private Canvas canvas;
    
    private JMenuBar menuBar;
    private JMenu menu, subMenu;
    private JMenuItem menuItem;
    private ButtonGroup group;
    
    private Game game;
    private GameLoop loop;
    
    public GUI(Game game, GameLoop loop) {
        this.game = game;
        this.loop = loop;
        //KiwiCountUI oldUI = new KiwiCountUI(game);
        //oldUI.setVisible(true);
        createMenus();
        createDisplay();
    }
    
    /**
     * Creates the frame and populates it
     */
    private void createDisplay() {
        frame = new JFrame(GUIConfigs.TITLE);
        frame.setSize(GUIConfigs.WINDOW_WIDTH, GUIConfigs.WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        
        canvas = new Canvas();
        canvas.setIgnoreRepaint(true);
        canvas.setPreferredSize(new Dimension(GUIConfigs.WINDOW_WIDTH, GUIConfigs.WINDOW_HEIGHT));
        canvas.setMaximumSize(new Dimension(GUIConfigs.WINDOW_WIDTH, GUIConfigs.WINDOW_HEIGHT));
        canvas.setMinimumSize(new Dimension(GUIConfigs.WINDOW_WIDTH, GUIConfigs.WINDOW_HEIGHT));
        
        // add mouse listeners and keypressed listeners here
        NavigationKeyListener keyListener = new NavigationKeyListener(game);
        ClickListener clickListener = new ClickListener(game, loop);
        ShortcutListener shortcutListener = new ShortcutListener(game);
        
        frame.addKeyListener(keyListener);
        canvas.addKeyListener(keyListener);
        canvas.addMouseListener(clickListener);
        frame.addKeyListener(shortcutListener);
        canvas.addKeyListener(shortcutListener);
        
        
        // add keylisterner to exit the game
        
        
        
        frame.setJMenuBar(menuBar);
        frame.add(canvas);
        frame.pack();
        frame.setVisible(true);
    }
    
    private void createMenus() {
        menuBar = new JMenuBar();
        
        // settings menu
        menu = new JMenu("Settings");
        menuBar.add(menu);
        
        // window size
        subMenu = new JMenu("Window Size");
        menu.add(subMenu);
        group = new ButtonGroup();
        
        
        JRadioButtonMenuItem rbMenuItem;
        ScalingAssistant scalingAssistant = ScalingAssistant.getScalingAssistant();
        
        rbMenuItem = new JRadioButtonMenuItem ("120%");
        if(scalingAssistant.getScale()==120) rbMenuItem.setSelected(true);
        subMenu.add(rbMenuItem);
        group.add(rbMenuItem);
        rbMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scaleAndRefresh(120);
            }    
        });
        
        
        rbMenuItem = new JRadioButtonMenuItem ("100%");
        if(scalingAssistant.getScale()==100) rbMenuItem.setSelected(true);
        subMenu.add(rbMenuItem);
        group.add(rbMenuItem);
        rbMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scaleAndRefresh(100);
            }
        });
        
        rbMenuItem = new JRadioButtonMenuItem("80%");
        if(scalingAssistant.getScale()==80) rbMenuItem.setSelected(true);
        subMenu.add(rbMenuItem);
        group.add(rbMenuItem);
        rbMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scaleAndRefresh(80);
            }
        });
        
        rbMenuItem = new JRadioButtonMenuItem("50%");
        if(scalingAssistant.getScale()==50) rbMenuItem.setSelected(true);
        subMenu.add(rbMenuItem);
        group.add(rbMenuItem);
        rbMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scaleAndRefresh(50);
            }
        });
    }

    public Canvas getCanvas() {
        return canvas;
    }
    
    /**
     * Scales the game by a percentage
     * @param percentage the percentage to scale by
     */
    private void scaleAndRefresh(int percentage){
        ScalingAssistant.getScalingAssistant().setScale(percentage);
        AssetManager.getAssetManager().loadTextures();
        refreshScale();
    }
    
    /**
     * Refreshes the GUI to the current scale
     */
    private void refreshScale() {
        frame.setVisible(false);
        
        ScalingAssistant scaler = ScalingAssistant.getScalingAssistant();
        int newWidth = scaler.scale(GUIConfigs.WINDOW_WIDTH);
        int newHeight = scaler.scale(GUIConfigs.WINDOW_HEIGHT);
        frame.setSize(newWidth, newHeight);
        canvas.setPreferredSize(new Dimension(newWidth, newHeight));
        canvas.setMaximumSize(new Dimension(newWidth, newHeight));
        canvas.setMinimumSize(new Dimension(newWidth, newHeight));
        
        frame.repaint();
        frame.pack();
        frame.setVisible(true);
    }
}
