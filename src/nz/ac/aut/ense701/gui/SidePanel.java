package nz.ac.aut.ense701.gui;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import nz.ac.aut.ense701.gameModel.Game;
import nz.ac.aut.ense701.gameModel.Item;
import nz.ac.aut.ense701.gameModel.Occupant;
import nz.ac.aut.ense701.gameModel.Player;
import nz.ac.aut.ense701.gameModel.Position;

/**
 *
 * @author DonnaCello
 */
public class SidePanel {

    private final Game game;

    private BufferedImage playerIcon, questIcon, inventoryEmpty, inventorySnack,
            inventoryScrewdriver, inventoryApple, inventoryTrap;
    
    private BufferedImage tag,trap,collect;
    
    

    private AssetManager assetManager;
    
    // occupant for displaying on info board
    private Occupant infoOccupant;
    
    
    
    public SidePanel(Game g) {
        this.game = g;
        this.assetManager = AssetManager.getAssetManager();
        
    }

    //this is run every step, to check for updates to the stats
    //called from the Tick machine
    public void checkStats() {
        //what happens every step?
        loadImages();
        inventoryImage();
    }

    public void loadImages() {
        playerIcon = assetManager.getPlayerIcon();
        questIcon = assetManager.getQuestIcon();
        inventoryEmpty = assetManager.getInventoryEmpty();
        inventorySnack = assetManager.getInventorySnack();
        inventoryScrewdriver = assetManager.getInventoryToolbox();
        inventoryApple = assetManager.getInventoryApple();
        inventoryTrap = assetManager.getInventoryTrap();
        tag = assetManager.getActionTag();
        collect = assetManager.getActionCollect();
        trap = assetManager.getActionTrap();
    }

    public BufferedImage showPlayerIcon() {
        return playerIcon;
    }

    public BufferedImage showQuests() {
        return questIcon;
    }

    public BufferedImage emptyInventory() {
        return inventoryEmpty;
    }

    public String numOfKiwi() {
        return "" + game.getKiwiCount();
    }

    public String numOfPredator() {
        return "" + game.getPredatorsRemaining();
    }

    public int totalStamina() {
        return game.getPlayerValues()[Game.MAXSTAMINA_INDEX];
    }

    public int currentStamina() {
        return game.getPlayerValues()[Game.STAMINA_INDEX];
    }
    
    public BufferedImage[] inventoryImage() {
        BufferedImage[] imgArray = new BufferedImage[3];
        imgArray[0] = inventoryEmpty;
        imgArray[1] = inventoryEmpty;
        imgArray[2] = inventoryEmpty;
        for (int i = 0; i < game.getPlayerInventory().length; i++) {
            switch (((Item) game.getPlayerInventory()[i]).getName().toLowerCase()) {
                case "trap":
                    imgArray[i] = inventoryTrap;
                    break;
                case "screwdriver":
                    imgArray[i] = inventoryScrewdriver;
                    break;
                case "orange juice":
                    imgArray[i] = inventorySnack;
                    break;
                case "sandwich":
                    imgArray[i] = inventorySnack;
                    break;
                case "muesli bar":
                    imgArray[i] = inventorySnack;
                    break;
                case "apple":
                    imgArray[i] = inventoryApple;
                    break;
            }
        }
        return imgArray;
    }

    public BufferedImage[] actionImage(){
        BufferedImage[] imgArray = new BufferedImage[3];
        imgArray[0]=tag;
        imgArray[1]=trap;
        imgArray[2]=collect;
        return imgArray;
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

    public List<BufferedImage> showOccupants() {
        Occupant[] occupants = game.getOccupantsPlayerPosition();
        List<BufferedImage> bi = new LinkedList();
        for(Occupant o : occupants) {
            bi.add(AssetManager.getAssetManager().getOccupantPortrait(o.getName()));
        }
        return bi;
    }
    
    
}
