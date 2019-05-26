
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import racing.common.data.Entity;
import racing.common.data.GameData;
import racing.common.data.GameImage;
import racing.common.data.TileType;
import racing.common.data.World;
import racing.common.data.entityparts.PositionPart;
import racing.common.data.entityparts.ScorePart;
import racing.common.npc.NPC;
import racing.LapSystem.LapControlSystem;
import racing.map.MapPlugin;

/**
 * Test class for LapControlSystem
 */
public class LapControlSystemTest {

    private World world;
    private GameData gameData;
    private Entity npc1;
    private Entity npc2;
    private MapPlugin map;
    private LapControlSystem lcs;
    private PositionPart p1;
    private PositionPart p2;

    public LapControlSystemTest() {
    }

    @BeforeClass
    public static void setUpClass() {

    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        world = new World();
        gameData = new GameData();
        npc1 = new NPC();
        npc2 = new NPC();

        GameImage i1 = new GameImage();
        GameImage i2 = new GameImage();
        npc1.setImage(i1);
        npc2.setImage(i2);

        p1 = new PositionPart(0, 0, 0);
        p2 = new PositionPart(0, 0, 0);
        npc1.add(p1);
        npc2.add(p2);

        map = new MapPlugin();
        lcs = new LapControlSystem();

        TileType[][] aMap = new TileType[][]{{TileType.START, TileType.FINISHLINE, TileType.ROAD}};

        map.start(gameData, world);
        map.createMap(aMap, gameData, world);
        lcs.setMapService(map);

    }

    @After
    public void tearDown() {
        world = null;
        gameData = null;
        lcs = null;
        npc1 = null;
        npc2 = null;
        map = null;
    }

    /**
     * Testing if IsThereAWinner returns true, when there is a winner
     */
    @Test
    public void test_IsThereAWinner_Succesful_WhenThereisaWinner() {
        //Arrange
        ScorePart scorePart1 = new ScorePart();
        ScorePart scorePart2 = new ScorePart();
        scorePart1.setLabs(2);
        scorePart2.setLabs(0);
        npc1.add(scorePart1);
        npc2.add(scorePart2);

        world.addEntity(npc1);
        world.addEntity(npc2);
        System.out.println(gameData);
        System.out.println(world);
        System.out.println(map);

        //Act
        lcs.process(gameData, world);

        p1.setPosition(70, 0);
        p2.setPosition(70, 0);
        lcs.process(gameData, world);

        p1.setPosition(140, 0);
        p2.setPosition(140, 0);
        lcs.process(gameData, world);

        p1.setPosition(0, 0);
        p2.setPosition(0, 0);
        lcs.process(gameData, world);

        p1.setPosition(70, 0);
        p2.setPosition(70, 0);
        lcs.process(gameData, world);

        p1.setPosition(140, 0);
        p2.setPosition(140, 0);
        lcs.process(gameData, world);
        boolean result = lcs.isThereAWinner();

        //Assert
        assertTrue(result);
    }

    /**
     * Testing if IsThereAWinner returns true, when there is no winner
     */
    @Test
    public void test_IsThereAWinner_Failing_WhenThereisNoWinner() {
        //Arrange
        ScorePart scorePart1 = new ScorePart();
        ScorePart scorePart2 = new ScorePart();
        scorePart1.setLabs(0);
        scorePart2.setLabs(0);
        npc1.add(scorePart1);
        npc2.add(scorePart2);
        world.addEntity(npc1);
        world.addEntity(npc2);

        //Act
        lcs.process(gameData, world);

        p1.setPosition(70, 0);
        p2.setPosition(70, 0);
        lcs.process(gameData, world);

        p1.setPosition(140, 0);
        p2.setPosition(140, 0);
        lcs.process(gameData, world);

        p1.setPosition(0, 0);
        p2.setPosition(0, 0);
        lcs.process(gameData, world);

        p1.setPosition(70, 0);
        p2.setPosition(70, 0);
        lcs.process(gameData, world);

        p1.setPosition(140, 0);
        p2.setPosition(140, 0);
        lcs.process(gameData, world);
        boolean result = lcs.isThereAWinner();

        //Assert
        assertFalse(result);

    }

