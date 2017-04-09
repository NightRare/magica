/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.ense701.gui;


import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

/**
 * The ScalingAssistant is a singleton design class which is used for storing information
 * regarding the current display size. It provides methods to assist with scaling images
 * and numbers by the same percentage as the display.
 * @author Sam Clough 15910515
 */


///////////////////////     SINGLETON


public class ScalingAssistant {
    
    private int scale = 100;

    private static ScalingAssistant scalingAssistant;
    
    /**
     * Private constructor - singleton
     */
    private ScalingAssistant(){
    }
    
    
    /**
     * Gets the instance of the scaling assistant or creates one if there is none
     * @return scaling assistant instance
     */
    public static ScalingAssistant getScalingAssistant() {
        if (scalingAssistant == null) {
            scalingAssistant = new ScalingAssistant();
        }
        return scalingAssistant;
    }
    
    
    
    /**
     * Sets the scale
     * @param scale the scale (percentage integer)
     */
    public void setScale(int scale) {
        this.scale = scale;
    }
    
    /**
     * Gets the scale
     * @return the scale (percentage integer)
     */
    public int getScale() {
        return scale;
    }
    
    /**
     * Scales an input number by the percentage that the display is currently scaled
     * @param input a number
     * @return a different number
     */
    public int scale(int input) {
        int output = (input * scale) / 100;
        return output;
    }
    
    /**
     * Scales a buffered image by a given percentage
     * @param src source image
     * @param percentage the percentage to scale by
     * @return scaled image
     */
    public BufferedImage getScaledImage(BufferedImage src, int percentage) {
        int w = src.getWidth() * percentage / 100;
        int h = src.getHeight() * percentage / 100;
        Image srcImg = (Image) src;
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TRANSLUCENT);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();
        return resizedImg;
    }
}