package nz.ac.aut.ense701.main;

import nz.ac.aut.ense701.gameModel.FeatureToggle;
import nz.ac.aut.ense701.gameModel.Game;
import nz.ac.aut.ense701.gameModel.IDataManager;
import nz.ac.aut.ense701.gameModel.JsonProcessor;
import nz.ac.aut.ense701.gui.GameLoop;

import java.util.Locale;

/**
 * Kiwi Count Project
 * 
 * @author AS
 * @version 2011
 */
public class Main 
{
    /**
     * Main method of Kiwi Count.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        // set the locale to English regardless of the system environment
        Locale.setDefault(Locale.ENGLISH);


        // initialise FeatureToggle
        FeatureToggle ft = new FeatureToggle();
//        ft.debug_setMapVisible();

        // initialise IDataManager
        IDataManager dm = JsonProcessor.make("data/Occupants.json",
                "data/OccupantsMap.json", "data/OccupantsPool.json");

        Game game = new Game(ft, dm);
        GameLoop gameLoop = new GameLoop(game);

        gameLoop.start();
    }

}
