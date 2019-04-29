/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package racing.npc;

import java.util.Random;
import racing.common.data.GameData;
import racing.common.data.Entity;
import racing.common.data.GameImage;
import racing.common.data.World;
import racing.common.data.entityparts.AStarPart;
import racing.common.data.entityparts.MovingPart;
import racing.common.data.entityparts.PositionPart;
import racing.common.map.MapSPI;
import racing.common.services.IGamePluginService;
import racing.commonnpc.NPC;
import racing.commonnpc.NPCSPI;

/**
 *
 * @author Victor Gram
 */
public class NPCPlugin implements IGamePluginService, NPCSPI {
    /**
     * ID identifying the specicfic NPC instance in the world
     */
    private String NPCID;
    
    /**
     * MapSPI
     */
    private MapSPI map;
    
    /**
     * Creates a number of NPCs and adds it to the world 
     * @param gameData
     * @param world
     */
    @Override
    public void start(GameData gameData, World world) {
        int NPCCount = 5;
        for (int i = 0; i < NPCCount; i++) {
        Entity npc = createNPC(gameData);
        NPCID = world.addEntity(npc);
        }
        
    }

    /**
     * Removes specific NPC from the world, using NPCID
     * @param gameData 
     * @param world
     */
    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(NPCID);
    }
    


    /**
     * Declarative service set map service
     *
     * @param map map service
     */
    public void setMapService(MapSPI map) {
        this.map = map;
    }

    /**
     * Declarative service remove map service
     *
     * @param map map service
     */
    public void removeMapService(MapSPI map) {
        this.map = null;
    }

    /**
     * Creates Entity of the type NPC
     * @param gameData
     * @return created NPC instance
     */
    private Entity createNPC (GameData gameData) { 
        float deacceleration = 10;
        float acceleration = 150;
        float maxSpeed = 200;
        float rotationSpeed = 5;
        float x = new Random().nextFloat() * gameData.getDisplayWidth();
        float y = new Random().nextFloat() * gameData.getDisplayHeight();
        float radians = 3.1415f / 2;

        float[] colour = new float[4];
        colour[0] = 1.0f;
        colour[1] = 0.0f;
        colour[2] = 0.0f;
        colour[3] = 1.0f;

        Entity enemyShip = new NPC();
        enemyShip.setImage(new GameImage("testresource/testnpc.png", 100, 100));
        enemyShip.add(new MovingPart(deacceleration, acceleration, maxSpeed, rotationSpeed));
        enemyShip.add(new PositionPart(x, y, radians));
        
        //for(Tile t: map.)
        
        //enemyShip.add(new AStarPart());
 
        return enemyShip;
    }
    
}
