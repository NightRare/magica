/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.ense701.gui;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import nz.ac.aut.ense701.gameModel.Fauna;
import nz.ac.aut.ense701.gameModel.Game;
import nz.ac.aut.ense701.gameModel.GameState;
import nz.ac.aut.ense701.gameModel.Occupant;

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
     *
     * @param g2d graphics2D reference
     */
    public void render(Graphics2D g2d) {

        renderBackground(g2d);
        
        ArrayList<MapSquare> squareList = loop.getMapSquareList();
        for (MapSquare square : squareList) {
            renderMapSquare(g2d, square);
        }
        //renders Side Panel
        renderSidePanel(g2d, loop.getSidePanel());

        // render info board
        renderInfoBoard(g2d, loop.getSidePanel(), ScalingAssistant.getScalingAssistant());
    }

    /**
     * Renders a map square
     *
     * @param g2d graphics2D reference
     * @param square MapSquare to render
     */
    private void renderMapSquare(Graphics2D g2d, MapSquare square) {

        int xToRenderAt = GUIConfigs.colToX(square.getColumn());
        int yToRenderAt = GUIConfigs.rowToY(square.getRow());

        int yLowered = yToRenderAt + GUIConfigs.getSquareHeight() / 2;
        /*    
        g2d.fill(new RoundRectangle2D.Double(xToRenderAt, yToRenderAt,
                GUIConfigs.getSquareWidth(),
                GUIConfigs.getSquareHeight(),
                10, 10)); 
         */

        g2d.fill(new Rectangle2D.Double(xToRenderAt, yToRenderAt,
                GUIConfigs.getSquareWidth(),
                GUIConfigs.getSquareHeight()));

        g2d.drawImage(square.getTexture(), null, xToRenderAt, yToRenderAt);

        g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
        //g2d.drawString(square.getLabel(), xToRenderAt, yLowered);

        //drawing the occupants
        ScalingAssistant scaleAssist = ScalingAssistant.getScalingAssistant();
        g2d.drawImage(square.getOccupantImage(square.getLabel()),
                xToRenderAt, yToRenderAt,
                scaleAssist.scale(50), scaleAssist.scale(50), null);
    }

    /**
     * Renders a Side Panel
     *
     * @param g2d graphics2D reference
     * @param sidePanel side panel object
     */
    private void renderSidePanel(Graphics2D g2d, SidePanel sidePanel) {
        ScalingAssistant scaleAssist = ScalingAssistant.getScalingAssistant();
        renderBoards(g2d);
        renderPlayerIcon(g2d, sidePanel, scaleAssist);
        renderQuest(g2d, sidePanel, scaleAssist);
        renderStaminaBar(g2d, sidePanel, scaleAssist);
        renderInventoryGroup(g2d, sidePanel, scaleAssist);
        renderActionGroup(g2d, sidePanel, scaleAssist);

    }

    /**
     * Renders the boards for the Side Panel
     *
     * @param g2d graphics2D reference
     */
    private void renderBoards(Graphics2D g2d) {
        g2d.setColor(Color.white);
        //display Action Board area
        g2d.fill(new Rectangle2D.Double(GUIConfigs.boardOffsetX(), GUIConfigs.boardOffsetY(),
                GUIConfigs.boardWidth(), GUIConfigs.boardHeight()));
        //display Info Board area
        g2d.fill(new Rectangle2D.Double(
                GUIConfigs.boardOffsetX(), (GUIConfigs.boardOffsetY() * 2) + GUIConfigs.boardHeight(),
                GUIConfigs.boardWidth(), GUIConfigs.boardHeight()));
    }

    /**
     * Renders a Player Icon for the Side Panel
     *
     * @param g2d graphics2D reference
     * @param sidePanel side panel object
     * @param scAs scaling assistant
     */
    private void renderPlayerIcon(Graphics2D g2d, SidePanel sidePanel, ScalingAssistant scAs) {
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
    private void renderQuest(Graphics2D g2d, SidePanel sidePanel, ScalingAssistant scaleAssist) {
        //displays the quest clipboard image
        g2d.drawImage(sidePanel.showQuests(),
                scaleAssist.scale(12 + 170), scaleAssist.scale(12), //X & Y offset 
                scaleAssist.scale(105), scaleAssist.scale(155), //width & height
                null);
        g2d.setColor(Color.gray);
        g2d.setFont(new Font("Arial", Font.BOLD, scaleAssist.scale(18)));
        //display number of Kiwis to tag/count
        g2d.drawString(sidePanel.numOfKiwi(),
                scaleAssist.scale(12 + 170 + 65), scaleAssist.scale(90));//X & Y offset
        //display number of Predators left
        g2d.drawString(sidePanel.numOfPredator(),
                scaleAssist.scale(12 + 170 + 65), scaleAssist.scale(137));//X & Y offset
    }

    /**
     * Renders a Stamina Bar for the Side Panel
     *
     * @param g2d graphics2D reference
     * @param sidePanel side panel object
     * @param scAs scaling assistant
     */
    private void renderStaminaBar(Graphics2D g2d, SidePanel sidePanel, ScalingAssistant scaleAssist) {
        //display Max Stamina
        g2d.setColor(Color.lightGray);
        g2d.fill(new Rectangle2D.Double(scaleAssist.scale(45), scaleAssist.scale(170), //X & Y offset
                scaleAssist.scale(sidePanel.totalStamina() * 2), scaleAssist.scale(20)));//width & height
        //displays Current Stamina
        g2d.setColor(new Color(57, 181, 75));
        g2d.fill(new Rectangle2D.Double(scaleAssist.scale(45), scaleAssist.scale(170), //X & Y offset
                scaleAssist.scale(sidePanel.currentStamina() * 2), scaleAssist.scale(20)));//width & height
        //displays the text STAMINA
        g2d.setColor(Color.darkGray);
        g2d.drawString("STAMINA", scaleAssist.scale(48), scaleAssist.scale(188));
    }

    private void renderInventoryGroup(Graphics2D g2d, SidePanel sidePanel, ScalingAssistant scaleAssist) {
        //display inventory boxes
        g2d.setColor(Color.gray);
        g2d.drawString("INVENTORY", 
                        scaleAssist.scale(35), scaleAssist.scale(220));//X & Y offset
            //inventory box 1
            g2d.drawImage(sidePanel.inventoryImage()[0], 
                    scaleAssist.scale(35), scaleAssist.scale(225), //X & Y offset 
                    scaleAssist.scale(65), scaleAssist.scale(65), //width & height
                    null);
            //inventory box 2
            g2d.drawImage(sidePanel.inventoryImage()[1], 
                    scaleAssist.scale(35+65+15), scaleAssist.scale(225), //X & Y offset 
                    scaleAssist.scale(65), scaleAssist.scale(65), //width & height
                    null);
            //inventory box 3
            g2d.drawImage(sidePanel.inventoryImage()[2], 
                    scaleAssist.scale(35+(65*2)+30), scaleAssist.scale(225), //X & Y offset 
                    scaleAssist.scale(65), scaleAssist.scale(65), //width & height
                    null);
    }
    
    /**
     * Renders the action boxes
     *
     * @param g2d
     * @param sidePanel
     * @param scaleAssist
     */
    private void renderActionGroup(Graphics2D g2d, SidePanel sidePanel, ScalingAssistant scaleAssist) {
        //display action boxes    
        g2d.drawString("ACTION",
                scaleAssist.scale(35), scaleAssist.scale(310));//X & Y offset
        //action box tag
        g2d.drawImage(sidePanel.actionImage()[0],
                scaleAssist.scale(35), scaleAssist.scale(315), //X & Y offset 
                scaleAssist.scale(65), scaleAssist.scale(65), //width & height
                null);
        //action box trap
        g2d.drawImage(sidePanel.actionImage()[1],
                scaleAssist.scale(35 + 65 + 15), scaleAssist.scale(315), //X & Y offset 
                scaleAssist.scale(65), scaleAssist.scale(65), //width & height
                null);
        //action box collect
        g2d.drawImage(sidePanel.actionImage()[2],
                scaleAssist.scale(35 + (65 * 2) + 30), scaleAssist.scale(315), //X & Y offset 
                scaleAssist.scale(65), scaleAssist.scale(65), //width & height
                null);
    }

    /**
     * Renders the occupant info board
     *
     * @param g2d
     * @param sidePanel
     * @param scaleAssist
     */
    private void renderInfoBoard(Graphics2D g2d, SidePanel sidePanel, ScalingAssistant scaleAssist) {
        switch (sidePanel.getOccupants().length) {
            case 2:
                renderOccupantsList(g2d, sidePanel, scaleAssist);
                break;
            case 1:
                renderOccupantInfo(g2d, sidePanel, scaleAssist);
                renderOccupants(g2d, sidePanel, scaleAssist);
                break;
            default:
                //draw something pretty
                break;
        }
    }

    /**
     * Renders the info for an occupant 
     * @param g2d
     * @param sidePanel
     * @param scaleAssist 
     */
    private void renderOccupantInfo(Graphics2D g2d, SidePanel sidePanel, ScalingAssistant scaleAssist) {
        Occupant occupant = sidePanel.getOccupants()[0];

        Font originalFont = g2d.getFont(); //record the original font
        
        Font contentFont = new Font(Font.SERIF, Font.PLAIN, scaleAssist.scale(16));
        g2d.setColor(Color.gray);
        g2d.drawString(occupant.getName().toUpperCase(), scaleAssist.scale(30), scaleAssist.scale(590));
        
        g2d.setFont(contentFont);
        List<String> descLines = wordSplitter(occupant.getDescription(), 35);

        for(int i = 0; i < descLines.size(); i++) {
            g2d.drawString(descLines.get(i), scaleAssist.scale(30), scaleAssist.scale(615 + i * 16));
        }

        g2d.setFont(originalFont); //set back to original font
    }
    
    
    private List<String> wordSplitter(String text, int charNums) {
        char[] charArray = text.toCharArray();
        List<String> output = new LinkedList();
        String line = "";
        
        for(int i = 0; i < charArray.length; i++) {
            line += charArray[i];
            if(i % charNums == (charNums - 1)) {
                output.add(line);
                line = "";
            }            
        }
        output.add(line);
        return output;
    }
    
    /**
     * Renders the list of up to two occupants. If one has been selected then render only one.
     * @param g2d
     * @param sidePanel
     * @param scaleAssist 
     */
    private void renderOccupantsList(Graphics2D g2d, SidePanel sidePanel, ScalingAssistant scaleAssist) {
        Occupant infoOccupant = sidePanel.getInfoOccupant();
        
        if (infoOccupant == null) 
        {
            Occupant occupant1 = sidePanel.getOccupants()[0];
            Occupant occupant2 = sidePanel.getOccupants()[1];
            
            List<BufferedImage> occupantsImages = new LinkedList();
            occupantsImages.add(AssetManager.getAssetManager()
                    .getOccupantPortrait(occupant1.getName()));
            occupantsImages.add(AssetManager.getAssetManager()
                    .getOccupantPortrait(occupant2.getName()));
                        
            for(int i = 0; i < 2; i ++) 
            {
                g2d.drawImage(occupantsImages.get(i), 
                    scaleAssist.scale(35), scaleAssist.scale(12+315+100 + i*(130+57)), //X & Y offset 
                    scaleAssist.scale(225), scaleAssist.scale(130), //width & height
                    null);
                //g2d.setColor(Color.gray);  
            }
            
            g2d.setColor(Color.gray);
            g2d.drawString(occupant1.getName().toUpperCase(), scaleAssist.scale(35), scaleAssist.scale(600));
            g2d.drawString(occupant2.getName().toUpperCase(), scaleAssist.scale(35), scaleAssist.scale(770));
        } 
        
        else {
	      // display the portrait of the Occupant            
            BufferedImage bi = AssetManager.getAssetManager().getOccupantPortrait(infoOccupant.getName());
            g2d.drawImage(bi, 
                scaleAssist.scale(35), scaleAssist.scale(12+315+100), //X & Y offset 
                scaleAssist.scale(225), scaleAssist.scale(130), //width & height
                null);
            g2d.setColor(Color.gray);
	      
             // display text info
            Font originalFont = g2d.getFont(); //record the original font

            Font contentFont = new Font(Font.SERIF, Font.PLAIN, scaleAssist.scale(16));

            g2d.drawString(infoOccupant.getName().toUpperCase(), scaleAssist.scale(30), scaleAssist.scale(590));

            g2d.setFont(contentFont);
            List<String> descLines = wordSplitter(infoOccupant.getDescription(), 35);

            for(int i = 0; i < descLines.size(); i++) {
                g2d.drawString(descLines.get(i), scaleAssist.scale(30), scaleAssist.scale(615 + i * 16));
            }

            g2d.setFont(originalFont); //set back to original font

            // display "read more" if its fauna
            if(infoOccupant instanceof Fauna)
                g2d.drawString("READ MORE", scaleAssist.scale(35), scaleAssist.scale(600+160));            
        }
    }

    private void renderBackground(Graphics2D g2d) {
        g2d.setColor(Color.lightGray);
        //display background for sidePanel
        g2d.fill(new Rectangle2D.Double(0, 0,
                GUIConfigs.width, GUIConfigs.height));
    }

    private void renderOccupants(Graphics2D g2d, SidePanel sidePanel, ScalingAssistant scaleAssist) {
        
        Occupant o = sidePanel.getOccupants()[0];
        BufferedImage bi = AssetManager.getAssetManager().getOccupantPortrait(o.getName());
        g2d.drawImage(bi, 
            scaleAssist.scale(35), scaleAssist.scale(12+315+100), //X & Y offset 
            scaleAssist.scale(225), scaleAssist.scale(130), //width & height
            null);
        g2d.setColor(Color.gray);
        
        // display "read more" if its fauna
        if(o instanceof Fauna)
            g2d.drawString("READ MORE", scaleAssist.scale(35), scaleAssist.scale(600+160));

    }    
    
    
}
