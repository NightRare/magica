package nz.ac.aut.ense701.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JOptionPane;
import nz.ac.aut.ense701.gameModel.Fauna;
import nz.ac.aut.ense701.gameModel.Food;
import nz.ac.aut.ense701.gameModel.Game;
import nz.ac.aut.ense701.gameModel.GameState;
import nz.ac.aut.ense701.gameModel.Item;
import nz.ac.aut.ense701.gameModel.Kiwi;
import nz.ac.aut.ense701.gameModel.Occupant;
import nz.ac.aut.ense701.gameModel.Position;
import nz.ac.aut.ense701.gameModel.Tool;

/**
 *
 * @author DonnaCello
 */
public class SidePanel {

    private final Game game;

    private BufferedImage inventoryEmpty, inventorySnack,
            inventoryScrewdriver, inventoryApple, inventoryTrap,inventoryRatTrap,inventoryCatTrap,inventoryA24Trap,
            tag,trap,collect;

    private final AssetManager assetManager;
    
    private ScalingAssistant scaleAssist;
    
    // occupant for displaying on info board
    private Occupant infoOccupant;
    
    //private GUI ui;
    
    //array indexes reference
    private final int X_OFFSET = 0;
    private final int Y_OFFSET = 1;
    private final int BOX_1 = 0;
    private final int BOX_2 = 1;
    private final int BOX_3 = 2;
    private final int INACTIVE_BUTTON = 0;
    private final int ACTIVE_BUTTON = 1;

    //X offset, Y offset, WINDOW_WIDTH, and WINDOW_HEIGHT values of side panel images
    private final int[] PLAYER_ICON = {12, 12, 170, 155};
    private final int[] CLIPBOARD_ICON = {182, 12, 105, 155};
    private final int[] STAMINA_BAR = {45, 170, 100, 20}; //width is game dependent
    private final int[] INVENTORY_BOXES = {35, 225, 65, 65, 35, 115, 195};
    private final int[] ACTION_BOXES = {35, 315, 65, 65, 35, 115, 195};
    private final int[] BOXES_X_OFFSET = {35, 115, 195};
    
    
    public SidePanel(Game g) {
        this.game = g;
        this.assetManager = AssetManager.getAssetManager();
        
    }

