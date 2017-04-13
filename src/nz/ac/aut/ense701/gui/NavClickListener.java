/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.ense701.gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import static java.lang.StrictMath.abs;
import nz.ac.aut.ense701.gameModel.Game;
import nz.ac.aut.ense701.gameModel.MoveDirection;
import nz.ac.aut.ense701.gameModel.Position;

/**
 *
 * @author Sam
 */
public class NavClickListener implements MouseListener{
    
    Game game;
    
    public NavClickListener(Game game) {
        this.game = game;
    }
    
    /**
     * Gets the center x coordinate for the square that the player is in
     * @param game
     * @return x coordinate
     */
    private int getPlayerCentreX(Game game) {
        return Globals.colToX(this.game.getPlayer().getPosition().getColumn()) + (Globals.getSquareWidth()/2);
    }
    
    /**
     * Gets the center x coordinate for the square that the player is in
     * @param game
     * @return x coordinate
     */
    private int getPlayerCentreY(Game game) {
        return Globals.rowToY(this.game.getPlayer().getPosition().getRow()) + (Globals.getSquareHeight()/2);
    }
    
    /**
     * Gets the move direction of a click at the given coordinates
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
            if (diffY > abs(diffX)) return MoveDirection.SOUTH;
        }
        if (diffY < 0) {
            if (abs(diffY) > abs(diffX)) return MoveDirection.NORTH;
        }
        if (diffX > 0) {
            if (diffX > abs(diffY)) return MoveDirection.EAST;
        }
        if (diffX < 0) {
            if (abs(diffX) > abs(diffY)) return MoveDirection.WEST;
        }
        return null;
    }
    
    
    /**
     * Moves the player
     * @param direction the direction to move the player. 
     */
    private void move(MoveDirection direction){
        if (this.game.isPlayerMovePossible(direction)) {
            game.playerMove(direction);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {  
        if(e.getX() > Globals.sidePanel_width){
            MoveDirection direction = getDirection(e.getX(), e.getY());
            if (direction != null) move(direction);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
    
}
