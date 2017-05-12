/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.ense701.gameModel;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import junit.framework.Assert;
import org.junit.Test;

/**
 * @author Yuan
 */
public class JsonProcessorTest extends junit.framework.TestCase {

    final String OCCUPANTS_FILEPATH = "testdata/Occupants.json";
    final String OCCUPANTSMAP_FILEPATH = "testdata/OccupantsMap.json";
    final String OCCUPANTPOOL_FILEPATH = "testdata/OccupantsPool.json";

    private Island island;
    private String occupantsJson;
    private String occupantsMapJson;
    private String occupantsPoolJson;
    private IDataManager dataManager;

    /**
     * Default constructor
     */
    public JsonProcessorTest() {
    }

    /**
     * Sets up the test fixture.
     * <p>
     * Called before every test case method.
     */
    @Override
    protected void setUp() {
        island = new Island(5, 5);
        occupantsJson = "{\n" +
                "	\"tools\":\n" +
                "	[\n" +
                "		{\n" +
                "			\"name\":\"Trap\",\n" +
                "			\"description\":\"A trap for predators\",\n" +
                "			\"weight\":1.0,\n" +
                "			\"size\":2.0,\n" +
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

        occupantsMapJson = "[\n" +
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

        occupantsPoolJson = "{\n" +
                "  \"food\":{\n" +
                "  },\n" +
                "\n" +
                "  \"tools\":{\n" +
                "    \"Trap\":3\n" +
                "  },\n" +
                "\n" +
                "  \"faunae\":{\n" +
                "  },\n" +
                "\n" +
                "  \"kiwis\": {\n" +
                "  },\n" +
                "\n" +
                "  \"predators\":{\n" +
                "  },\n" +
                "\n" +
                "  \"hazards\":{\n" +
                "  }\n" +
                "}";

        writeJsonToFiles(occupantsJson, OCCUPANTS_FILEPATH);
        writeJsonToFiles(occupantsMapJson, OCCUPANTSMAP_FILEPATH);
        writeJsonToFiles(occupantsPoolJson, OCCUPANTPOOL_FILEPATH);

        dataManager = JsonProcessor.make(OCCUPANTS_FILEPATH, OCCUPANTSMAP_FILEPATH, OCCUPANTPOOL_FILEPATH);
    }

    /**
     * Tears down the test fixture.
     * <p>
     * Called after every test case method.
     */
    @Override
    protected void tearDown() {
        island = null;
        occupantsJson = null;
        occupantsMapJson = null;
        dataManager = null;
    }

