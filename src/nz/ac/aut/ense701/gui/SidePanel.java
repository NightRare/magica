
package nz.ac.aut.ense701.gui;

import nz.ac.aut.ense701.gameModel.Game;
import nz.ac.aut.ense701.gameModel.Player;

/**
 *
 * @author DonnaCello
 */
public class SidePanel {

    private Player player;
    private Game game;
    
    public SidePanel(Game g){
        this.game = g;
    }
    
    
    //thi is run every step, to check for updates to the stats
    //called from the Tick machine
    public void checkStats(){
        //what do you want to happen every step?
    }
    
}
