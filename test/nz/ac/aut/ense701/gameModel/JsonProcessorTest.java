/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.ense701.gameModel;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import junit.framework.Assert;
import org.junit.Test;

/**
 *
 * @author Vince
 */
public class JsonProcessorTest extends junit.framework.TestCase {
    
    final String OCCUPANTS_FILEPATH = "testdata/Occupants.json";
    final String OCCUPANTSMAP_FILEPATH = "testdata/OccupantsMap.json";
    
    Island island;
    String occupantsJson;
    String occupantsMapJson;
    IDataManager dataManager;
    
    /**
     * Default constructor 
     */
    public JsonProcessorTest() { }

     /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Override
    protected void setUp()
    {
        island = new Island(5,5);
        occupantsJson = "{\n" +
                        "	\"tools\":\n" +
                        "	[\n" +
                        "		{\n" +
                        "			\"name\":\"Trap\",\n" +
                        "			\"description\":\"A trap for predators\",\n" +
                        "			\"weight\":1.0,\n" +
                        "			\"size\":1.0,\n" +
                        "			\"portrait\":\"\"\n" +
                        "		}\n" +
                        "	],\n" +
                        "\n" +
                        "	\"food\": [],\n" +
                        "	\"faunae\": [],\n" +
                        "	\"predators\": [],\n" +
                        "	\"hazards\": [],\n" +
                        "	\"kiwis\": []\n" +
                        "}";
        
        occupantsMapJson =  "[\n" +
                            "	{\n" +
                            "		\"position\":{\n" +
                            "			\"row\":0,\n" +
                            "			\"column\":4\n" +
                            "		},\n" +
                            "\n" +
                            "		\"occupants\":[\n" +
                            "			\"Trap\"\n" +
                            "		]\n" +
                            "	}\n" +
                            "]";
        
        writeJsonToFiles(occupantsJson, OCCUPANTS_FILEPATH);
        writeJsonToFiles(occupantsMapJson, OCCUPANTSMAP_FILEPATH);
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @Override
    protected void tearDown()
    {
        island = null;
        occupantsJson = null;
        occupantsMapJson = null;
        dataManager = null;
    }

    @Test
    public void testMakeWithIllegalArgument() {
        try {
            dataManager = JsonProcessor.make(null, null);
            
            fail("Should have thrown IllegalArgumentException");
        } catch (IOException ex) {
            Logger.getLogger(JsonProcessorTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            // pass test
        }
        
        try {
            dataManager = JsonProcessor.make("", "");
            
            fail("Should have thrown IllegalArgumentException");
        } catch (IOException ex) {
            Logger.getLogger(JsonProcessorTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            // pass test
        }
        
        try {
            dataManager = JsonProcessor.make("nosuchfile.json", "wrongfile.txt");
            
            fail("Should have thrown IOException");
        } catch (IOException ex) {
            // pass test
        }
    }
    
    @Test
    public void testDataIntegrity() {
        // to be implemented
    }
    
    
    @Test
    public void testGetOccupantsInPosition() {
        try {
            dataManager = JsonProcessor.make(OCCUPANTS_FILEPATH, OCCUPANTSMAP_FILEPATH);            
            Set<Occupant> occupants = dataManager.getOccupantsInPosition(new Position(island, 0, 4));
            
            Assert.assertEquals("There should only be 1 occupant", 1, occupants.size());
            
            for(Occupant o : occupants) {
                Assert.assertTrue("The type is incorrect.", o instanceof Tool);
                Tool tool = (Tool) o;
                Assert.assertEquals("The name is incorrect", "Trap", tool.getName());
                Assert.assertEquals("The description is incorrect", "A trap for predators", tool.getDescription());
                Assert.assertEquals("The weight shall be 1.0", 1.0, tool.getWeight());
                Assert.assertEquals("The size shall be 1.0", 1.0, tool.getSize());
                Assert.assertEquals("Row number should be 0", 0, tool.getPosition().getRow());
                Assert.assertEquals("Column number should be 4", 4, tool.getPosition().getColumn());
            }
        } catch (IOException ex) {
            Logger.getLogger(JsonProcessorTest.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    @Test
    public void testGetOccupantsInEmptyPosition() {
        try {
            dataManager = JsonProcessor.make(OCCUPANTS_FILEPATH, OCCUPANTSMAP_FILEPATH);            
            Set<Occupant> occupants = dataManager.getOccupantsInPosition(new Position(island, 2, 4));
            
            Assert.assertEquals("There should be no occupants", 0, occupants.size());
            
        } catch (IOException ex) {
            Logger.getLogger(JsonProcessorTest.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    
    @Test
    public void testGetOccupantsInPositionWithIllegalArgument() {
        try {
            dataManager = JsonProcessor.make(OCCUPANTS_FILEPATH, OCCUPANTSMAP_FILEPATH);
            
            //pass in null as argument
            Set<Occupant> occupants = dataManager.getOccupantsInPosition(null);
            
            fail("IllegalArgumentException should be thrown");  
        } catch (IOException ex) {
            Logger.getLogger(JsonProcessorTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            // pass test
        }
    }
    
    private void writeJsonToFiles(String json, String path) {
        try(FileWriter fw = new FileWriter(path, false)) {
            fw.write(json);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
}