    @Test
    public void testMakeWithIllegalArgument() {
        try {
            dataManager = JsonProcessor.make(null, null, null);

            fail("Should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass test
        }

        try {
            dataManager = JsonProcessor.make("", "", "");

            fail("Should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass test
        }

        try {
            dataManager = JsonProcessor.make("nosuchfile.json", "wrongfile.txt",
                    "nosuchfile.json");

            fail("Should have thrown IOException");
        } catch (IllegalStateException ex) {
            // pass test
        }
    }

    @Test
    public void testDataIntegrity_occupantTemplateFileLacksOccupantGroup() {

        // hazards missing
        occupantsJson = "{\n" +
                "	\"tools\":[],\n" +
                "	\"food\": [],\n" +
                "	\"faunae\": [],\n" +
                "	\"predators\": [],\n" +
                "	\"kiwis\": []\n" +
                "}";

        writeJsonToFiles(occupantsJson, OCCUPANTS_FILEPATH);

        try {
            dataManager = JsonProcessor.make(OCCUPANTS_FILEPATH, OCCUPANTSMAP_FILEPATH, OCCUPANTPOOL_FILEPATH);

            fail("Should have thrown Exception");
        } catch (IllegalStateException e) {
            // pass test
        }
    }

    @Test
    public void testDataIntegrity_occupantTemplateFileLacksFieldOfCertainOccupant() {

        // description missing
        occupantsJson = "{\n" +
                "	\"tools\":\n" +
                "	[\n" +
                "		{\n" +
                "			\"name\":\"Trap\",\n" +
                "           \"weight\":1.0,\n" +
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

        writeJsonToFiles(occupantsJson, OCCUPANTS_FILEPATH);

        try {
            dataManager = JsonProcessor.make(OCCUPANTS_FILEPATH, OCCUPANTSMAP_FILEPATH, OCCUPANTPOOL_FILEPATH);

            fail("Should have thrown Exception");
        } catch (IllegalStateException e) {
            // pass test
        }
    }

    @Test
    public void testDataIntegrity_occupantsMapFileLacksProperty() {

        // position missing
        occupantsJson = "[\n" +
                "	{\n" +
                "		\"occupants\":[\n" +
                "			\"Trap\"\n" +
                "		]\n" +
                "	}\n" +
                "]";

        writeJsonToFiles(occupantsJson, OCCUPANTSMAP_FILEPATH);

        try {
            dataManager = JsonProcessor.make(OCCUPANTS_FILEPATH, OCCUPANTSMAP_FILEPATH, OCCUPANTPOOL_FILEPATH);

            fail("Should have thrown Exception");
        } catch (IllegalStateException e) {
            // pass test
        }
    }

    @Test
    public void testDataIntegrity_occupantsPoolFileLacksOccupantGroup() {

        // faunae missing
        occupantsPoolJson = "{\n" +
                "  \"food\":{\n" +
                "  },\n" +
                "\n" +
                "  \"tools\":{\n" +
                "    \"Trap\":3\n" +
                "  },\n" +
                "\n" +
                "  \"kiwis\": {\n" +
                "  },\n" +
                "\n" +
                "  \"predators\":{\n" +
                "  },\n" +
                "\n" +
                "  \"hazards\":{\n" +
                "  }\n" +
                "}";
        writeJsonToFiles(occupantsPoolJson, OCCUPANTPOOL_FILEPATH);

        try {
            dataManager = JsonProcessor.make(OCCUPANTS_FILEPATH, OCCUPANTSMAP_FILEPATH, OCCUPANTPOOL_FILEPATH);

            fail("Should have thrown Exception");
        } catch (IllegalStateException e) {
            // pass test
        }
    }

    @Test
    public void testGetOccupantsInPosition() {
        Set<Occupant> occupants = dataManager.getOccupantsInPosition(new Position(island, 0, 4));

        Assert.assertEquals("There should only be 1 occupant", 1, occupants.size());

        for (Occupant o : occupants) {
            Assert.assertTrue("The type is incorrect.", o instanceof Tool);
            Tool tool = (Tool) o;
            Assert.assertEquals("The name is incorrect", "Trap", tool.getName());
            Assert.assertEquals("The description is incorrect", "A trap for predators", tool.getDescription());
            Assert.assertEquals("The weight shall be 1.0", 1.0, tool.getWeight());
            Assert.assertEquals("The size shall be 2.0", 2.0, tool.getSize());
            Assert.assertEquals("Row number should be 0", 0, tool.getPosition().getRow());
            Assert.assertEquals("Column number should be 4", 4, tool.getPosition().getColumn());
        }
    }

    @Test
    public void testGetOccupantsInEmptyPosition() {
        Set<Occupant> occupants = dataManager.getOccupantsInPosition(new Position(island, 2, 4));

        Assert.assertEquals("There should be no occupants", 0, occupants.size());
    }

    @Test
    public void testGetOccupantsInPositionWithIllegalArgument() {
        try {
            //pass in null as argument
            Set<Occupant> occupants = dataManager.getOccupantsInPosition(null);

            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException ex) {
            // pass test
        }
    }

    @Test
    public void testGetAllOccupantTemplates() {
        Set<Occupant> set = dataManager.getAllOccupantTemplates();
        assertEquals("The size of the set of all occupant templates should be 1.", 1,
                set.size());
        for (Occupant o : set) {
            assertTrue("Should be a Tool", o instanceof Tool);
            Tool tool = (Tool) o;
            assertTrue("Name should be Trap", tool.getName().equals("Trap"));
            assertEquals("Weight should be 1.0", 1.0, tool.getWeight());
            assertEquals("Size should be 2.0", 2.0, tool.getSize());
        }
    }

    @Test
    public void testGetAllOccupantTemplatesDeepCloneValid() {
        // changes applied to the occupant set should not
        // affect the original object in JsonProcessor
        dataManager.getAllOccupantTemplates().add(
                new Fauna(null, "Fish", "A test fish"));

        Set<Occupant> set = dataManager.getAllOccupantTemplates();
        assertEquals("The size of the set of all occupant templates should still "
                + "be 1.", 1, set.size());

        // changes applied to any occupant should not affect the original object
        // in JsonProcessor
        for (Occupant o : set) {
            assertTrue("Should be a Tool", o instanceof Tool);
            Tool tool = (Tool) o;
            tool.setBroken();
        }

        set = dataManager.getAllOccupantTemplates();
        for (Occupant o : set) {
            assertTrue("Should be a Tool", o instanceof Tool);
            Tool tool = (Tool) o;
            assertFalse("The trap should not be broken", tool.isBroken());
        }
    }

    @Test
    public void testGetAllOccupantInstances() {
        List<Occupant> all = dataManager.getAllOccupantInstances();
        assertEquals(3, all.size());
        for (Occupant o : all) {
            assertTrue("Should be a Tool", o instanceof Tool);
            Tool tool = (Tool) o;
            assertTrue("Name should be Trap", tool.getName().equals("Trap"));
            assertEquals("Weight should be 1.0", 1.0, tool.getWeight());
            assertEquals("Size should be 2.0", 2.0, tool.getSize());
        }
    }

    @Test
    public void testGetAllOccupantInstancesDeepCloneValid() {
        // changes applied to the occupant list should not
        // affect the original object in JsonProcessor
        dataManager.getAllOccupantInstances().add(
                new Fauna(null, "Fish", "A test fish"));

        List<Occupant> all = dataManager.getAllOccupantInstances();
        assertEquals("The number of all occupant instances should still "
                + "be 3.", 3, all.size());

        // changes applied to any occupant should not affect the original object
        // in JsonProcessor
        for (Occupant o : all) {
            assertTrue("Should be a Tool", o instanceof Tool);
            Tool tool = (Tool) o;
            tool.setBroken();
        }

        // get the data again, should not be changed
        all = dataManager.getAllOccupantInstances();
        for (Occupant o : all) {
            assertTrue("Should be a Tool", o instanceof Tool);
            Tool tool = (Tool) o;
            assertFalse("The trap should not be broken", tool.isBroken());
        }
    }

    private void writeJsonToFiles(String json, String path) {
        try (FileWriter fw = new FileWriter(path, false)) {
            fw.write(json);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
