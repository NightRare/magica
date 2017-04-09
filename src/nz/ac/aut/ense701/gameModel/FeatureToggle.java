/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.ense701.gameModel;

/**
 * A feature toggle class where some newly added features can be marked as enabled/disabled.
 * 
 * @author Yuan
 */
public class FeatureToggle {
    
    private boolean isUsingIDataManager;
    
    public FeatureToggle(boolean isUsingIDataManager) {
        this.isUsingIDataManager = isUsingIDataManager;
    }
    
    public boolean isUsingIDataManager() {
        return isUsingIDataManager;
    }
}
