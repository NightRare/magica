/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.ense701.gui;

import java.awt.Desktop;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import static java.lang.StrictMath.abs;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import nz.ac.aut.ense701.gameModel.Fauna;
import nz.ac.aut.ense701.gameModel.Game;
import nz.ac.aut.ense701.gameModel.MoveDirection;
import nz.ac.aut.ense701.gameModel.Occupant;
import nz.ac.aut.ense701.gameModel.Tool;
import static java.lang.StrictMath.abs;
import static java.lang.StrictMath.abs;
import static java.lang.StrictMath.abs;

/**
 *
 * @author Sam
 */
public class ClickListener implements MouseListener {

    Game game;
    GameLoop loop;

    public ClickListener(Game game, GameLoop loop) {
        this.game = game;
        this.loop = loop;
    }

    /**
     * Gets the center x coordinate for the square that the player is in
     *
     * @param game
     * @return x coordinate
     */
    private int getPlayerCentreX(Game game) {
        return GUIConfigs.colToX(this.game.getPlayer().getPosition().getColumn()) + (GUIConfigs.getSquareWidth() / 2);
    }

    /**
     * Gets the center x coordinate for the square that the player is in
     *
     * @param game
     * @return x coordinate
     */
    private int getPlayerCentreY(Game game) {
        return GUIConfigs.rowToY(this.game.getPlayer().getPosition().getRow()) + (GUIConfigs.getSquareHeight() / 2);
    }

    /**
     * Gets the move direction of a click at the given coordinates
     *
     * @param x the x coordinate clicked
     * @param y the y coordinate clicked
     * @return the direction of the click relative to the player
     */
    private MoveDirection getDirection(int x, int y) {
        int playerX = getPlayerCentreX(game);
        int playerY = getPlayerCentreY(game);

        int diffX = x - playerX;
        int diffY = y - playerY;

        if (diffY > 0) {
            if (diffY > abs(diffX)) {
                return MoveDirection.SOUTH;
            }
        }
        if (diffY < 0) {
            if (abs(diffY) > abs(diffX)) {
                return MoveDirection.NORTH;
            }
        }
        if (diffX > 0) {
            if (diffX > abs(diffY)) {
                return MoveDirection.EAST;
            }
        }
        if (diffX < 0) {
            if (abs(diffX) > abs(diffY)) {
                return MoveDirection.WEST;
            }
        }
        return null;
    }

