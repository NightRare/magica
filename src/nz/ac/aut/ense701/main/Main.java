package nz.ac.aut.ense701.main;

import java.io.IOException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import nz.ac.aut.ense701.gameModel.*;
import nz.ac.aut.ense701.gui.GameLoop;

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

        try {
            // initialise FeatureToggle
            FeatureToggle ft = new FeatureToggle();
            ft.debug_setMapVisible();

            // initialise IDataManager
            IDataManager dm = JsonProcessor.make("data/Occupants.json",
                    "data/OccupantsMap.json", "data/OccupantsPool.json");

            Game game = new Game(ft, dm);
            GameLoop gameLoop = new GameLoop(game);

            gameLoop.start();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
