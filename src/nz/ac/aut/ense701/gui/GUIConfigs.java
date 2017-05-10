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
public class GUIConfigs {
    
    public static final String TITLE = "Kiwi Island";
    
    public static final int WINDOW_WIDTH = 1100;
    public static final int WINDOW_HEIGHT = 800;
    public static final int MAP_WIDTH = 800;
    private static final int SIDEPANEL_WIDTH = WINDOW_WIDTH - MAP_WIDTH;
    private static final int MAP_ROWS = 16;
    private static final int MAP_COLUMNS = 16;
    
    private static ScalingAssistant scalingAssistant= ScalingAssistant.getScalingAssistant();
    private static final int[] BOARD_DIMENSIONS = new int[]{12,12,276,382};//x,y,WINDOW_WIDTH,WINDOW_HEIGHT
    
    
    public static int getSidePanelWidth(){
        return scalingAssistant.scale(SIDEPANEL_WIDTH);
    }
    
    /**
     * Converts the row number to a y coordinate
     * @param row row number
     * @return starting y coordinate of square
     */
    public static int rowToY(int row) {
        return scalingAssistant.scale((WINDOW_HEIGHT / MAP_ROWS) * row);
    }
    
    /**
     * Converts the column number to an x coordinate
     * @param col column number
     * @return starting x coordinate of square
     */
    public static int colToX(int col) {
        return scalingAssistant.scale(((MAP_WIDTH / MAP_COLUMNS) * col)) + getSidePanelWidth();
    }
    
    /**
     * @return WINDOW_HEIGHT of the squares 
     */
    public static int getSquareHeight() {
        return scalingAssistant.scale(WINDOW_HEIGHT / MAP_ROWS);
    }
    
    /**
     * @return WINDOW_WIDTH of the squares
     */
    public static int getSquareWidth() {
        return scalingAssistant.scale(MAP_WIDTH / MAP_COLUMNS);
    }
    
    /**
     * @return side panel board WINDOW_WIDTH
     */
    public static double boardWidth(){
        return (double)scalingAssistant.scale(BOARD_DIMENSIONS[2]);
    }
    
    /**
     * @return side panel board WINDOW_HEIGHT
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
