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
    private boolean mapVisible;
    private boolean occupantsOnCertainTerrains;

    /**
     * <p>Default toggles:</p>
     * <p>
     * <p>isUsingIDataManager = true;</p>
     * <p>mapVisible = false;</p>
     */
    public FeatureToggle() {
        isUsingIDataManager = true;
        mapVisible = false;
        occupantsOnCertainTerrains = true;
    }

    public void disableIDataManager() {
        isUsingIDataManager = false;
    }

    public void debug_setMapVisible() {
        mapVisible = true;
    }

    public void disableOccupantsOnCertainTerrains() {
        this.occupantsOnCertainTerrains = false;
    }

    public boolean isUsingIDataManager() {
        return isUsingIDataManager;
    }

    public boolean isMapVisible() {
        return mapVisible;
    }

    public boolean occupantsOnCertainTerrains() {
        return occupantsOnCertainTerrains;
    }

}
