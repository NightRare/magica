package nz.ac.aut.ense701.gameModel;

import nz.ac.aut.ense701.gui.GameNotification;
import org.junit.Test;

import java.util.HashMap;

/**
 * The test class GameTest.
 *
 * @author  AS
 * @version S2 2011
 */
public class GameTest extends junit.framework.TestCase
{
    Game       game;
    Player     player;
    Position   playerPosition;
    Island island ;
    
    /**
     * Default constructor for test class GameTest
     */
    public GameTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Override
    protected void setUp()
    {
        // Create a new game from the data file.
        // Player is in position 2,0 & has 100 units of stamina
        game           = new Game();
        playerPosition = game.getPlayer().getPosition();
        player         = game.getPlayer();
        island = game.getIsland();
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @Override
    protected void tearDown()
    {
        game = null;
        player = null;
        playerPosition = null;
    }

    /*********************************************************************************************************
     * Game under test
      
---------------------------------------------------------------------------------
|    |    |@   | F  | T  |    |    | PK |    |    | T  |    |    |    |    |    |
|~~~~|~~~~|....|....|....|~~~~|^^^^|^^^^|^^^^|^^^^|^^^^|~~~~|~~~~|....|....|....|
---------------------------------------------------------------------------------
|    |    |    |    | H  |    |    |    |    |    |    |    |    |    |    |    |
|~~~~|####|^^^^|^^^^|^^^^|^^^^|^^^^|^^^^|^^^^|^^^^|^^^^|####|####|####|~~~~|~~~~|
---------------------------------------------------------------------------------
|    |    | H  |    | E  |    | P  |    | K  |    |    |    |    | F  |    |    |
|####|####|####|####|^^^^|^^^^|^^^^|^^^^|^^^^|~~~~|^^^^|^^^^|^^^^|^^^^|^^^^|^^^^|
---------------------------------------------------------------------------------
| T  |    |    |    | P  | H  |    |    |    |    |    |    |    |    | P  |    |
|....|####|####|####|****|****|^^^^|....|~~~~|~~~~|....|####|####|####|####|****|
---------------------------------------------------------------------------------
| F  | P  |    |    |    |    | F  |    |    |    |    |    | F  |    |    |    |
|....|^^^^|^^^^|^^^^|****|****|^^^^|....|~~~~|~~~~|****|****|****|****|****|****|
---------------------------------------------------------------------------------
| H  |    | P  | T  | E  |    |    |    |    |    |    |    | P  |    |    |    |
|....|****|****|****|****|****|####|####|####|~~~~|~~~~|####|####|####|####|####|
---------------------------------------------------------------------------------
|    |    | K  |    | P  | H  | K  | E  | F  |    |    |    |    |    | P  |    |
|....|****|****|****|****|****|****|####|####|####|^^^^|^^^^|^^^^|^^^^|^^^^|^^^^|
---------------------------------------------------------------------------------
| K  |    |    | F  | H  |    | H  | K  | T  |    |    |    | K  |    |    |    |
|****|****|****|****|****|~~~~|****|****|~~~~|~~~~|^^^^|^^^^|^^^^|^^^^|^^^^|^^^^|
---------------------------------------------------------------------------------
|    |    | E  | K  |    |    |    |    | F  |    |    |    | E  |    |    | E  |
|....|....|****|****|~~~~|~~~~|~~~~|****|****|....|****|****|****|****|****|****|
---------------------------------------------------------------------------------
|    |    |    | K  | K  |    | K  | P  |    |    |    |    |    |    | K  | E  |
|~~~~|....|****|****|****|~~~~|****|****|****|....|####|####|****|****|****|****|
---------------------------------------------------------------------------------
| K  |    |    | K  |    |    | P  | P  |    |    |    |    | F  | F  |    | E  |
|....|....|****|****|~~~~|~~~~|~~~~|****|****|....|****|****|****|****|****|****|
---------------------------------------------------------------------------------
| E  |    |    | E  |    | E  | H  |    |    |    |    |    | H  | H  | K  | K  |
|....|****|****|****|****|****|####|####|####|~~~~|~~~~|####|####|####|####|####|
---------------------------------------------------------------------------------
| P  |    |    | K  |    |    | K  |    | E  |    | E  |    | F  |    | K  | F  |
|....|^^^^|^^^^|^^^^|****|****|^^^^|....|~~~~|~~~~|****|****|****|****|****|****|
---------------------------------------------------------------------------------
| E  | E  | E  |    |    | H  |    |    | H  |    |    |    | F  | K  |    | F  |
|~~~~|####|^^^^|^^^^|^^^^|^^^^|^^^^|^^^^|^^^^|^^^^|^^^^|####|####|####|~~~~|~~~~|
---------------------------------------------------------------------------------
| H  |    | E  | E  |    |    | E  |    | K  |    |    |    |    | K  | F  | F  |
|~~~~|####|^^^^|^^^^|^^^^|^^^^|^^^^|^^^^|^^^^|^^^^|^^^^|####|####|####|~~~~|~~~~|
---------------------------------------------------------------------------------
| F  |    | E  |    |    |    | E  |    | K  |    | K  |    | H  |    |    | H  |
|****|****|****|****|****|~~~~|****|****|~~~~|~~~~|^^^^|^^^^|^^^^|^^^^|^^^^|^^^^|
---------------------------------------------------------------------------------
 *********************************************************************************************************/
    /**
     * Tests for Accessor methods of Game, excluding those which are wrappers for accessors in other classes.
     * Other class accessors are tested in their test classes.
     */
    
    @Test
    public void testGetNumRows(){
        assertEquals("Check row number", game.getNumRows(), 16);
    }
    
    @Test
    public void testGetNumColumns(){
        assertEquals("Check column number", game.getNumRows(), 16);
    }
    
    @Test
    public void testGetPlayer(){
        String name = player.getName();
        String checkName = "River Song";
        assertTrue("Check player name", name.equals(checkName) );
    } 

    @Test
    public void testGetInitialState(){
        assertEquals("Wrong initial state", game.getState(), GameState.PLAYING);
    }
    
    @Test
    public void testGetPlayerValues(){
        int[] values = game.getPlayerValues();
        assertEquals("Check Max backpack size.", values[Game.MAXSIZE_INDEX], 5);    
        assertEquals("Check max stamina.", values[Game.MAXSTAMINA_INDEX], 100);
        assertEquals("Check max backpack weight.", values[Game.MAXWEIGHT_INDEX], 10);
        assertEquals("Check initialstamina", values[Game.STAMINA_INDEX], 100);
        assertEquals("Check initial backpack weight.", values[Game.WEIGHT_INDEX], 0);
        assertEquals("Check initial backp[ack size.", values[Game.SIZE_INDEX], 0);
    }
    
    @Test
    public void testIsPlayerMovePossibleValidMove(){
        //At start of game player has valid moves EAST, West & South
        assertTrue("Move should be valid", game.isPlayerMovePossible(MoveDirection.SOUTH));
    }
    
    @Test
    public void testIsPlayerMovePossibleInvalidMove(){
        //At start of game player has valid moves EAST, West & South
        assertFalse("Move should not be valid", game.isPlayerMovePossible(MoveDirection.NORTH));
    }
    
    @Test
    public void testCanCollectCollectable(){
        //Items that are collectable and fit in backpack
        Item valid = new Food(playerPosition,"Sandwich", "Yummy", "", 1.0, 1.0,1.0, new HashMap<>());
        assertTrue("Should be able to collect", game.canCollect(valid));
    }
    
    @Test    
    public void testCanCollectNotCollectable(){
        //Items with size of '0' cannot be carried
        Item notCollectable = new Food(playerPosition,"Sandwich", "Very Heavy Sandwich", "", 10.0, 0.0,1.0, new HashMap<>());
        assertFalse("Should not be able to collect", game.canCollect(notCollectable));
    }
    
    @Test
    public void testCanUseFoodValid(){
        //Food can always be used
        Item valid = new Food(playerPosition,"Sandwich", "Yummy", "", 1.0, 1.0,1.0, new HashMap<>());
        assertTrue("Should be able to use", game.canUse(valid));
    }
    
    @Test
    public void testCanUseTrapOnPredatorValid(){
        //Trap can be used only if there is a non-Kiwi fauna here
        Item valid = new Tool(playerPosition,"Trap", "A predator trap", "", 1.0, 1.0, new HashMap<>());
        //Add predator
        Predator rat = new Predator(playerPosition,"Rat", "A norway rat", "", "", new HashMap<>());
        island.addOccupant(playerPosition, rat);
        assertTrue("Should be able to use", game.canUse(valid));
    }
    
    @Test
    public void testCanUseTrapOnNonKiwiFaunaValid(){
        //Trap can be used only if there is a non-Kiwi fauna here
        Item valid = new Tool(playerPosition,"Trap", "A predator trap", "", 1.0, 1.0, new HashMap<>());
        //Add non-Kiwi Fauna
        Fauna fish = new Fauna(playerPosition, "Fish", "Just a fish", "", "", new HashMap<>());
        island.addOccupant(playerPosition, fish);
        assertTrue("Should be able to use", game.canUse(valid));
    }
    
    @Test
    public void testCanUseTrapOnNonFauna(){
        //Trap can be used only if there is a non-Kiwi fauna here
        Item tool = new Tool(playerPosition,"Trap", "A predator trap", "",1.0, 1.0, new HashMap<>());

        assertFalse("Should not be able to use", game.canUse(tool));
    }
    
    @Test
    public void testCanUseTrapOnKiwi() {
        //Trap can be used only if there is a non-Kiwi fauna here
        Item tool = new Tool(playerPosition,"Trap", "A predator trap", "",1.0, 1.0, new HashMap<>());
        Kiwi kiwi = new Kiwi(playerPosition, "Kiwi", "A test kiwi.", "", "", new HashMap<>());
        island.addOccupant(playerPosition, kiwi);
        assertFalse("Should not be able to use", game.canUse(tool));
    }
    
    @Test
    public void testCanUseTool(){
        //Screwdriver can be used if player has a broken trap
        Item tool = new Tool(playerPosition,"Screwdriver", "A good tool to fix a trap", "",1.0, 1.0, new HashMap<>());
        Tool trap = new Tool(playerPosition,"Trap", "A predator trap", "",1.0, 1.0, new HashMap<>());
        trap.setBroken();
        player.collect(trap);

        assertTrue("Should be able to use", game.canUse(tool));
    }
    
    @Test
    public void testCanUseToolNoTrap(){
        //Screwdriver can be used if player has a broken trap
        Item tool = new Tool(playerPosition,"Screwdriver", "A good tool to fix a trap", "",1.0, 1.0, new HashMap<>());
        Tool trap = new Tool(playerPosition,"Trap", "A predator trap", "",1.0, 1.0, new HashMap<>());
        trap.setBroken();

        assertFalse("Should not be able to use", game.canUse(tool));
    }
    
    @Test
    public void testCanUseToolTrapNotBroken(){
        //Screwdriver can be used if player has a broken trap
        Item tool = new Tool(playerPosition,"Screwdriver", "A good tool to fix a trap", "",1.0, 1.0, new HashMap<>());
        Tool trap = new Tool(playerPosition,"Trap", "A predator trap",1.0, 1.0);
        player.collect(trap);

        assertFalse("Should not be able to use", game.canUse(tool));
    }
    
     @Test 
    public void testCountKiwi() 
    { 
        //Need to move to a place where there is a kiwi 
        assertTrue (" This move valid", playerMoveEast(5)); 
        //Test has been modified to set the turn to 12 so that the kiwi will be counted
        //...this test broke when adding kiwi only at night functionality
        game.setToFirstNightForTestingPurposes();
        game.countKiwi(); 
        assertEquals("Wrong count", game.getKiwiCount(), 1); 
    }
    
    @Test 
    public void testCountKiwiDuringDay() 
    { 
        //Need to move to a place where there is a kiwi 
        assertTrue (" This move valid", playerMoveEast(5));
        game.countKiwi(); 
        //There should be no kiwi counted because they do not appear during the day
        assertEquals("Wrong count", game.getKiwiCount(), 0); 
    }
    
    @Test
    public void testGetKiwiCountInitial()
    {
       assertEquals("Shouldn't have counted any kiwis yet",game.getKiwiCount(),0); 
    }
    /**
     * Test for mutator methods
     */
    
    @Test
    public void testCollectValid(){
        Item food = new Food(playerPosition,"Sandwich", "Yummy",1.0, 1.0,1.0);
        island.addOccupant(playerPosition, food);
        assertTrue("Food now on island", island.hasOccupant(playerPosition, food));
        game.collectItem(food);
        
        assertTrue("Player should have food",player.hasItem(food));
        assertFalse("Food should no longer be on island", island.hasOccupant(playerPosition, food));
    }
    
    @Test
    public void testCollectNotCollectable(){
        Item notCollectable = new Food(playerPosition,"House", "Can't collect",1.0, 0.0,1.0);
        island.addOccupant(playerPosition, notCollectable);
        assertTrue("House now on island", island.hasOccupant(playerPosition, notCollectable));
        game.collectItem(notCollectable);
        
        assertFalse("Player should not have house",player.hasItem(notCollectable));
        assertTrue("House should be on island", island.hasOccupant(playerPosition, notCollectable));
    }
    
    @Test    
    public void testDropValid(){
        Item food = new Food(playerPosition,"Sandwich", "Yummy",1.0, 1.0,1.0);
        island.addOccupant(playerPosition, food);
        game.collectItem(food);
        assertTrue("Player should have food",player.hasItem(food));
        
        game.dropItem(food);
        assertFalse("Player should no longer have food",player.hasItem(food));
        assertTrue("Food should be on island", island.hasOccupant(playerPosition, food));
    }
    
    @Test
    public void testDropNotValidPositionFull(){
        Item food = new Food(playerPosition,"Sandwich", "Yummy",1.0, 1.0,1.0);
        island.addOccupant(playerPosition, food);
        game.collectItem(food);
        assertTrue("Player should have food",player.hasItem(food));
        
        //Positions can have at most three occupants
        Item dummy = new Tool(playerPosition,"Trap", "An extra occupant", 1.0, 1.0);
        Item dummy2 = new Tool(playerPosition,"Trap", "An extra occupant", 1.0, 1.0);
        Item dummy3 = new Tool(playerPosition,"Trap", "An extra occupant", 1.0, 1.0);
        island.addOccupant(playerPosition, dummy);
        island.addOccupant(playerPosition, dummy2);
        island.addOccupant(playerPosition, dummy3);
        
        game.dropItem(food);
        assertTrue("Player should have food",player.hasItem(food));
        assertFalse("Food should not be on island", island.hasOccupant(playerPosition, food));
    }
    
    @Test
    public void testUseItemFoodCausesIncrease(){
        Item food = new Food(playerPosition,"Sandwich", "Yummy",1.0, 1.0,1.3);
        player.collect(food);
        assertTrue("Player should have food",player.hasItem(food));
        
        // Will only get a stamina increase if player has less than max stamina
        player.reduceStamina(5.0);
        game.useItem(food);
        assertFalse("Player should no longer have food",player.hasItem(food));
        assertEquals("Wrong stamina level", player.getStaminaLevel(), 96.3);
    }
 
    public void testUseItemFoodNoIncrease(){
        Item food = new Food(playerPosition,"Sandwich", "Yummy",1.0, 1.0,1.3);
        player.collect(food);
        assertTrue("Player should have food",player.hasItem(food));
        
        // Will only get a stamina increase if player has less than max stamina
        game.useItem(food);
        assertFalse("Player should no longer have food",player.hasItem(food));
        assertEquals("Wrong stamina level", player.getStaminaLevel(), 100.0);
    }  
    
    @Test
    public void testUseItemTrapOnPredator(){
        Item trap = new Tool(playerPosition,"Trap", "Rat trap",1.0, 1.0);
        player.collect(trap);
        assertTrue("Player should have trap",player.hasItem(trap));
        
        // Can only use trap if there is a predator.
        Predator predator = new Predator(playerPosition,"Rat", "Norway rat");
        island.addOccupant(playerPosition, predator);
        game.useItem(trap);
        assertTrue("Player should still have trap",player.hasItem(trap));
        assertFalse("Predator should be gone.", island.hasPredator(playerPosition));
    }
    
    @Test
    public void testUseItemTrapOnNonKiwiFauna() {
        Item trap = new Tool(playerPosition,"Trap", "Rat trap",1.0, 1.0);
        player.collect(trap);
        assertTrue("Player should have trap",player.hasItem(trap));
        // setup the stamina value for testing
        player.increaseStamina(10.0);
        double staminaLevel = player.getStaminaLevel();
        
        // Can only use trap if there is a predator.
        Fauna fish = new Fauna(playerPosition,"Fish", "A test fish");
        island.addOccupant(playerPosition, fish);
        game.useItem(trap);
        assertTrue("Player should still have trap", player.hasItem(trap));
        assertFalse("Fauna should be gone.", island.hasNonKiwiFauna(playerPosition));
        assertTrue("Stamina should be reduced 10.0 for punishing.", 
                player.getStaminaLevel() == staminaLevel - 10.0);
    }
    
    @Test
    public void testUseItemTrapOnKiwi() {
        Item trap = new Tool(playerPosition,"Trap", "Rat trap",1.0, 1.0);
        player.collect(trap);
        assertTrue("Player should have trap",player.hasItem(trap));
        
        // Can only use trap if there is a predator.
        Kiwi kiwi = new Kiwi(playerPosition,"Kiwi", "A test kiwi");
        island.addOccupant(playerPosition, kiwi);
        
        assertFalse("Should have failed to use trap.", game.useItem(trap));
        assertTrue("Player should still have trap", player.hasItem(trap));
        Occupant[] occupants = island.getOccupants(playerPosition);
        if(occupants.length == 0) {
            fail("Kiwi should still be there.");
        } else {
            assertTrue("Kiwi should still be there.", occupants[0].equals(kiwi));
        }
    }
    
    @Test
    public void testUseItemTrapOnNonFauna() {
        Item trap = new Tool(playerPosition,"Trap", "Rat trap",1.0, 1.0);
        player.collect(trap);
        assertTrue("Player should have trap",player.hasItem(trap));
        
        // Can only use trap if there is a predator.
        assertFalse("Should have failed to use trap.", game.useItem(trap));
        assertTrue("Player should still have trap", player.hasItem(trap));
    }
    
    
    @Test
    public void testUseItemBrokenTrap(){
        Tool trap = new Tool(playerPosition,"Trap", "Rat trap",1.0, 1.0);
        player.collect(trap);
        assertTrue("Player should have trap",player.hasItem(trap));
        
        // Can only use trap if there is a predator.
        Predator predator = new Predator(playerPosition,"Rat", "Norway rat");
        island.addOccupant(playerPosition, predator);
        trap.setBroken();
        game.useItem(trap);
        assertTrue("Player should still have trap",player.hasItem(trap));
        assertTrue("Predator should still be there as trap broken.", island.hasPredator(playerPosition));
    }
    
    @Test
    public void testUseItemToolFixTrap(){
        Tool trap = new Tool(playerPosition,"Trap", "Rat trap",1.0, 1.0);
        trap.setBroken();
        player.collect(trap);
        assertTrue("Player should have trap",player.hasItem(trap));
        Tool screwdriver = new Tool(playerPosition,"Screwdriver", "Useful screwdriver",1.0, 1.0);
        player.collect(screwdriver);
        assertTrue("Player should have screwdriver",player.hasItem(screwdriver));
        
        game.useItem(screwdriver);
        assertFalse("Trap should be fixed", trap.isBroken());
    }
   
    @Test
    public void testPlayerMoveToInvalidPosition(){
        //A move NORTH would be invalid from player's start position
        assertFalse("Move not valid", game.playerMove(MoveDirection.NORTH));
    }
 
    @Test
    public void testPlayerMoveValidNoHazards(){
        double stamina = player.getStaminaLevel();  

        assertTrue("Move valid", game.playerMove(MoveDirection.SOUTH));
        //Stamina reduced by move
        assertEquals("Wrong stamina", stamina - 3, player.getStaminaLevel());
        Position newPos = game.getPlayer().getPosition();
        assertEquals("Wrong position", newPos.getRow(), 1);
        assertFalse("Player should not be here", game.hasPlayer(playerPosition.getRow(), playerPosition.getColumn()));
    }
    
    @Test
    public void testPlayerMoveFatalHazard(){ 
        Position hazardPosition = new Position(island, playerPosition.getRow()+1, playerPosition.getColumn());
        Hazard fatal = new Hazard(hazardPosition, "Cliff", "Steep cliff", 1.0);
        island.addOccupant(hazardPosition, fatal);
        
        assertTrue("Move valid", game.playerMove(MoveDirection.SOUTH));
        //Fatal Hazard should kill player
        assertTrue("Player should be dead.", !player.isAlive());
        assertTrue("Game should be over", game.getState()== GameState.LOST);
    }
    
    @Test
    public void testPlayerMoveDeadPlayer(){
        player.kill();
        assertFalse(game.playerMove(MoveDirection.SOUTH));
    }
    
    @Test
    public void testPlayerMoveNonFatalHazardNotDead(){
        double stamina = player.getStaminaLevel(); 
        Position hazardPosition = new Position(island, playerPosition.getRow()+1, playerPosition.getColumn());
        Hazard fatal = new Hazard(hazardPosition, "Cliff", "Not so steep cliff", 0.5);
        island.addOccupant(hazardPosition, fatal);
        
        assertTrue("Move valid", game.playerMove(MoveDirection.SOUTH));
        //Non-fatal Hazard should reduce player stamina
        assertTrue("Player should be alive.", player.isAlive());
        assertTrue("Game should not be over", game.getState()== GameState.PLAYING);
        assertEquals("Wrong stamina", (stamina-53), player.getStaminaLevel());
    }
    
    @Test
    public void testPlayerMoveNonFatalHazardDead(){
        Position hazardPosition = new Position(island, playerPosition.getRow()+1, playerPosition.getColumn());
        Hazard fatal = new Hazard(hazardPosition, "Cliff", "Not so steep cliff", 0.5);
        island.addOccupant(hazardPosition, fatal);
        player.reduceStamina(47.0);
        
        assertTrue("Move valid", game.playerMove(MoveDirection.SOUTH));
        //Non-fatal Hazard should reduce player stamina to less than zero
        assertFalse("Player should not be alive.", player.isAlive());
        assertTrue("Game should be over", game.getState()== GameState.LOST);
        assertEquals("Wrong stamina", 0.0, player.getStaminaLevel());
    }
    
    @Test
    public void testPlayerMoveNotEnoughStamina(){
        // Reduce player's stamina to less than is needed for the most challenging move
        //Most challenging move is WEST as Terrain is water
        player.reduceStamina(98.0);
        assertFalse("Player should not have required stamina", game.playerMove(MoveDirection.WEST));
        //Game not over as there other moves player has enough stamina for
        assertTrue("Game should not be over", game.getState()== GameState.PLAYING);
    }
    
    
    @Test
    public void testPredatorViaForestWetlandTrap()
    {
        //Valid for Forest terrain predators
        assertTrue (" This trap for the rat predator is valid", game.predatorViaForestWetlandTrap("FOREST","Forest & Wetland Trap"));
        //Valid for Wetland terrain predators
        assertTrue (" This trap for the kiore predator is valid", game.predatorViaForestWetlandTrap("WETLAND","Forest & Wetland Trap"));
        
        assertFalse (" This trap for sand terrain is invalid", game.predatorViaForestWetlandTrap("SAND","Forest & Wetland Trap"));
        assertFalse (" This trap for water terrain is invalid", game.predatorViaForestWetlandTrap("WATER","Forest & Wetland Trap"));
        assertFalse (" This trap for scrub terrain is invalid", game.predatorViaForestWetlandTrap("SCRUB","Forest & Wetland Trap"));
    }
    
    @Test
    public void testPredatorViaWaterScrubTrap()
    {
        //Valid for water terrain predators
        assertTrue (" This trap for water terrain is valid", game.predatorViaWaterScrubTrap("WATER","Water & Scrub Trap"));
        //Valid for scrub terrain predators
        assertTrue (" This trap for scrub terrain is valid", game.predatorViaWaterScrubTrap("SCRUB","Water & Scrub Trap"));
        
        assertFalse (" This trap for wetland terrain is invalid", game.predatorViaWaterScrubTrap("WETLAND","Water & Scrub Trap"));
        assertFalse (" This trap for sand terrain is invalid", game.predatorViaWaterScrubTrap("SAND","Water & Scrub Trap"));
        assertFalse (" This trap for forest terrain is invalid", game.predatorViaWaterScrubTrap("FOREST","Water & Scrub Trap"));
    }
    
    @Test
    public void testPredatorViaA24LandTrap()
    {
        //Invalid for water terrain predator
        assertFalse (" This trap for the Rat predator is invalid", game.predatorViaA24LandTrap("WATER","A24 Land Trap"));

        assertTrue (" This trap for scrub terrain is valid", game.predatorViaA24LandTrap("SCRUB","A24 Land Trap"));
        assertTrue (" This trap for forest terrain is valid", game.predatorViaA24LandTrap("FOREST","A24 Land Trap"));
        assertTrue (" This trap for wetland terrain is valid", game.predatorViaA24LandTrap("WETLAND","A24 Land Trap"));
        assertTrue (" This trap for sand terrain is valid", game.predatorViaA24LandTrap("SAND","A24 Land Trap"));
    }
    
    @Test
    public void testPredatorViaGeneralTrap()
    {
        //Valid for All predators as long as they have the Trap
        assertTrue (" This trap is valid for the all predators", game.predatorViaGeneralTrap("Trap"));
    } 

/**
 * Private helper methods
 */
    

    
    private boolean playerMoveEast(int numberOfMoves)
    {
        boolean success = false;
        for (int i = 0; i < numberOfMoves; i++) {
            success = game.playerMove(MoveDirection.EAST);
            if(!success)break;
            
        }
        return success;
    }
    
}
