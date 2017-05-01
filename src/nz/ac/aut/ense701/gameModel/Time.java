/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.ense701.gameModel;

/**
 * Stores the time of day it is
 * @author Sam
 */
public class Time {
    
    private int turn;
    
    public Time() {
        this.turn = 0;
    }
    
    /**
     * Signals the passing of a turn
     */
    public void tick() {
        this.turn += 1;
    }
    
    /**
     * Determines whether it is day or night
     * @return LightLevel DAY or NIGHT
     */
    public LightLevel dayOrNight() {
        if (turn%20 < 10) return LightLevel.DAY;
        else return LightLevel.NIGHT;
    }
    
}