    //this is run every step, to check for updates to the stats
    //called from the Tick machine
    public void checkStats() {
        loadImages();
        inventoryImage();
        inventoryList();
        if ( game.getState() == GameState.LOST ){
            popUpMessage(game.getLoseMessage(),"Game Over!");
            game.createNewGame();
        } else if ( game.getState() == GameState.WON ){
            popUpMessage(game.getWinMessage(),"Well Done!");
            game.createNewGame();
        } else if (game.messageForPlayer()){
            popUpMessage(game.getPlayerMessage(),"Hey.."); 
        }
    }
    
       
    public void render(Graphics2D g2d){
        scaleAssist = ScalingAssistant.getScalingAssistant();
        renderBoards(g2d);
        renderPlayerIcon(g2d);
        renderQuest(g2d);
        renderStaminaBar(g2d);
        renderInventoryGroup(g2d);
        renderActionGroup(g2d);
        renderInfoBoard(g2d);
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
    
    private static void popUpMessage(String message, String title){
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
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
    private void renderPlayerIcon(Graphics2D g2d) {
        BufferedImage image = AssetManager.getAssetManager().getPlayerFace_happy();
        //display player status through facial expression
        if(currentStamina() >= totalStamina() *0.75){
            image = AssetManager.getAssetManager().getPlayerFace_happy();
        }else if(currentStamina() >= totalStamina() *0.50){
            image = AssetManager.getAssetManager().getPlayerFace_neutral();
        }else if(currentStamina() >= totalStamina() *0.25){
            image = AssetManager.getAssetManager().getPlayerFace_hungry();
        }else if(currentStamina() < totalStamina() *0.25){
            image = AssetManager.getAssetManager().getPlayerFace_tired();
        }
        
        //display images
        g2d.drawImage(image,
                valueOf(PLAYER_ICON, X_OFFSET), valueOf(PLAYER_ICON, Y_OFFSET),
                null);
    }

    /**
     * Renders the Quest Panel for the Side Panel
     *
     * @param g2d graphics2D reference
     * @param sidePanel side panel object
     * @param scaleAssist scaling assistant
     */
    private void renderQuest(Graphics2D g2d) {

        //setup quest object values
        int fontSize = scaleAssist.scale(18);
        int textLeftMargin = scaleAssist.scale(65 + CLIPBOARD_ICON[X_OFFSET]);
        int kiwiTextTopMargin = scaleAssist.scale(78 + CLIPBOARD_ICON[Y_OFFSET]);
        int predatorTextTopMargin = scaleAssist.scale(125 + CLIPBOARD_ICON[Y_OFFSET]);

        //displays the quest clipboard image
        g2d.drawImage(AssetManager.getAssetManager().getQuestIcon(),
                valueOf(CLIPBOARD_ICON, X_OFFSET), valueOf(CLIPBOARD_ICON, Y_OFFSET),
                null);

        //set the text color as gray
        g2d.setColor(Color.gray);

        //set font properties
        g2d.setFont(new Font("Arial", Font.BOLD, fontSize));

        //display number of Kiwis to tag/count
        g2d.drawString(numOfKiwi(), textLeftMargin, kiwiTextTopMargin);

        //display number of Predators left
        g2d.drawString(numOfPredator(), textLeftMargin, predatorTextTopMargin);

    }

    /**
     * Renders a Stamina Bar for the Side Panel
     *
     * @param g2d graphics2D reference
     * @param sidePanel side panel object
     * @param scaleAssist scaling assistant
     */
    private void renderStaminaBar(Graphics2D g2d) {

        //setup stamina bar values
        int maxStaminawidth = scaleAssist.scale(totalStamina() * 2);
        int currentStaminawidth = scaleAssist.scale(currentStamina() * 2);
        int textLeftMargin = scaleAssist.scale(48);
        int textTopMargin = scaleAssist.scale(188);

        //display Max Stamina
        g2d.setColor(Color.lightGray);
        int STAMINA_HEIGHT_INDEX = 3;
        g2d.fill(new Rectangle2D.Double(
                valueOf(STAMINA_BAR, X_OFFSET), valueOf(STAMINA_BAR, Y_OFFSET),
                maxStaminawidth, valueOf(STAMINA_BAR, STAMINA_HEIGHT_INDEX)));

        //displays Current Stamina
        if(currentStaminawidth >= maxStaminawidth*0.75){
            g2d.setColor(new Color(57, 181, 75));//green
        }else if(currentStaminawidth >= maxStaminawidth*0.50){
            g2d.setColor(new Color(216, 216, 48));//yellow
        }else if(currentStaminawidth >= maxStaminawidth*0.25){
            g2d.setColor(new Color(201, 147, 40));//orange
        }else if(currentStaminawidth < maxStaminawidth*0.25){
            g2d.setColor(new Color(216, 26, 26));//red
        }
        

        g2d.fill(new Rectangle2D.Double(
                valueOf(STAMINA_BAR, X_OFFSET), valueOf(STAMINA_BAR, Y_OFFSET),
                currentStaminawidth, valueOf(STAMINA_BAR, STAMINA_HEIGHT_INDEX)));

        //displays the text STAMINA
        g2d.setColor(Color.darkGray);

        g2d.drawString("STAMINA", textLeftMargin, textTopMargin);

    }

    private void renderInventoryGroup(Graphics2D g2d) {

        int textLeftMargin = scaleAssist.scale(35);

        int textTopMargin = scaleAssist.scale(220);

        //display inventory boxes
        g2d.setColor(Color.gray);

        g2d.drawString("INVENTORY", textLeftMargin, textTopMargin);

        //inventory box 1
        g2d.drawImage(inventoryImage()[BOX_1],
                valueOf(BOXES_X_OFFSET, BOX_1), valueOf(INVENTORY_BOXES, Y_OFFSET),null);

        //inventory box 2
        g2d.drawImage(inventoryImage()[BOX_2],
                valueOf(BOXES_X_OFFSET, BOX_2), valueOf(INVENTORY_BOXES, Y_OFFSET),null);

        //inventory box 3
        g2d.drawImage(inventoryImage()[BOX_3],
                valueOf(BOXES_X_OFFSET, BOX_3), valueOf(INVENTORY_BOXES, Y_OFFSET),null);

    }

    /**
     * Renders the action boxes
     *
     * @param g2d
     * @param sidePanel
     * @param scaleAssist
     */
    private void renderActionGroup(Graphics2D g2d) {
        //setup text margins
        int textLeftMargin = scaleAssist.scale(35);
        int textTopMargin = scaleAssist.scale(310);
        //show action text
        g2d.drawString("ACTION", textLeftMargin, textTopMargin);
        //action box tag
        g2d.drawImage(getTagButton(),
                valueOf(BOXES_X_OFFSET, BOX_1), valueOf(ACTION_BOXES, Y_OFFSET),null);
        //action box trap
        g2d.drawImage(getTrapButton(),
                valueOf(BOXES_X_OFFSET, BOX_2), valueOf(ACTION_BOXES, Y_OFFSET),null);
        //action box collect
        g2d.drawImage(getCollectButton(),
                valueOf(BOXES_X_OFFSET, BOX_3), valueOf(ACTION_BOXES, Y_OFFSET),null);
    }
    
    /**
     * Renders the occupant info board
     *
     * @param g2d
     * @param sidePanel
     * @param scaleAssist
     */
    private void renderInfoBoard(Graphics2D g2d) {
        switch (getOccupants().length) {
            case 2:
                renderOccupantsList(g2d);
                break;
            case 1:
                renderOccupantInfo(g2d, getOccupants()[0]);
                renderOccupants(g2d);
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
    private void renderOccupantInfo(Graphics2D g2d, Occupant occupant) {
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
    private void renderOccupantsList(Graphics2D g2d) {
        Occupant infoOccupant = getInfoOccupant();

        if (infoOccupant == null) {
            Occupant occupant1 = getOccupants()[0];
            Occupant occupant2 = getOccupants()[1];

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

            renderOccupantInfo(g2d, infoOccupant);

            // display "read more" if its fauna
            if(infoOccupant instanceof Fauna)
                g2d.drawString("READ MORE", scaleAssist.scale(35), scaleAssist.scale(600+160));            
        }
    }
    
    private void renderOccupants(Graphics2D g2d) {
        Occupant[] occupants = getOccupants();
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

    private void loadImages() {
        inventoryEmpty = assetManager.getInventoryEmpty();
        inventorySnack = assetManager.getInventorySnack();
        inventoryScrewdriver = assetManager.getInventoryToolbox();
        inventoryApple = assetManager.getInventoryApple();
        inventoryTrap = assetManager.getInventoryTrap();
        inventoryRatTrap = assetManager.getInventoryRatTrap();
        inventoryCatTrap = assetManager.getInventoryCatTrap();
        inventoryA24Trap = assetManager.getInventoryA24Trap();
        tag = assetManager.getActionTag();
        collect = assetManager.getActionCollect();
        trap = assetManager.getActionTrap();
    }

    private String numOfKiwi() {
        return "" + game.getKiwiCount();
    }

    private String numOfPredator() {
        return "" + game.getPredatorsRemaining();
    }

    private int totalStamina() {
        return game.getPlayerValues()[Game.MAXSTAMINA_INDEX];
    }

    private int currentStamina() {
        return game.getPlayerValues()[Game.STAMINA_INDEX];
    }
    
    private ArrayList<Item> inventoryList(){
        ArrayList list = new ArrayList();
        list.addAll(Arrays.asList(game.getPlayerInventory()));
        return list;
    }
    
    private BufferedImage[] inventoryImage() {
        BufferedImage[] imgArray = new BufferedImage[3];
        imgArray[0] = inventoryEmpty;
        imgArray[1] = inventoryEmpty;
        imgArray[2] = inventoryEmpty;
        for (int i = 0; i < inventoryList().size(); i++) {
            switch (((Item) inventoryList().get(i)).getName().toLowerCase()) {
                case "trap": imgArray[i] = inventoryTrap;break;
                case "screwdriver": imgArray[i] = inventoryScrewdriver;break;
                case "orange juice": imgArray[i] = inventorySnack;break;
                case "sandwich": imgArray[i] = inventorySnack;break;
                case "muesli bar": imgArray[i] = inventorySnack;break;
                case "apple": imgArray[i] = inventoryApple;break;
                case "rat trap": imgArray[i] = inventoryRatTrap;break;
                case "cat trap": imgArray[i] = inventoryCatTrap;break;
                case "a24 trap": imgArray[i] = inventoryA24Trap;break;
            }
        }
        return imgArray;
    }
    
    private BufferedImage getTagButton(){
        BufferedImage tagIcon = assetManager.getActionTag()[INACTIVE_BUTTON];
        for(Occupant o: getOccupants()){
            if(o instanceof Kiwi){
                tagIcon = assetManager.getActionTag()[ACTIVE_BUTTON];
            }
        }
        return tagIcon;
    }
    
    private BufferedImage getTrapButton(){
        BufferedImage trapIcon = assetManager.getActionTrap()[INACTIVE_BUTTON];
        for(Occupant o: getOccupants()){
            if(game.getPlayer().hasTrap() && 
                o instanceof Fauna && !(o instanceof Kiwi)){
                    trapIcon = assetManager.getActionTrap()[ACTIVE_BUTTON];
            }
        }
        return trapIcon;
    }
    
    private BufferedImage getCollectButton(){
        BufferedImage collectIcon = assetManager.getActionCollect()[INACTIVE_BUTTON];
        for(Occupant o: getOccupants()){
            if(o instanceof Tool || o instanceof Food){
                collectIcon = assetManager.getActionCollect()[ACTIVE_BUTTON];
            }
        }
        return collectIcon;
    }
    
    public Occupant[] getOccupants() {
        Position position = game.getPlayer().getPosition();
        return game.getIsland().getOccupants(position);
    }
    
    public void setInfoOccupant(Occupant occupantToDisplay) {
        infoOccupant = occupantToDisplay;
    }
    
    public Occupant getInfoOccupant() {
        return infoOccupant;
    }
   
    public void clearInfoOccupant() {
        this.infoOccupant = null;
    }
}
