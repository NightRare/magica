/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.ense701.gui;

import java.awt.image.BufferedImage;
import java.util.*;

import nz.ac.aut.ense701.gameModel.IDataManager;
import nz.ac.aut.ense701.gameModel.JsonProcessor;
import nz.ac.aut.ense701.gameModel.Occupant;

/**
 * Singleton class for managing graphics assets. Stores all buffered images for the game with load method and getters.
 * @author Sam
 */
public class AssetManager {
    
    private BufferedImage map, visible, water, scrub, wetland, forest, sand, player, dark, fog, night; 
    private BufferedImage animal, food, tool, hazard; //inventory items
    private BufferedImage tag, trap, collect; //action boxes
    private BufferedImage playerFace_happy,playerFace_neutral, playerFace_hungry, playerFace_tired,questIcon,
            inventoryEmpty,inventorySnack, inventoryToolbox,inventoryApple,inventoryTrap, inventoryRatTrap, inventoryCatTrap, inventoryA24Trap;
    private BufferedImage taggedBubble, trappedBubble, yumBubble;
    private HashMap<String, BufferedImage> occupantsPortraits;
    private IDataManager dataManager;
    
    private BufferedImageLoader loader;
    
    private static AssetManager assetManager;
    
    private AssetManager()  {
        this.loader = new BufferedImageLoader();
        dataManager = JsonProcessor.make("data/Occupants.json",
                "data/OccupantsMap.json", "data/OccupantsPool.json");

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
        map = scaleAssist.getScaledImage((loader.loadImage("images/map_objects/whole_map.png")), scaleAssist.getScale());
        visible = scaleAssist.getScaledImage((loader.loadImage("images/map_objects/visible.png")), scaleAssist.getScale());
        dark = scaleAssist.getScaledImage((loader.loadImage("images/map_objects/dark.png")), scaleAssist.getScale());
        fog = scaleAssist.getScaledImage((loader.loadImage("images/map_objects/fog.png")), scaleAssist.getScale());
        player = scaleAssist.getScaledImage((loader.loadImage("images/map_icons/player_1.png")), scaleAssist.getScale());
        night = scaleAssist.getScaledImage((loader.loadImage("images/night.png")), scaleAssist.getScale());
        
        animal = scaleAssist.getScaledImage((loader.loadImage("images/map_icons/animal.png")), scaleAssist.getScale());
        food = scaleAssist.getScaledImage((loader.loadImage("images/map_icons/food.png")), scaleAssist.getScale());
        tool = scaleAssist.getScaledImage((loader.loadImage("images/map_icons/tool.png")), scaleAssist.getScale());
        hazard = scaleAssist.getScaledImage((loader.loadImage("images/map_icons/hazard.png")), scaleAssist.getScale());
        
        playerFace_happy = scaleAssist.getScaledImage((loader.loadImage("images/side_panel/happy.png")), scaleAssist.getScale());
        playerFace_neutral = scaleAssist.getScaledImage((loader.loadImage("images/side_panel/neutral.png")), scaleAssist.getScale());
        playerFace_hungry = scaleAssist.getScaledImage((loader.loadImage("images/side_panel/hungry.png")), scaleAssist.getScale());
        playerFace_tired = scaleAssist.getScaledImage((loader.loadImage("images/side_panel/tired.png")), scaleAssist.getScale());
        questIcon = scaleAssist.getScaledImage((loader.loadImage("images/side_panel/quest_board.png")), scaleAssist.getScale());
        inventoryEmpty = scaleAssist.getScaledImage((loader.loadImage("images/inventory_icons/inventory_empty.png")), scaleAssist.getScale());
        inventorySnack = scaleAssist.getScaledImage((loader.loadImage("images/inventory_icons/inventory_snack.png")), scaleAssist.getScale());
        inventoryToolbox = scaleAssist.getScaledImage((loader.loadImage("images/inventory_icons/inventory_screwdriver.png")), scaleAssist.getScale());
        inventoryApple = scaleAssist.getScaledImage((loader.loadImage("images/inventory_icons/inventory_apple.png")), scaleAssist.getScale());
        inventoryTrap = scaleAssist.getScaledImage((loader.loadImage("images/inventory_icons/inventory_trap.png")), scaleAssist.getScale());
        inventoryRatTrap = scaleAssist.getScaledImage((loader.loadImage("images/inventory_icons/inventory_ratTrap.png")), scaleAssist.getScale()); 
        inventoryCatTrap = scaleAssist.getScaledImage((loader.loadImage("images/inventory_icons/inventory_catTrap.png")), scaleAssist.getScale()); 
        inventoryA24Trap = scaleAssist.getScaledImage((loader.loadImage("images/inventory_icons/inventory_a24Trap.png")), scaleAssist.getScale()); 
        
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
        for (Occupant o : dataManager.getAllOccupantPrototypes() ) {
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
    
    public BufferedImage getVisible() {
        return visible;
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

    public BufferedImage getDark() {
        return dark;
    }

    public BufferedImage getFog() {
        return fog;
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

    public BufferedImage getPlayerFace_happy() {
        return playerFace_happy;
    }
    
    public BufferedImage getPlayerFace_neutral() {
        return playerFace_neutral;
    }
    
    public BufferedImage getPlayerFace_hungry() {
        return playerFace_hungry;
    }
    
    public BufferedImage getPlayerFace_tired() {
        return playerFace_tired;
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
    
    public BufferedImage getInventoryRatTrap() { 
        return inventoryRatTrap; 
    }
    
    public BufferedImage getInventoryCatTrap() { 
        return inventoryCatTrap; 
    }
    
    public BufferedImage getInventoryA24Trap() { 
        return inventoryA24Trap; 
    }
}
