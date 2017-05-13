/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.ense701.gameModel;

import java.util.HashMap;
import java.util.Map;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import org.junit.Test;
import org.newdawn.slick.Music;
import org.newdawn.slick.Sound;

/**
 *
 * @author K Cortez
 */
public class AudioTest {
    public static Map<String, Sound> soundMap = new HashMap<String, Sound>();
    public static Map<String, Music> musicMap = new HashMap<String, Music>();
    
    public AudioTest(){
    
    }
    
 @Test
    public void testSoundMap(){
        assertTrue("Should have sound files", soundMap.isEmpty());
        
    }
    
 @Test
    public void testMusicMap(){
        
        assertTrue("Should be empty", musicMap.isEmpty());
    }
    
  @Test
    public void testMusicMapObject(){
        
        assertFalse("Should have sound files", musicMap.containsKey("bg-music"));
    }   
    
  @Test
    public void testSoundMapObject(){
        
        assertFalse("Should have sound files", soundMap.containsKey("sm-music"));
    }   
    
}
