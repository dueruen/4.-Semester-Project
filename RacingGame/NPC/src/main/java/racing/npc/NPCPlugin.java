package racing.npc;

import racing.common.data.GameData;
import racing.common.data.Entity;
import racing.common.data.GameImage;
import racing.common.data.World;
import racing.common.data.entityparts.MoveToPointPart;
import racing.common.data.entityparts.PositionPart;
import racing.common.services.IGamePluginService;
import racing.commonnpc.NPC;
import racing.commonnpc.NPCSPI;

/**
 *NPC Plugin class, handling adding and rmeoving NPC Entitys from the game
 */
public class NPCPlugin implements IGamePluginService, NPCSPI {
       
    /**
     * Creates a number of NPCs and adds it to the world 
     * @param gameData
     * @param world
     */
    @Override
    public void start(GameData gameData, World world) {
        int NPCCount = 1;
        int colorVal = 1;
        for (int i = 0; i < NPCCount; i++) {
        Entity npc = createNPC(gameData, colorVal);
        colorVal++;
        world.addEntity(npc);
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


        Entity NPCRacer = new NPC();
        NPCRacer.setImage(new GameImage("cars/car" +colorVal+ ".png", 100, 50));
        NPCRacer.add(new MoveToPointPart(deacceleration, acceleration, maxSpeed, rotationSpeed));
        NPCRacer.add(new PositionPart(x, y, radians));
        
        return NPCRacer;
    }
    
}