    /**
     * Testing if reset() resets, when there is a winner
     */
    @Test
    public void test_Reset_Succeeding_WhenThereisAWinner() {
        //Arrange
        //Arrange
        ScorePart scorePart1 = new ScorePart();
        ScorePart scorePart2 = new ScorePart();
        scorePart1.setLabs(2);
        scorePart2.setLabs(0);
        npc1.add(scorePart1);
        npc2.add(scorePart2);

        world.addEntity(npc1);
        world.addEntity(npc2);
        System.out.println(gameData);
        System.out.println(world);
        System.out.println(map);

        //Act
        lcs.process(gameData, world);

        p1.setPosition(70, 0);
        p2.setPosition(70, 0);
        lcs.process(gameData, world);

        p1.setPosition(140, 0);
        p2.setPosition(140, 0);
        lcs.process(gameData, world);

        p1.setPosition(0, 0);
        p2.setPosition(0, 0);
        lcs.process(gameData, world);

        p1.setPosition(70, 0);
        p2.setPosition(70, 0);
        lcs.process(gameData, world);

        p1.setPosition(140, 0);
        p2.setPosition(140, 0);
        lcs.process(gameData, world);
        boolean result1 = lcs.isThereAWinner();
        lcs.reset();
        boolean result2 = lcs.isThereAWinner();

        //Assert
        assertTrue(result1);
        assertFalse(result2);

    }

    /**
     * Testing if reset() resets, when there is no winner
     */
    @Test
    public void test_Reset_Succeeding_WhenThereisnoWinner() {
        //Arrange
        ScorePart scorePart1 = new ScorePart();
        ScorePart scorePart2 = new ScorePart();
        scorePart1.setLabs(0);
        scorePart2.setLabs(0);
        npc1.add(scorePart1);
        npc2.add(scorePart2);

        world.addEntity(npc1);
        world.addEntity(npc2);
        System.out.println(gameData);
        System.out.println(world);
        System.out.println(map);

        //Act
        lcs.process(gameData, world);

        p1.setPosition(70, 0);
        p2.setPosition(70, 0);
        lcs.process(gameData, world);

        p1.setPosition(140, 0);
        p2.setPosition(140, 0);
        lcs.process(gameData, world);

        p1.setPosition(0, 0);
        p2.setPosition(0, 0);
        lcs.process(gameData, world);

        p1.setPosition(70, 0);
        p2.setPosition(70, 0);
        lcs.process(gameData, world);

        p1.setPosition(140, 0);
        p2.setPosition(140, 0);
        lcs.process(gameData, world);
        lcs.process(gameData, world);
        boolean result1 = lcs.isThereAWinner();
        lcs.reset();
        boolean result2 = lcs.isThereAWinner();

        //Assert
        assertFalse(result1);
        assertFalse(result2);

    }

    /**
     * Testing if getScores returns the scores
     */
    @Test
    public void test_getScores_ReturnsScores() {
        //Arrange
        ScorePart scorePart1 = new ScorePart();
        ScorePart scorePart2 = new ScorePart();
        scorePart1.setLabs(3);
        scorePart2.setLabs(2);
        npc1.add(scorePart1);
        npc2.add(scorePart2);
        world.addEntity(npc1);
        world.addEntity(npc2);

        //Act
        lcs.process(gameData, world);
        ArrayList<Entity> scoreList = (ArrayList<Entity>) lcs.getScores(world);
        ScorePart sp1 = scoreList.get(0).getPart(ScorePart.class);
        ScorePart sp2 = scoreList.get(1).getPart(ScorePart.class);

        //Assert
        assertEquals(sp1.getLabs(), 3);
        assertEquals(sp2.getLabs(), 2);

    }
}