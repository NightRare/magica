/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.ense701.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import nz.ac.aut.ense701.gameModel.Game;
import nz.ac.aut.ense701.gameModel.GameState;

/**
 * RenderingEngine is responsible for all graphics rendering
 * 
 * @author Sam
 */
public class RenderingEngine {

    GameLoop loop;
    GameState gamestate;
    Game game;
    
    
    public RenderingEngine(GameLoop loop) {
        this.loop = loop;
    }

    /**
     * Renders game onto a graphics object
     * @param g2d graphics2D reference
     */
    public void render(Graphics2D g2d) {
        
        ArrayList<NewMapSquare> squareList = loop.getMapSquareList();

        for (NewMapSquare square : squareList) {
            renderMapSquare(g2d, square);
        }
        
        renderSidePanel(g2d, loop.getSidePanel());
    }

    /**
     * Renders a map square
     * 
     * @param g2d graphics2D reference
     * @param square MapSquare to render
     */
    private void renderMapSquare(Graphics2D g2d, NewMapSquare square) {
        
                 
        int xToRenderAt = Globals.colToX(square.getColumn());
        int yToRenderAt = Globals.rowToY(square.getRow());
        
        int yLowered = yToRenderAt + Globals.getSquareHeight()/2;
        /*    
        g2d.fill(new RoundRectangle2D.Double(xToRenderAt, yToRenderAt,
                Globals.getSquareWidth(),
                Globals.getSquareHeight(),
                10, 10)); 
        */
        
        g2d.fill(new Rectangle2D.Double(xToRenderAt, yToRenderAt,
                Globals.getSquareWidth(),
                Globals.getSquareHeight()));         

        g2d.drawImage(square.getTexture(), null, xToRenderAt, yToRenderAt);
        
        g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
        g2d.drawString(square.getLabel(), xToRenderAt, yLowered);
       
    }
    
    private void renderSidePanel(Graphics2D g2d, SidePanel sidePanel){
        g2d.setColor(Color.yellow);
        g2d.fill(new Rectangle2D.Double(50, 50, 100, 100));
        g2d.drawRect(150, 150, 100, 100);
        
    }
}
