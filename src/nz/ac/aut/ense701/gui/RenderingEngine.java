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
        //g2d.drawString(square.getLabel(), xToRenderAt, yLowered);
        
        //drawing the occupants
        ScalingAssistant scaleAssist = ScalingAssistant.getScalingAssistant();
        g2d.drawImage(square.getOccupantImage(square.getLabel()), 
                xToRenderAt, yToRenderAt,
                scaleAssist.scale(50),scaleAssist.scale(50),null);
       
    }
    
    /**
     * Renders a Side Panel
     * 
     * @param g2d graphics2D reference
     * @param sidePanel side panel object
     */
    private void renderSidePanel(Graphics2D g2d, SidePanel sidePanel){
        ScalingAssistant scaleAssist = ScalingAssistant.getScalingAssistant();
        renderBoards(g2d);
        renderPlayerIcon(g2d,sidePanel,scaleAssist);
        renderQuest(g2d,sidePanel,scaleAssist);
        renderStaminaBar(g2d,sidePanel,scaleAssist);
        renderInventoryGroup(g2d,sidePanel,scaleAssist);
        renderActionGroup(g2d,sidePanel,scaleAssist);
        renderOccupants(g2d, sidePanel, scaleAssist);
    }
    
    /**
     * Renders the boards for the Side Panel
     * 
     * @param g2d graphics2D reference
     */
    private void renderBoards(Graphics2D g2d){
        g2d.setColor(Color.white);
        //display Action Board area
        g2d.fill(new Rectangle2D.Double(Globals.boardOffsetX(),Globals.boardOffsetY(),
                                        Globals.boardWidth(),Globals.boardHeight()));
        //display Info Board area
        g2d.fill(new Rectangle2D.Double(
            Globals.boardOffsetX(),(Globals.boardOffsetY()*2)+Globals.boardHeight(),
            Globals.boardWidth(),Globals.boardHeight()));
    }
    
    /**
     * Renders a Player Icon for the Side Panel
     * 
     * @param g2d graphics2D reference
     * @param sidePanel side panel object
     * @param scAs scaling assistant
     */
    private void renderPlayerIcon(Graphics2D g2d, SidePanel sidePanel,ScalingAssistant scAs){
        //display images
        g2d.drawImage(sidePanel.showPlayerIcon(), 
                scAs.scale(12), scAs.scale(12), //X & Y offset
                scAs.scale(170), scAs.scale(155), //width & height
                null);
    }
    
    /**
     * Renders the Quest Panel for the Side Panel
     * 
     * @param g2d graphics2D reference
     * @param sidePanel side panel object
     * @param scAs scaling assistant
     */
    private void renderQuest(Graphics2D g2d, SidePanel sidePanel,ScalingAssistant scaleAssist){
        //displays the quest clipboard image
        g2d.drawImage(sidePanel.showQuests(), 
                scaleAssist.scale(12+170), scaleAssist.scale(12), //X & Y offset 
                scaleAssist.scale(105), scaleAssist.scale(155), //width & height
                null);
        g2d.setColor(Color.gray);
        g2d.setFont(new Font("Arial",Font.BOLD,scaleAssist.scale(18)));
        //display number of Kiwis to tag/count
        g2d.drawString(sidePanel.numOfKiwi(), 
                        scaleAssist.scale(12+170+65), scaleAssist.scale(90));//X & Y offset
        //display number of Predators left
        g2d.drawString(sidePanel.numOfPredator(), 
                        scaleAssist.scale(12+170+65), scaleAssist.scale(137));//X & Y offset
    }
    
    /**
     * Renders a Stamina Bar for the Side Panel
     * 
     * @param g2d graphics2D reference
     * @param sidePanel side panel object
     * @param scAs scaling assistant
     */
    private void renderStaminaBar(Graphics2D g2d, SidePanel sidePanel,ScalingAssistant scaleAssist){
        //display Max Stamina
        g2d.setColor(Color.lightGray);
        g2d.fill(new Rectangle2D.Double(scaleAssist.scale(45), scaleAssist.scale(170), //X & Y offset
                            scaleAssist.scale(sidePanel.totalStamina()*2), scaleAssist.scale(20)));//width & height
        //displays Current Stamina
        g2d.setColor(new Color(57,181,75));
        g2d.fill(new Rectangle2D.Double(scaleAssist.scale(45), scaleAssist.scale(170), //X & Y offset
                            scaleAssist.scale(sidePanel.currentStamina()*2), scaleAssist.scale(20)));//width & height
        //displays the text STAMINA
        g2d.setColor(Color.darkGray);
        g2d.drawString("STAMINA",scaleAssist.scale(48), scaleAssist.scale(188));
    }
    
    private void renderInventoryGroup(Graphics2D g2d, SidePanel sidePanel,ScalingAssistant scaleAssist){
        //display inventory boxes
        g2d.setColor(Color.gray);
        g2d.drawString("INVENTORY", 
                        scaleAssist.scale(35), scaleAssist.scale(220));//X & Y offset
            //inventory box 1
            g2d.drawImage(sidePanel.emptyInventory(), 
                    scaleAssist.scale(35), scaleAssist.scale(225), //X & Y offset 
                    scaleAssist.scale(65), scaleAssist.scale(65), //width & height
                    null);
            //inventory box 2
            g2d.drawImage(sidePanel.emptyInventory(), 
                    scaleAssist.scale(35+65+15), scaleAssist.scale(225), //X & Y offset 
                    scaleAssist.scale(65), scaleAssist.scale(65), //width & height
                    null);
            //inventory box 3
            g2d.drawImage(sidePanel.emptyInventory(), 
                    scaleAssist.scale(35+(65*2)+30), scaleAssist.scale(225), //X & Y offset 
                    scaleAssist.scale(65), scaleAssist.scale(65), //width & height
                    null);
    }
    
    private void renderActionGroup(Graphics2D g2d, SidePanel sidePanel,ScalingAssistant scaleAssist){
        //display action boxes    
        g2d.drawString("ACTION", 
                        scaleAssist.scale(35), scaleAssist.scale(310));//X & Y offset
            //action box 1
            g2d.drawImage(sidePanel.emptyInventory(), 
                    scaleAssist.scale(35), scaleAssist.scale(315), //X & Y offset 
                    scaleAssist.scale(65), scaleAssist.scale(65), //width & height
                    null);
            //action box 2
            g2d.drawImage(sidePanel.emptyInventory(), 
                    scaleAssist.scale(35+65+15), scaleAssist.scale(315), //X & Y offset 
                    scaleAssist.scale(65), scaleAssist.scale(65), //width & height
                    null);
            //action box 3
            g2d.drawImage(sidePanel.emptyInventory(), 
                    scaleAssist.scale(35+(65*2)+30), scaleAssist.scale(315), //X & Y offset 
                    scaleAssist.scale(65), scaleAssist.scale(65), //width & height
                    null);
    }

    private void renderOccupants(Graphics2D g2d, SidePanel sidePanel, ScalingAssistant scaleAssist) {
        //display the images of occupants
        g2d.drawImage(sidePanel.showOccupants(), 
            scaleAssist.scale(35), scaleAssist.scale(12+315+100), //X & Y offset 
            scaleAssist.scale(225), scaleAssist.scale(130), //width & height
            null);
        g2d.setColor(Color.gray);
    }
}
