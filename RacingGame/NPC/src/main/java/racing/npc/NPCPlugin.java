/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package racing.npc;

import racing.common.data.GameData;
import racing.common.data.Entity;
import racing.common.data.World;
import racing.common.services.IGamePluginService;
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
     * Creates an NPC and adds it to the world 
     * @param gameData
     * @param world
     */
    @Override
    public void start(GameData gameData, World world) {
        Entity npc = createNPC(gameData);
        NPCID = world.addEntity(npc);
        
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
     * Creates Entity of the type NPC
     * @param gameData
     * @return created NPC instance
     */
    private Entity createNPC (GameData gameData) { 
        //TODO
        return null;
    }
    
}
