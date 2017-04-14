package nz.ac.aut.ense701.gui;

import java.awt.image.BufferedImage;
import nz.ac.aut.ense701.gameModel.Game;
import nz.ac.aut.ense701.gameModel.Item;

/**
 *
 * @author DonnaCello
 */
public class SidePanel {

    private final Game game;

    private BufferedImage playerIcon, questIcon, inventoryEmpty, inventorySnack,
            inventoryToolbox, inventoryApple, inventoryTrap;
    
    BufferedImage[] imgArray = new BufferedImage[3];

    private AssetManager assetManager;

    public SidePanel(Game g) {
        this.game = g;
        this.assetManager = AssetManager.getAssetManager();
    }

    //this is run every step, to check for updates to the stats
    //called from the Tick machine
    public void checkStats() {
        //what happens every step?
        loadImages();
    }

    public void loadImages() {
        playerIcon = assetManager.getPlayerIcon();
        questIcon = assetManager.getQuestIcon();
        inventoryEmpty = assetManager.getInventoryEmpty();
        inventorySnack = assetManager.getInventorySnack();
        inventoryToolbox = assetManager.getInventoryToolbox();
        inventoryApple = assetManager.getInventoryApple();
        inventoryTrap = assetManager.getInventoryTrap();
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
        if(game.getPlayerInventory().length == 0) {
            imgArray[0] = inventoryEmpty;
            imgArray[1] = inventoryEmpty;
            imgArray[2] = inventoryEmpty;
        }
        for (int i = 0; i < game.getPlayerInventory().length; i++) {
            if (game.getPlayerInventory().length <= i) {
                imgArray[i] = inventoryEmpty;
            } else {
                switch (((Item) game.getPlayerInventory()[i]).getName().toLowerCase()) {
                    case "trap":
                        imgArray[i] = inventoryTrap;
                        break;
                    case "screwdriver":
                        imgArray[i] = inventoryToolbox;
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
        }
        return imgArray;
    }


}
