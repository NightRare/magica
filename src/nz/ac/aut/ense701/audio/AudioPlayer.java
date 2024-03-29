/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.ense701.audio;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

/**
 *
 * @author K Cortez
 */
public class AudioPlayer {
    
    public static Map<String, Sound> soundMap = new HashMap<String, Sound>();
    public static Map<String, Music> musicMap = new HashMap<String, Music>();
    
    public static void load(){
        
        try {
            soundMap.put("error_sound",new Sound("sound/Beep10.ogg"));
            musicMap.put("music", new Music("sound/KiwiIslandBG.ogg"));
        } catch (SlickException ex) {
            Logger.getLogger(AudioPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static Music getMusic(String key){
        return musicMap.get(key);
    }
    
    public static Sound getSound(String key){
        return soundMap.get(key);
    }
    
}


