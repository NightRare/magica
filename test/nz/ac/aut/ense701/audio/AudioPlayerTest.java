package nz.ac.aut.ense701.audio;

import org.junit.Test;

import java.util.HashMap;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author K Cortez
 */
public class AudioPlayerTest extends junit.framework.TestCase {
    
     /**
     * Default constructor for test class AudioPlayerTest
     */
    public AudioPlayerTest()
    {
        
    }
    
    @Test
    public void testAudioPlayer(){
        
        //Background Music
        AudioPlayer.load();
        AudioPlayer.getMusic("music").loop(1.0f,0.4f);
        
        //At start of game sound will play
        assertTrue ("Playing background music should be valid ",AudioPlayer.getMusic("music").playing());
        //Error sound valid when called
        AudioPlayer.getSound("error_sound").play();
        assertTrue ("Playing background music should be valid ",AudioPlayer.getSound("error_sound").playing());

    }
    
}
