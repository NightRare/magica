/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.ense701.gui;

/**
 *
 * @author Sam
 */
public class Globals {
    
    public static final String TITLE = "Kiwi Island";
    
    public static int width = 800;
    public static int height = 800;
    
    // Map to canvas conversions
    private static int rows = 10;
    private static int columns = 10;
    
    public static int rowToY(int row) {
        return (height / rows) * row;
    }
    
    public static int colToX(int col) {
        return (width / columns) * col;
    }
    
    public static int getSquareHeight() {
        return height / rows;
    }
    
    public static int getSquareWidth() {
        return width / columns;
    }
}
