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
        //renders Side Panel
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
    
    /**
     * Renders a Side Panel
     * 
     * @param g2d graphics2D reference
     * @param sidePanel side panel object
     */
    private void renderSidePanel(Graphics2D g2d, SidePanel sidePanel){
        g2d.setColor(Color.white);
        //display Action Board area
        g2d.fill(new Rectangle2D.Double(Globals.boardOffsetX(),Globals.boardOffsetY(),
                                        Globals.boardWidth(),Globals.boardHeight()));
        //display Info Board area
        g2d.fill(new Rectangle2D.Double(
            Globals.boardOffsetX(),(Globals.boardOffsetY()*2)+Globals.boardHeight(),
            Globals.boardWidth(),Globals.boardHeight()));
        
        ScalingAssistant scaleAssist = ScalingAssistant.getScalingAssistant();
        //display images
        g2d.drawImage(sidePanel.showPlayerIcon(), 
                scaleAssist.scale(12), scaleAssist.scale(12), //X & Y offset
                scaleAssist.scale(170), scaleAssist.scale(155), //width & height
                null);
        g2d.drawImage(sidePanel.showQuests(), 
                scaleAssist.scale(12+170), scaleAssist.scale(12), //X & Y offset 
                scaleAssist.scale(105), scaleAssist.scale(155), //width & height
                null);
        //display number of Kiwis to tag/count
        g2d.setColor(Color.gray);
        g2d.setFont(new Font("Arial",Font.BOLD,scaleAssist.scale(20)));
        g2d.drawString(sidePanel.numOfKiwi(), 
                        scaleAssist.scale(12+170+60), scaleAssist.scale(88));//X & Y offset
        g2d.drawString(sidePanel.numOfPredator(), 
                        scaleAssist.scale(12+170+60), scaleAssist.scale(135));//X & Y offset
        //display Stamina Bar
        g2d.setColor(Color.gray);
        //g2d.fill(new Rectangle2D.Double(scaleAssist.scale(20), scaleAssist.scale(20), //X & Y offset
        //                    scaleAssist.scale(20), scaleAssist.scale(20)));//width & height
        
    }
    
    private void renderBackground(Graphics2D g2d){
        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, 1100, 800);
    }
}
