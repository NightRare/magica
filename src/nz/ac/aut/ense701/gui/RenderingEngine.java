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
import java.util.ArrayList;
import nz.ac.aut.ense701.gameModel.Game;
import nz.ac.aut.ense701.gameModel.GameState;
import nz.ac.aut.ense701.gameModel.LightLevel;

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

    

    public RenderingEngine(GameLoop loop) {
        
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
        loop.getSidePanel().render(g2d);
        
        loop.getPlayerSprite().render(g2d);
        //renders notifications
        game.getNotification().render(g2d);

        if (game.lightLevel() == LightLevel.NIGHT) {
            g2d.drawImage(AssetManager.getAssetManager().getNight(), null, GUIConfigs.colToX(0), GUIConfigs.rowToY(0));
        }
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

        g2d.drawImage(square.getOccupantIcon(square.getOccupants()),
                xToRenderAt, yToRenderAt,
                scaleAssist.scale(50), scaleAssist.scale(50), null);
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


}
