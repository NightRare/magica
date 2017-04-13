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
    
    public static int width = 1100;
    public static int height = 800;
    public static int mapWidth = 800;
    private static int sidePanelWidth = width - mapWidth;
    private static int rows = 16;
    private static int columns = 16;
    
    private static ScalingAssistant scalingAssistant= ScalingAssistant.getScalingAssistant();
    private static final int[] BOARD_DIMENSIONS = new int[]{12,12,276,382};//x,y,width,height
    
    
    public static int getSidePanelWidth(){
        return scalingAssistant.scale(sidePanelWidth);
    }
    
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
        return scalingAssistant.scale(((mapWidth / columns) * col)) + getSidePanelWidth();
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
    
    /**
     * @return side panel board width
     */
    public static double boardWidth(){
        return (double)scalingAssistant.scale(BOARD_DIMENSIONS[2]);
    }
    
    /**
     * @return side panel board height
     */
    public static double boardHeight(){
        return (double)scalingAssistant.scale(BOARD_DIMENSIONS[3]);
    }
    
    /**
     * @return side panel board x origin
     */
    public static double boardOffsetX(){
        return (double)scalingAssistant.scale(BOARD_DIMENSIONS[0]);
    }
    
    /**
     * @return side panel board y origin
     */
    public static double boardOffsetY(){
        return (double)scalingAssistant.scale(BOARD_DIMENSIONS[1]);
    }
}
