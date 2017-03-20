/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.ense701.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import nz.ac.aut.ense701.gameModel.Game;

/**
 *
 * @author Sam
 */
public class RenderingEngine {

    GameLoop loop;


    public RenderingEngine(GameLoop loop) {
        this.loop = loop;
    }

    public void render(Graphics2D g2d) {
        
        ArrayList<NewMapSquare> squareList = loop.getMapSquareList();

        for (NewMapSquare square : squareList) {
            renderMapSquare(g2d, square);
        }

    }

    private void renderMapSquare(Graphics2D g2d, NewMapSquare square) {
        
        int xToRenderAt = Globals.colToX(square.getColumn());
        int yToRenderAt = Globals.rowToY(square.getRow());
        
        int yLowered = yToRenderAt + Globals.getSquareHeight()/2;

        g2d.setPaint(square.getColour());
        g2d.fill(new RoundRectangle2D.Double(xToRenderAt, yToRenderAt,
                100,
                100,
                10, 10));

        g2d.setColor(Color.RED);
        g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
        g2d.drawString(square.getLabel(), xToRenderAt, yLowered);
       
    }
}
