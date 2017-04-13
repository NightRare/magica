/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.ense701.gui;

/**
 * Settings and Related Methods that need to be accessed by multiple classes
 * @author Sam
 */
public class Globals {
    
    public static final String TITLE = "Kiwi Island";
    
    public static int width = 1200;
    public static int height = 800;
    public static int mapWidth = 800;
    public static int sidePanelWidth = width - mapWidth;
    private static int rows = 16;
    private static int columns = 16;
    
    private static ScalingAssistant scalingAssistant= ScalingAssistant.getScalingAssistant();
    public static int sidePanel_width = 4 * scalingAssistant.getScale();
    /**
     * Converts the row number to a y coordinate
     * @param row row number
     * @return starting y coordinate of square
     */
    public static int rowToY(int row) {
        return scalingAssistant.scale((height / rows) * row);
    }
    
    /**
     * Converts the column number to an x coordinate
     * @param col column number
     * @return starting x coordinate of square
     */
    public static int colToX(int col) {
        return scalingAssistant.scale(((mapWidth / columns) * col) + sidePanel_width );
    }
    
    /**
     * @return height of the squares 
     */
    public static int getSquareHeight() {
        return scalingAssistant.scale(height / rows);
    }
    
    /**
     * @return width of the squares
     */
    public static int getSquareWidth() {
        return scalingAssistant.scale(mapWidth / columns);
    }
}
