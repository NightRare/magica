/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.ense701.gui;

import java.awt.image.BufferedImage;

/**
 * Singleton class for managing graphics assets. Stores all buffered images for the game with load method and getters.
 * @author Sam
 */
public class AssetManager {
    
    private BufferedImage water, scrub, wetland, forest, sand, player, black, grey;
    private BufferedImage animal, food, tool, hazard;
    private BufferedImage playerIcon,questIcon,inventoryEmpty,inventorySnack,
                inventoryToolbox,inventoryApple,inventoryTrap;
    
    
    private BufferedImageLoader loader;
    
    private static AssetManager assetManager;
    
    private AssetManager() {
        this.loader = new BufferedImageLoader();
        loadTextures();
    }
    
    public static AssetManager getAssetManager() {
        if (assetManager == null) {
            assetManager = new AssetManager();
        }
        return assetManager;
    }
    
    public void loadTextures() {
        ScalingAssistant scaleAssist = ScalingAssistant.getScalingAssistant();
        
        water = scaleAssist.getScaledImage((loader.loadImage("/resource/images/tile_water.png")), scaleAssist.getScale());
        scrub = scaleAssist.getScaledImage((loader.loadImage("/resource/images/tile_scrub.png")), scaleAssist.getScale());
        wetland = scaleAssist.getScaledImage((loader.loadImage("/resource/images/tile_wetland.png")), scaleAssist.getScale());
        forest = scaleAssist.getScaledImage((loader.loadImage("/resource/images/tile_forest.png")), scaleAssist.getScale());
        sand = scaleAssist.getScaledImage((loader.loadImage("/resource/images/tile_sand.png")), scaleAssist.getScale());
        player = scaleAssist.getScaledImage((loader.loadImage("/resource/images/player_01.png")), scaleAssist.getScale());
        black = scaleAssist.getScaledImage((loader.loadImage("/resource/images/black.png")), scaleAssist.getScale());
        grey = scaleAssist.getScaledImage((loader.loadImage("/resource/images/grey.png")), scaleAssist.getScale());
        
        animal = scaleAssist.getScaledImage((loader.loadImage("/resource/images/animal.png")), scaleAssist.getScale());
        food = scaleAssist.getScaledImage((loader.loadImage("/resource/images/food.png")), scaleAssist.getScale());
        tool = scaleAssist.getScaledImage((loader.loadImage("/resource/images/tool.png")), scaleAssist.getScale());
        hazard = scaleAssist.getScaledImage((loader.loadImage("/resource/images/hazard.png")), scaleAssist.getScale());
        
        playerIcon = scaleAssist.getScaledImage((loader.loadImage("/resource/images/sidepanel_player.png")), scaleAssist.getScale());
        questIcon = scaleAssist.getScaledImage((loader.loadImage("/resource/images/sidepanel_quest.png")), scaleAssist.getScale());
        inventoryEmpty = scaleAssist.getScaledImage((loader.loadImage("/resource/images/inventory_empty.png")), scaleAssist.getScale());
        inventorySnack = scaleAssist.getScaledImage((loader.loadImage("/resource/images/inventory_snack.png")), scaleAssist.getScale());
        inventoryToolbox = scaleAssist.getScaledImage((loader.loadImage("/resource/images/inventory_toolbox.png")), scaleAssist.getScale());
        inventoryApple = scaleAssist.getScaledImage((loader.loadImage("/resource/images/inventory_apple.png")), scaleAssist.getScale());
        inventoryTrap = scaleAssist.getScaledImage((loader.loadImage("/resource/images/inventory_trap.png")), scaleAssist.getScale());
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
    
}