    /**
     * Moves the player
     *
     * @param direction the direction to move the player.
     */
    private void move(MoveDirection direction) {
        if (this.game.isPlayerMovePossible(direction)) {
            game.playerMove(direction);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getX() > GUIConfigs.getSidePanelWidth()) {
            MoveDirection direction = getDirection(e.getX(), e.getY());
            if (direction != null) {
                move(direction);
            }
        }
        ScalingAssistant sA = ScalingAssistant.getScalingAssistant();
        if ((e.getX() < GUIConfigs.getSidePanelWidth()) && (e.getY() > sA.scale(400))) {
            infoBoardClicked(e);
        }
        
        //Clicking in an area in the Side Panel where there are inventory boxes
        if ((e.getX() < GUIConfigs.getSidePanelWidth()) 
                && (e.getY() > sA.scale(225))
                && (e.getY() < sA.scale(225+65))) {
            inventoryBoxesClicked(e);
        }
        //Clicking in an area in the Side Panel where there are action buttons
        if ((e.getX() < GUIConfigs.getSidePanelWidth()) 
                && (e.getY() > sA.scale(315))
                && (e.getY() < sA.scale(315+65))) {
            actionButtonsClicked(e);
        }
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    private void infoBoardClicked(MouseEvent e) {
        ScalingAssistant sA = ScalingAssistant.getScalingAssistant();
        SidePanel sidePanel = loop.getSidePanel();
        Occupant[] occupants = sidePanel.getOccupants();
        
        
        if(occupants.length > 1) {
            if(sidePanel.getInfoOccupant() == null) {
                
                if (e.getY() > sA.scale(600)) {
                    sidePanel.setInfoOccupant(sidePanel.getOccupants()[1]);
                } else if (e.getY() > sA.scale(400)) {
                    sidePanel.setInfoOccupant(sidePanel.getOccupants()[0]);
                }
            } else {
                Occupant o = sidePanel.getInfoOccupant();
                if (e.getY() > sA.scale(735) && o instanceof Fauna) {
                    openLink(((Fauna)o).getLink());
                } else sidePanel.setInfoOccupant(null);                
            }            
            
        } else if (occupants.length == 1 && occupants[0] instanceof Fauna) {
            if (e.getY() > sA.scale(735)) {
               openLink(((Fauna)occupants[0]).getLink());
            }
        
        }

    }
    
    //open default browser
    private void openLink(String link) {
        if(Desktop.isDesktopSupported())
        {
            try {
                Desktop.getDesktop().browse(new URI(link));
            } catch (URISyntaxException | IOException ex) {
                Logger.getLogger(RenderingEngine.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    

    
    /**
     * Performs an "action" when a specific area in the game is clicked
     *
     * @param e 
     */
    private void inventoryBoxesClicked(MouseEvent e) {
        //ensures that the given pixels are scaled according to game settings
        ScalingAssistant sA = ScalingAssistant.getScalingAssistant();
        //Box 1 clicked
        if((e.getX() > sA.scale(35)) && (e.getX() < sA.scale(35+65))){
            useOrDrop(e,1);
        }
        //Box 2 clicked
        if((e.getX() > sA.scale(35+65+15)) && (e.getX() < sA.scale(35+(65*2)+15))){
            useOrDrop(e,2);
        }
        //Box 3 clicked
        if((e.getX() > sA.scale(35+((65+15)*2))) && (e.getX() < sA.scale(35+(65*3)+30))){
            useOrDrop(e,3);
        }
    }
    
    /**
     * Method for use or drop of item as displayed in the inventory boxes 
     *
     * @param e 
     * @param boxNumber tell which inventory box (1,2, or 3)
     */
    private void useOrDrop(MouseEvent e, int boxNumber){
        int boxIndex = boxNumber -1;
        if(game.getPlayerInventory().length == 0){}
            if(game.getPlayerInventory().length >= boxNumber){
                if(e.getClickCount()>1){game.dropItem(game.getPlayerInventory()[boxIndex]);} 
                else if(e.getClickCount()==1){game.useItem(game.getPlayerInventory()[boxIndex]);}
            }
    }
    
    /**
     * Performs an "action" when a specific area in the game is clicked
     *
     * @param e 
     */
    private void actionButtonsClicked(MouseEvent e) {
        //ensures that the given pixels are scaled according to game settings
        ScalingAssistant sA = ScalingAssistant.getScalingAssistant();
        //TAG if player is on a square where there is a Kiwi
        if((e.getX() > sA.scale(35)) && (e.getX() < sA.scale(35+65))){
            for(Occupant o: game.getOccupantsPlayerPosition()){
                if(game.canCount(o)){game.countKiwi();}
            }
        }
        //TRAP if player is on a square where there is a predator
        if((e.getX() > sA.scale(35+65+15)) && (e.getX() < sA.scale(35+(65*2)+15))){
            for(Occupant o: game.getOccupantsPlayerPosition()){
                //if(o.getStringRepresentation().contains("P")){
                    if(game.getPlayer().hasTrap()){game.useItem(game.getPlayer().getTrap());}
                //}
            }
        }
        //COLLECT if player is on a square where there is tool / food
        if((e.getX() > sA.scale(35+((65+15)*2))) && (e.getX() < sA.scale(35+(65*3)+30))){
            for(Occupant o: game.getOccupantsPlayerPosition()){
                if(game.canCollect(o)){
                    game.collectItem(o);
                }
            }
        }
    }
    
    
}
