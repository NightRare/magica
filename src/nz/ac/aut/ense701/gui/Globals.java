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
    
    public static int width = 800;
    public static int height = 800;
    
    private static int rows = 10;
    private static int columns = 10;
    
    /**
     * Converts the row number to a y coordinate
     * @param row row number
     * @return starting y coordinate of square
     */
    public static int rowToY(int row) {
        return (height / rows) * row;
    }
    
    /**
     * Converts the column number to an x coordinate
     * @param col column number
     * @return starting x coordinate of square
     */
    public static int colToX(int col) {
        return (width / columns) * col;
    }
    
    /**
     * @return height of the squares 
     */
    public static int getSquareHeight() {
        return height / rows;
    }
    
    /**
     * @return width of the squares
     */
    public static int getSquareWidth() {
        return width / columns;
    }
}
