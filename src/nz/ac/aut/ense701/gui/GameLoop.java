/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.ense701.gui;

import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import nz.ac.aut.ense701.gameModel.Game;

/**
 * GameLoop class implements a game loop to update and render game
 * @author Sam
 */
public class GameLoop implements Runnable{

    private Game game;
    private GUI newUI;
    private Thread thread;
    
    // The graphics 2D will be taken from the canvas of the user interface and a bufferstrategy applied
    private BufferStrategy bs;
    private Graphics2D g2d;
    
    // Tick machine will be responsible for updating data and rendering engine will draw the game to the graphics2D object
    private TickMachine tickMachine;
    private RenderingEngine renderingEngine;
    
    
    
    private boolean running;
    
    private ArrayList<MapSquare> mapSquareList;
    private SidePanel sidePanel;
    PlayerSprite playerSprite;

    /**
     * The constructor for GameLoop takes a game object. Game represents the game model.
     * @param game 
     */
    public GameLoop(Game game) {
        this.game = game;
        this.running = false;
        tickMachine = new TickMachine(this);
        renderingEngine = new RenderingEngine(this);
        playerSprite = new PlayerSprite(game);
        
        // Initialise the map grid
        initialiseMapSquareList();
    }

    @Override
    public void run() {
        initialise();

        
        //frames per second
        int fps = 60;
        //time per tick (nano seconds)
        double timePerTick = 1000000000 / fps;
        //delta
        double delta = 0;
        //current time
        long now;
        long lastTime = System.nanoTime();

        while (running) {
            now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            lastTime = now;

            if (delta >= 1) {
                tick();
                render();
                delta--;
            }

            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
            }
        }
        stop();
    }
    
    /**
     * Makes any updates required before rendering
     */
    private void tick() {
        tickMachine.tick();
       
    }
    
    /**
     * Renders the game
     */
    private void render() {
        bs = newUI.getCanvas().getBufferStrategy();
        if (bs == null) {
            newUI.getCanvas().createBufferStrategy(2);
            return;
        }
        g2d = (Graphics2D) bs.getDrawGraphics();
        
        g2d.clearRect(0, 0, GUIConfigs.width, GUIConfigs.height);
        
        renderingEngine.render(g2d);
        
        bs.show();
        g2d.dispose();
    }
    
    /**
     * Initialises the UI and Assets
     */
    private void initialise() {
        newUI = new GUI(game, this);
        
        this.sidePanel = new SidePanel(game);
        // Initialise Assets
    }
    
    /**
     * Starts the game loop
     */
    public void start() {
        if (running) {
            return;
        }
        running = true;
        thread = new Thread(this);
        thread.start();
    }
    
    /**
     * Stops the game loop
     */
    private void stop() {
        running = false;
        if (!running) {
            return;
        }
        try {
            thread.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Initialises a collection of Map Squares
     */
    private void initialiseMapSquareList() {
        mapSquareList = new ArrayList();
        
        int rows    = game.getNumRows();
        int columns = game.getNumColumns();
        
        for ( int row = 0 ; row < rows ; row++ )
        {
            for ( int col = 0 ; col < columns ; col++ )
            {
                mapSquareList.add(new MapSquare(game, row, col));
            }
        }
    }

    public ArrayList<MapSquare> getMapSquareList() {
        return mapSquareList;
    }
    
    public final SidePanel getSidePanel(){
        return this.sidePanel;
    }
    
    public boolean getRunning(){
        return running;
    }

    public Game getGame() {
        return game;
    }
    
    public PlayerSprite getPlayerSprite() {
        return this.playerSprite;
    }
    
    
}