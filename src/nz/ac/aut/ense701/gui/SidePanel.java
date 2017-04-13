
package nz.ac.aut.ense701.gui;

import java.awt.image.BufferedImage;
import nz.ac.aut.ense701.gameModel.Game;
import nz.ac.aut.ense701.gameModel.Player;

/**
 *
 * @author DonnaCello
 */
public class SidePanel {

    private final Game game;
    private Player player;
    
    private BufferedImage playerIcon,questIcon,inventoryEmpty,inventorySnack,
                inventoryToolbox,inventoryApple,inventoryTrap;
    
    
    public SidePanel(Game g){
        this.game = g;
    }
    
    //this is run every step, to check for updates to the stats
    //called from the Tick machine
    public void checkStats(){
        //what happens every step?
        loadImages();
    }
    
    public void loadImages(){
        BufferedImageLoader loader = new BufferedImageLoader();
            try{
            playerIcon = loader.loadImage("/resource/images/sidepanel_player.png");
            questIcon = loader.loadImage("/resource/images/sidepanel_quest.png");
            inventoryEmpty = loader.loadImage("/resource/images/inventory_empty.png");
            inventorySnack = loader.loadImage("/resource/images/inventory_snack.png");
            inventoryToolbox = loader.loadImage("/resource/images/inventory_toolbox.png");
            inventoryApple = loader.loadImage("/resource/images/inventory_apple.png");
            inventoryTrap = loader.loadImage("/resource/images/inventory_trap.png");
            }
            catch(Exception e){
                e.printStackTrace();
            }
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
}
