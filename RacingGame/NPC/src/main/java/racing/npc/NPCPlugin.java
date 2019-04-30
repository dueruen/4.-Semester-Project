package racing.npc;

import racing.common.data.GameData;
import racing.common.data.Entity;
import racing.common.data.GameImage;
import racing.common.data.World;
import racing.common.data.entityparts.MovingPart;
import racing.common.data.entityparts.PositionPart;
import racing.common.data.entityparts.ScorePart;
import racing.common.services.IGamePluginService;
import racing.commonnpc.NPC;
import racing.commonnpc.NPCSPI;

/**
 *NPC Plugin class, handling adding and rmeoving NPC Entitys from the game
 */
public class NPCPlugin implements IGamePluginService, NPCSPI {
    
    /**
     * ID identifying the specicfic NPC instance in the world
     */
    private String NPCID;
    
    
    /**
     * Creates a number of NPCs and adds it to the world 
     * @param gameData
     * @param world
     */
    @Override
    public void start(GameData gameData, World world) {
        int NPCCount = 5;
        int colorVal = 1;
        for (int i = 0; i < NPCCount; i++) {
        Entity npc = createNPC(gameData, colorVal);
        colorVal++;
        NPCID = world.addEntity(npc);
        }
        
    }

    /**
     * Removes all NPCs from the world
     * @param gameData 
     * @param world
     */
    @Override
    public void stop(GameData gameData, World world) {
        for (Entity npc : world.getEntities(NPC.class)) {
            world.removeEntity(npc);
        }
    }
    

    /**
     * Creates Entity of the type NPC
     * @param gameData
     * @return created NPC instance
     */
    private Entity createNPC (GameData gameData, int colorVal) { 
        float deacceleration = 10;
        float acceleration = 150;
        float maxSpeed = 200;
        float rotationSpeed = 5;
        float x = (gameData.getDisplayHeight()/2);
        float y = (gameData.getDisplayWidth()/2);
        float radians = 3.1415f / 2;

        float[] colour = new float[4];
        colour[0] = 1.0f;
        colour[1] = 0.0f;
        colour[2] = 0.0f;
        colour[3] = 1.0f;

        Entity enemyShip = new NPC();
        enemyShip.setImage(new GameImage("cars/car" +colorVal+ ".png", 100, 50));
        enemyShip.add(new MovingPart(deacceleration, acceleration, maxSpeed, rotationSpeed));
        enemyShip.add(new PositionPart(x, y, radians));
        enemyShip.add(new ScorePart());
        
        return enemyShip;
    }
}
