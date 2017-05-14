/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.ense701.gameModel;

import org.junit.Assert;

/**
 *
 * @author K Cortez
 */
public class AudioTest {
    
    public AudioTest(){
    
    }
    
     /**
     * Test AudioPlayer should not return null if load() is called.
     */
    public void testMusicPlaying(){
        boolean playing = false;
        
        AudioPlayer.load();
   
        if(AudioPlayer.getMusic("music").playing()==true){
            playing = true;
        }

        Assert.assertTrue(playing);
        
    }
    
    /**
     * Test AudioPlayer should return null if load() has not been called
    */
    public void testNullPlayingSound(){
        boolean playing = false;
        
        if(AudioPlayer.getMusic("music").playing()){
            playing = true;
        }

        Assert.assertFalse(playing);
        
    }
    
    
}
