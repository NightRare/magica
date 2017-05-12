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
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import nz.ac.aut.ense701.gameModel.Fauna;
import nz.ac.aut.ense701.gameModel.Game;
import nz.ac.aut.ense701.gameModel.GameState;
import nz.ac.aut.ense701.gameModel.LightLevel;
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

    ScalingAssistant scaleAssist;

    //array indexes reference
    private final int X_OFFSET, Y_OFFSET, IMG_WIDTH, IMG_HEIGHT, BOX_1, BOX_2, BOX_3;

    //X offset, Y offset, WINDOW_WIDTH, and WINDOW_HEIGHT values of side panel images
    private final int[] PLAYER_ICON = {12, 12, 170, 155};
    private final int[] CLIPBOARD_ICON = {182, 12, 105, 155};
    private final int[] STAMINA_BAR = {45, 170, 100, 20}; //width is game dependent
    private final int[] INVENTORY_BOXES = {35, 225, 65, 65, 35, 115, 195};
    private final int[] ACTION_BOXES = {35, 315, 65, 65, 35, 115, 195};
    private final int[] BOXES_X_OFFSET = {35, 115, 195};

    public RenderingEngine(GameLoop loop) {

        X_OFFSET = 0;
        Y_OFFSET = 1;

        IMG_WIDTH = 2;
        IMG_HEIGHT = 3;

        BOX_1 = 0;
        BOX_2 = 1;
        BOX_3 = 2;

        this.loop = loop;

        this.scaleAssist = ScalingAssistant.getScalingAssistant();

        this.game = loop.getGame();

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

        loop.getPlayerSprite().render(g2d);
        game.getNotification().render(g2d);

        if (game.lightLevel() == LightLevel.NIGHT) {
            g2d.drawImage(AssetManager.getAssetManager().getNight(), null, GUIConfigs.colToX(0), GUIConfigs.rowToY(0));
        }
        //g2d.drawImage(AssetManager.getAssetManager().getMap(), null, GUIConfigs.MAP_WIDTH,GUIConfigs.WINDOW_HEIGHT);
        

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

        g2d.drawImage(square.getTexture(), null, xToRenderAt, yToRenderAt);

        g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
        //g2d.drawString(square.getLabel(), xToRenderAt, yLowered);
        //drawing the occupants
        ScalingAssistant scaleAssist = ScalingAssistant.getScalingAssistant();

        g2d.drawImage(square.getOccupantIcon(square.getLabel()),
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
        renderPlayerIcon(g2d, sidePanel);
        renderQuest(g2d, sidePanel);
        renderStaminaBar(g2d, sidePanel);
        renderInventoryGroup(g2d, sidePanel);
        renderActionGroup(g2d, sidePanel);

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

    private int valueOf(int[] array, int index) {

        return scaleAssist.scale(array[index]);

    }

    /**
     * Renders a Player Icon for the Side Panel
     *
     * @param g2d graphics2D reference
     * @param sidePanel side panel object
     * @param scAs scaling assistant
     */
    private void renderPlayerIcon(Graphics2D g2d, SidePanel sp) {

        //display images
        g2d.drawImage(sp.showPlayerIcon(),
                valueOf(PLAYER_ICON, X_OFFSET), valueOf(PLAYER_ICON, Y_OFFSET),
                valueOf(PLAYER_ICON, IMG_WIDTH), valueOf(PLAYER_ICON, IMG_HEIGHT),
                null);
    }

    /**
     * Renders the Quest Panel for the Side Panel
     *
     * @param g2d graphics2D reference
     * @param sidePanel side panel object
     * @param scaleAssist scaling assistant
     */
    private void renderQuest(Graphics2D g2d, SidePanel sidePanel) {

        //setup quest object values
        int fontSize = scaleAssist.scale(18);

        int textLeftMargin = scaleAssist.scale(65 + CLIPBOARD_ICON[X_OFFSET]);

        int kiwiTextTopMargin = scaleAssist.scale(78 + CLIPBOARD_ICON[Y_OFFSET]);

        int predatorTextTopMargin = scaleAssist.scale(125 + CLIPBOARD_ICON[Y_OFFSET]);

        //displays the quest clipboard image
        g2d.drawImage(sidePanel.showQuests(),
                valueOf(CLIPBOARD_ICON, X_OFFSET), valueOf(CLIPBOARD_ICON, Y_OFFSET),
                valueOf(CLIPBOARD_ICON, IMG_WIDTH), valueOf(CLIPBOARD_ICON, IMG_HEIGHT),
                null);

        //set the text color as gray
        g2d.setColor(Color.gray);

        //set font properties
        g2d.setFont(new Font("Arial", Font.BOLD, fontSize));

        //display number of Kiwis to tag/count
        g2d.drawString(sidePanel.numOfKiwi(), textLeftMargin, kiwiTextTopMargin);

        //display number of Predators left
        g2d.drawString(sidePanel.numOfPredator(), textLeftMargin, predatorTextTopMargin);

    }

    /**
     * Renders a Stamina Bar for the Side Panel
     *
     * @param g2d graphics2D reference
     * @param sidePanel side panel object
     * @param scaleAssist scaling assistant
     */
    private void renderStaminaBar(Graphics2D g2d, SidePanel sidePanel) {

        //setup stamina bar values
        int maxStaminawidth = scaleAssist.scale(sidePanel.totalStamina() * 2);

        int currentStaminawidth = scaleAssist.scale(sidePanel.currentStamina() * 2);

        int textLeftMargin = scaleAssist.scale(48);

        int textTopMargin = scaleAssist.scale(188);

        //display Max Stamina
        g2d.setColor(Color.lightGray);

        g2d.fill(new Rectangle2D.Double(
                valueOf(STAMINA_BAR, X_OFFSET), valueOf(STAMINA_BAR, Y_OFFSET),
                maxStaminawidth, valueOf(STAMINA_BAR, IMG_HEIGHT)));

        //displays Current Stamina
        if(currentStaminawidth >= maxStaminawidth*0.8){//80%+ of health
            g2d.setColor(new Color(57, 181, 75));//green
        }else if(currentStaminawidth >= maxStaminawidth*0.6){//60%+
            g2d.setColor(new Color(216, 216, 48));//yellow
        }else if(currentStaminawidth >= maxStaminawidth*0.4){//40%+
            g2d.setColor(new Color(201, 147, 40));//orange
        }else if(currentStaminawidth >= maxStaminawidth*0.2){//20%+
            g2d.setColor(new Color(216, 26, 26));//red
        }
        

        g2d.fill(new Rectangle2D.Double(
                valueOf(STAMINA_BAR, X_OFFSET), valueOf(STAMINA_BAR, Y_OFFSET),
                currentStaminawidth, valueOf(STAMINA_BAR, IMG_HEIGHT)));

        //displays the text STAMINA
        g2d.setColor(Color.darkGray);

        g2d.drawString("STAMINA", textLeftMargin, textTopMargin);

    }

    private void renderInventoryGroup(Graphics2D g2d, SidePanel sidePanel) {

        int textLeftMargin = scaleAssist.scale(35);

        int textTopMargin = scaleAssist.scale(220);

        //display inventory boxes
        g2d.setColor(Color.gray);

        g2d.drawString("INVENTORY", textLeftMargin, textTopMargin);

        //inventory box 1
        g2d.drawImage(sidePanel.inventoryImage()[BOX_1],
                valueOf(BOXES_X_OFFSET, BOX_1), valueOf(INVENTORY_BOXES, Y_OFFSET),
                valueOf(INVENTORY_BOXES, IMG_WIDTH), valueOf(INVENTORY_BOXES, IMG_HEIGHT),
                null);

        //inventory box 2
        g2d.drawImage(sidePanel.inventoryImage()[BOX_2],
                valueOf(BOXES_X_OFFSET, BOX_2), valueOf(INVENTORY_BOXES, Y_OFFSET),
                valueOf(INVENTORY_BOXES, IMG_WIDTH), valueOf(INVENTORY_BOXES, IMG_HEIGHT),
                null);

        //inventory box 3
        g2d.drawImage(sidePanel.inventoryImage()[BOX_3],
                valueOf(BOXES_X_OFFSET, BOX_3), valueOf(INVENTORY_BOXES, Y_OFFSET),
                valueOf(INVENTORY_BOXES, IMG_WIDTH), valueOf(INVENTORY_BOXES, IMG_HEIGHT),
                null);

    }

    /**
     * Renders the action boxes
     *
     * @param g2d
     * @param sidePanel
     * @param scaleAssist
     */
    private void renderActionGroup(Graphics2D g2d, SidePanel sidePanel) {

        //setup text margins
        int textLeftMargin = scaleAssist.scale(35);

        int textTopMargin = scaleAssist.scale(310);

        //show action text
        g2d.drawString("ACTION", textLeftMargin, textTopMargin);

        //action box tag
        g2d.drawImage(sidePanel.actionImage()[BOX_1],
                valueOf(BOXES_X_OFFSET, BOX_1), valueOf(ACTION_BOXES, Y_OFFSET),
                valueOf(ACTION_BOXES, IMG_WIDTH), valueOf(ACTION_BOXES, IMG_HEIGHT),
                null);

        //action box trap
        g2d.drawImage(sidePanel.actionImage()[BOX_2],
                valueOf(BOXES_X_OFFSET, BOX_2), valueOf(ACTION_BOXES, Y_OFFSET),
                valueOf(ACTION_BOXES, IMG_WIDTH), valueOf(ACTION_BOXES, IMG_HEIGHT),
                null);

        //action box collect
        g2d.drawImage(sidePanel.actionImage()[BOX_3],
                valueOf(BOXES_X_OFFSET, BOX_3), valueOf(ACTION_BOXES, Y_OFFSET),
                valueOf(ACTION_BOXES, IMG_WIDTH), valueOf(ACTION_BOXES, IMG_HEIGHT),
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
                renderOccupantInfo(g2d, sidePanel.getOccupants()[0], scaleAssist);
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
     * @param occupant the occupant
     * @param scaleAssist 
     */
    private void renderOccupantInfo(Graphics2D g2d, Occupant occupant, ScalingAssistant scaleAssist) {
        Font originalFont = g2d.getFont(); //record the original font

        Font contentFont = new Font(Font.SERIF, Font.PLAIN, scaleAssist.scale(16));
        g2d.setColor(Color.gray);
        g2d.drawString(occupant.getName().toUpperCase(), scaleAssist.scale(30), scaleAssist.scale(590));

        g2d.setFont(contentFont);
        List<String> descLines = wordSplitter(occupant.getDescription(), 40);

        for (int i = 0; i < descLines.size(); i++) {
            g2d.drawString(descLines.get(i), scaleAssist.scale(25), scaleAssist.scale(612 + i * 16));
        }

        g2d.setFont(originalFont); //set back to original font
    }

    private List<String> wordSplitter(String text, int charNums) {
        String[] words = text.split(" ");
        List<String> output = new LinkedList();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < words.length; i++) {
            int lineLength = sb.length() + words[i].length();

            // it is >= but not >, because should keep one char for space
            if (lineLength >= charNums) {
                output.add(sb.toString());
                sb = new StringBuilder();
                i--;
            }
            else {
                sb.append(" ");
                sb.append(words[i]);
            }
        }
        if (sb.length() != 0) {
            output.add(sb.toString());
        }

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

        if (infoOccupant == null) {
            Occupant occupant1 = sidePanel.getOccupants()[0];
            Occupant occupant2 = sidePanel.getOccupants()[1];

            List<BufferedImage> occupantsImages = new LinkedList();
            occupantsImages.add(AssetManager.getAssetManager()
                    .getOccupantPortrait(occupant1.getName()));
            occupantsImages.add(AssetManager.getAssetManager()
                    .getOccupantPortrait(occupant2.getName()));

            for (int i = 0; i < 2; i++) {
                g2d.drawImage(occupantsImages.get(i),
                        scaleAssist.scale(35), scaleAssist.scale(12 + 315 + 100 + i * (130 + 57)), //X & Y offset 
                        scaleAssist.scale(225), scaleAssist.scale(130), //width & WINDOW_HEIGHT
                        null);
                //g2d.setColor(Color.gray);  
            }

            g2d.setColor(Color.gray);
            g2d.drawString(occupant1.getName().toUpperCase(), scaleAssist.scale(35), scaleAssist.scale(600));
            g2d.drawString(occupant2.getName().toUpperCase(), scaleAssist.scale(35), scaleAssist.scale(770));

        } else {

            // display the portrait of the Occupant            
            BufferedImage bi = AssetManager.getAssetManager().getOccupantPortrait(infoOccupant.getName());
            g2d.drawImage(bi, 
                scaleAssist.scale(35), scaleAssist.scale(12+315+100), //X & Y offset 
                scaleAssist.scale(225), scaleAssist.scale(130), //width & height
                null);
            g2d.setColor(Color.gray);

            renderOccupantInfo(g2d, infoOccupant, scaleAssist);

            // display "read more" if its fauna
            if(infoOccupant instanceof Fauna)
                g2d.drawString("READ MORE", scaleAssist.scale(35), scaleAssist.scale(600+160));            
        }
    }

    private void renderBackground(Graphics2D g2d) {
        g2d.setColor(Color.lightGray);
        //display background for sidePanel
        g2d.fill(new Rectangle2D.Double(0, 0,
                GUIConfigs.WINDOW_WIDTH, GUIConfigs.WINDOW_HEIGHT));
        //render map
        g2d.drawImage(AssetManager.getAssetManager().getMap(), 
                scaleAssist.scale(300), 0, null);

    }

    private void renderOccupants(Graphics2D g2d, SidePanel sidePanel, ScalingAssistant scaleAssist) {
        Occupant[] occupants = sidePanel.getOccupants();
        //Don't draw image if there's no animal.
        if (occupants.length == 0)
            return;
        Occupant o = occupants[0];
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
