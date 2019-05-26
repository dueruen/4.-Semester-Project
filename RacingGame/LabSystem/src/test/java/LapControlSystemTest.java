//
//
//import java.util.Map;
//import org.junit.After;
//import org.junit.AfterClass;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import static org.junit.Assert.*;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.runners.MockitoJUnitRunner;
//import racing.common.data.Entity;
//import racing.common.data.GameData;
//import racing.common.data.World;
//import racing.common.data.entityparts.ScorePart;
//import racing.common.map.MapSPI;
//import racing.common.npc.NPC;
//import racing.labsystem.LapControllSystem;
//import racing.map.MapPlugin;
//
///**
// * Test class for LapControlSystem
// */
////@RunWith(MockitoJUnitRunner.class)
//public class LapControlSystemTest {
//    private World world;
//    private GameData gameData;
//    private Entity npc1;
//    private Entity npc2;
//    private MapPlugin map;
//    private LapControllSystem lcs;
//    
//    public LapControlSystemTest() {
//    }
//    
//    @BeforeClass
//    public static void setUpClass() {
//        
//    }
//    
//    @AfterClass
//    public static void tearDownClass() {
//    }
//    
//    @Before
//    public void setUp() {
//        world = new World();
//        gameData = new GameData();
//        npc1 = new NPC();   
//        npc2 = new NPC();
//        map = new MapPlugin();
//        lcs = new LapControllSystem();
//        
//    }
//    
//    @After
//    public void tearDown() {
//        world = null;
//        gameData = null;
//    }
//
//    
//    @Test
//    public void test_IsThereAWinner_Succesful_WhenThereisaWinner() { 
//        //Arrange
//        ScorePart scorePart1 = new ScorePart();
//        ScorePart scorePart2 = new ScorePart();
//        scorePart1.setLabs(3);
//        scorePart2.setLabs(0);
//        npc1.add(scorePart1);
//        npc2.add(scorePart2);
//        world.addEntity(npc1);
//        world.addEntity(npc2);
//        System.out.println(gameData);
//        System.out.println(world);
//        System.out.println(map);
//        map.start(gameData, world);
//        map.loadFromFile("DefaultMap.txt", gameData, world);
//        lcs.setMapService(map);
//        
//        
//        //Act
//        lcs.process(gameData, world);
//        boolean result = lcs.isThereAWinner();
//        
//        //Assert
//        assertTrue(result);
//    }
//    
//    //@Test
//    public void test_IsThereAWinner_Failing_WhenThereisNoWinner() { 
//       //Arrange
//        ScorePart scorePart1 = new ScorePart();
//        ScorePart scorePart2 = new ScorePart();
//        scorePart1.setLabs(0);
//        scorePart2.setLabs(0);
//        npc1.add(scorePart1);
//        npc2.add(scorePart2);
//        world.addEntity(npc1);
//        world.addEntity(npc2);
//        
//        
//        //Act
//        lcs.process(gameData, world);
//        boolean result = lcs.isThereAWinner();
//        
//        //Assert
//        assertFalse(result);
//        
//    }
//    
//    //@Test
//    public void test_Reset_Succeeding_WhenThereisAWinner() { 
//       //Arrange
//        ScorePart scorePart1 = new ScorePart();
//        ScorePart scorePart2 = new ScorePart();
//        scorePart1.setLabs(3);
//        scorePart2.setLabs(0);
//        npc1.add(scorePart1);
//        npc2.add(scorePart2);
//        //mapMock.loadFromFile("DefaultMap.txt", gameData, world);
//        world.addEntity(npc1);
//        world.addEntity(npc2);
//        
//        
//        //Act
//        lcs.process(gameData, world);
//        boolean result1 = lcs.isThereAWinner();
//        lcs.reset();
//        boolean result2 = lcs.isThereAWinner();
//        
//        //Assert
//        assertTrue(result1);
//        assertFalse(result2);
//        
//    }
//    
//    //@Test
//    public void test_Reset_Succeeding_WhenThereisnoWinner() { 
//       //Arrange
//        Entity npc1 = new NPC();   
//        Entity npc2 = new NPC();
//        ScorePart scorePart1 = new ScorePart();
//        ScorePart scorePart2 = new ScorePart();
//        scorePart1.setLabs(2);
//        scorePart2.setLabs(0);
//        npc1.add(scorePart1);
//        npc2.add(scorePart2);
//        //mapMock.loadFromFile("DefaultMap.txt", gameData, world);
//        world.addEntity(npc1);
//        world.addEntity(npc2);
//        
//        
//        //Act
//        lcs.process(gameData, world);
//        boolean result1 = lcs.isThereAWinner();
//        lcs.reset();
//        boolean result2 = lcs.isThereAWinner();
//        
//        //Assert
//        assertFalse(result1);
//        assertFalse(result2);
//        
//    }
//}
