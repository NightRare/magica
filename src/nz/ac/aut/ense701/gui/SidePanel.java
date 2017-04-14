
package nz.ac.aut.ense701.gui;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import nz.ac.aut.ense701.gameModel.Game;
import nz.ac.aut.ense701.gameModel.Occupant;
import nz.ac.aut.ense701.gameModel.Player;
import nz.ac.aut.ense701.gameModel.Position;

/**
 *
 * @author DonnaCello
 */
public class SidePanel {

    private final Game game;
    private Player player;
    
    private BufferedImage playerIcon,questIcon,inventoryEmpty,inventorySnack,
                inventoryToolbox,inventoryApple,inventoryTrap;
    
    private AssetManager assetManager;
    
    // occupant for displaying on info board
    private Occupant infoOccupant;
    
    
    
    public SidePanel(Game g){
        this.game = g;
        this.assetManager = AssetManager.getAssetManager();
    }
    
    //this is run every step, to check for updates to the stats
    //called from the Tick machine
    public void checkStats(){
        //what happens every step?
        loadImages();
    }
    
    public void loadImages(){
        playerIcon = assetManager.getPlayerIcon();
        questIcon = assetManager.getQuestIcon();
        inventoryEmpty = assetManager.getInventoryEmpty();
        inventorySnack = assetManager.getInventorySnack();
        inventoryToolbox = assetManager.getInventoryToolbox();
        inventoryApple = assetManager.getInventoryApple();
        inventoryTrap = assetManager.getInventoryTrap();
    }
    
    public BufferedImage showPlayerIcon(){
        return playerIcon;
    }
    
    public BufferedImage showQuests(){
        return questIcon;
    }
    
    public BufferedImage emptyInventory(){
        return inventoryEmpty;
    }
    
    public String numOfKiwi(){
        return ""+game.getKiwiCount();
    }
    
    public String numOfPredator(){
        return "" + game.getPredatorsRemaining();
    }
    
    public int totalStamina(){
        return game.getPlayerValues()[Game.MAXSTAMINA_INDEX];
    }
    
    public int currentStamina(){
        return game.getPlayerValues()[Game.STAMINA_INDEX];
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
}
