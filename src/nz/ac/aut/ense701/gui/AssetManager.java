/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.ense701.gui;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import nz.ac.aut.ense701.gameModel.IDataManager;
import nz.ac.aut.ense701.gameModel.JsonProcessor;
import nz.ac.aut.ense701.gameModel.Occupant;

/**
 * Singleton class for managing graphics assets. Stores all buffered images for the game with load method and getters.
 * @author Sam
 */
public class AssetManager {
    
    private BufferedImage map, empty, water, scrub, wetland, forest, sand, player, black, grey, night; 
    private BufferedImage animal, food, tool, hazard; //inventory items
    private BufferedImage tag, trap, collect; //action boxes
    private BufferedImage playerIcon,questIcon,inventoryEmpty,inventorySnack,
                inventoryToolbox,inventoryApple,inventoryTrap;
    private BufferedImage taggedBubble, trappedBubble, yumBubble;
    private HashMap<String, BufferedImage> occupantsPortraits;
    private IDataManager dataManager;
    
    private BufferedImageLoader loader;
    
    private static AssetManager assetManager;
    
    private AssetManager()  {
        this.loader = new BufferedImageLoader();
        try {
            dataManager = JsonProcessor.make("data/Occupants.json",
                    "data/OccupantsMap.json", "data/OccupantsPool.json");
        } catch (IOException ex) {
            Logger.getLogger(AssetManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        loadTextures();
        loadOccupantsPortraits();
    }
    
    public static AssetManager getAssetManager() {
        if (assetManager == null) {
            assetManager = new AssetManager();
        }
        return assetManager;
    }
    
    public void loadTextures() {
        ScalingAssistant scaleAssist = ScalingAssistant.getScalingAssistant();
        map = scaleAssist.getScaledImage((loader.loadImage("images/whole_map.png")), scaleAssist.getScale());
        empty = scaleAssist.getScaledImage((loader.loadImage("images/debug.png")), scaleAssist.getScale());
//        water = scaleAssist.getScaledImage((loader.loadImage("images/tile_water.png")), scaleAssist.getScale());
//        scrub = scaleAssist.getScaledImage((loader.loadImage("images/tile_scrub.png")), scaleAssist.getScale());
//        wetland = scaleAssist.getScaledImage((loader.loadImage("images/tile_wetland.png")), scaleAssist.getScale());
//        forest = scaleAssist.getScaledImage((loader.loadImage("images/tile_forest.png")), scaleAssist.getScale());
//        sand = scaleAssist.getScaledImage((loader.loadImage("images/tile_sand.png")), scaleAssist.getScale());
        player = scaleAssist.getScaledImage((loader.loadImage("images/map_icons/player_1.png")), scaleAssist.getScale());
        black = scaleAssist.getScaledImage((loader.loadImage("images/black.png")), scaleAssist.getScale());
        grey = scaleAssist.getScaledImage((loader.loadImage("images/grey.png")), scaleAssist.getScale());
        night = scaleAssist.getScaledImage((loader.loadImage("images/night.png")), scaleAssist.getScale());
        
        animal = scaleAssist.getScaledImage((loader.loadImage("images/map_icons/animal.png")), scaleAssist.getScale());
        food = scaleAssist.getScaledImage((loader.loadImage("images/map_icons/food.png")), scaleAssist.getScale());
        tool = scaleAssist.getScaledImage((loader.loadImage("images/map_icons/tool.png")), scaleAssist.getScale());
        hazard = scaleAssist.getScaledImage((loader.loadImage("images/map_icons/hazard.png")), scaleAssist.getScale());
        
        playerIcon = scaleAssist.getScaledImage((loader.loadImage("images/sidepanel_player.png")), scaleAssist.getScale());
        questIcon = scaleAssist.getScaledImage((loader.loadImage("images/sidepanel_quest.png")), scaleAssist.getScale());
        inventoryEmpty = scaleAssist.getScaledImage((loader.loadImage("images/inventory_empty.png")), scaleAssist.getScale());
        inventorySnack = scaleAssist.getScaledImage((loader.loadImage("images/inventory_snack.png")), scaleAssist.getScale());
        inventoryToolbox = scaleAssist.getScaledImage((loader.loadImage("images/inventory_screwdriver.png")), scaleAssist.getScale());
        inventoryApple = scaleAssist.getScaledImage((loader.loadImage("images/inventory_apple.png")), scaleAssist.getScale());
        inventoryTrap = scaleAssist.getScaledImage((loader.loadImage("images/inventory_trap.png")), scaleAssist.getScale());
        
        tag = scaleAssist.getScaledImage((loader.loadImage("images/action/tag.png")), scaleAssist.getScale());
        trap = scaleAssist.getScaledImage((loader.loadImage("images/action/trap.png")), scaleAssist.getScale());
        collect = scaleAssist.getScaledImage((loader.loadImage("images/action/collect.png")), scaleAssist.getScale());
        
        taggedBubble = scaleAssist.getScaledImage((loader.loadImage("images/notification/tagged.png")), scaleAssist.getScale());
        trappedBubble = scaleAssist.getScaledImage((loader.loadImage("images/notification/trapped.png")), scaleAssist.getScale());
        yumBubble = scaleAssist.getScaledImage((loader.loadImage("images/notification/yum.png")), scaleAssist.getScale());
    }
    
    
    /**
     * Get the image object of an occupant given its name.
     * @param occupantName the name of an occupant.
     * @return the image object of an occupant.
     * @throws IllegalArgumentException
     */
    public BufferedImage getOccupantPortrait (String occupantName) {
        if (occupantName == null || occupantName.isEmpty())
            throw new IllegalArgumentException(
                    "Occupant name cannot be null or empty.");

        if (occupantsPortraits == null)
            loadOccupantsPortraits();

        return occupantsPortraits.get(occupantName);
    }

    private void loadOccupantsPortraits () {
        ScalingAssistant scaleAssist = ScalingAssistant.getScalingAssistant();
        occupantsPortraits = new HashMap();
        for (Occupant o : dataManager.getAllOccupantTemplates() ) {
            BufferedImage bf = scaleAssist.getScaledImage(
                (loader.loadImage(o.getPortrait())), 
                scaleAssist.getScale());
            occupantsPortraits.put(o.getName(), bf);
        }
    }

    /*
    public void loadOccupantsImages() {
        ScalingAssistant scaleAssist = ScalingAssistant.getScalingAssistant();
        kiwi = scaleAssist.getScaledImage(
            (loader.loadImage("images/occupants/kiwi.jpg")), 
            scaleAssist.getScale()
            );
    }
    */
    
    public BufferedImage getMap() {
        return map;
    }
    
    public BufferedImage getEmpty() {
        return empty;
    }
    
    public BufferedImage getWater() {
        return water;
    }

    public BufferedImage getScrub() {
        return scrub;
    }

    public BufferedImage getWetland() {
        return wetland;
    }

    public BufferedImage getForest() {
        return forest;
    }

    public BufferedImage getSand() {
        return sand;
    }

    public BufferedImage getPlayer() {
        return player;
    }

    public BufferedImage getBlack() {
        return black;
    }

    public BufferedImage getGrey() {
        return grey;
    }

    public BufferedImage getAnimal() {
        return animal;
    }

    public BufferedImage getFood() {
        return food;
    }

    public BufferedImage getTool() {
        return tool;
    }

    public BufferedImage getHazard() {
        return hazard;
    }

    public BufferedImage getPlayerIcon() {
        return playerIcon;
    }

    public BufferedImage getQuestIcon() {
        return questIcon;
    }

    public BufferedImage getInventoryEmpty() {
        return inventoryEmpty;
    }

    public BufferedImage getInventorySnack() {
        return inventorySnack;
    }

    public BufferedImage getInventoryToolbox() {
        return inventoryToolbox;
    }

    public BufferedImage getInventoryApple() {
        return inventoryApple;
    }

    public BufferedImage getInventoryTrap() {
        return inventoryTrap;
    }
    
    public BufferedImage getActionTag() {
        return tag;
    }

    public BufferedImage getActionTrap() {
        return trap;
    }

    public BufferedImage getActionCollect() {
        return collect;
    }

    public BufferedImage getNight() {
        return night;
    }

    public BufferedImage getTaggedBubble() {
        return taggedBubble;
    }
    
    public BufferedImage getTrappedBubble() {
        return trappedBubble;
    }
    
    public BufferedImage getYumBubble() {
        return yumBubble;
    }
}
