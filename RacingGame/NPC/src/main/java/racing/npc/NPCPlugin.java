package racing.npc;

import racing.common.data.GameData;
import racing.common.data.Entity;
import racing.common.data.GameImage;
import racing.common.data.World;
import racing.common.data.entityparts.ItemPart;
import racing.common.data.entityparts.MovingPart;
import racing.common.data.entityparts.PositionPart;
import racing.common.data.entityparts.ScorePart;
import racing.common.npc.NPC;
import racing.common.npc.NPCSPI;
import racing.common.services.IGamePluginService;

/**
 * NPC Plugin class, handling adding and removing NPC Entitys from the game
 */
public class NPCPlugin implements IGamePluginService, NPCSPI {

    /**
     * Number for amount of npcs
     */
    private static int loadedNPCs;

    /**
     * Creates a number of NPCs and adds it to the world
     *
     * @param gameData
     * @param world
     */
    @Override
    public void start(GameData gameData, World world) {
        if (gameData.isGameRunning()) {
            create(gameData, world, loadedNPCs);
        }
    }

    /**
     * Removes all NPCs from the world
     *
     * @param gameData
     * @param world
     */
    @Override
    public void stop(GameData gameData, World world) {
        removeAll(gameData, world);
    }

    /**
     * Creates Entity of the type NPC
     *
     * @param gameData
     * @return created NPC instance
     */
    private Entity createNPC(GameData gameData, int colorVal) {
        float deacceleration = 10;
        float acceleration = 10;
        float maxSpeed = 400;
        float rotationSpeed = 5;
        float x = (gameData.getDisplayHeight() / 2);
        float y = (gameData.getDisplayWidth() / 2);
        float radians = 0.0f;

        Entity npcRacer = new NPC();
        npcRacer.setImage(new GameImage("cars/car" + colorVal + ".png", 100, 50));
        npcRacer.add(new MovingPart(deacceleration, acceleration, maxSpeed, rotationSpeed));
        npcRacer.add(new PositionPart(x, y, radians));
        npcRacer.add(new ScorePart());
        npcRacer.add(new ItemPart());

        return npcRacer;

    }

    /**
     * Creates all the NPC's in the game
     *
     * @param gameData
     * @param world
     * @param amount
     * @return array of NPC's
     */
    @Override
    public NPC[] create(GameData gameData, World world, int amount) {
        NPC[] npcs = new NPC[amount];
        int colorVal = 1;
        for (int i = 0; i < amount; i++) {
            Entity npc = createNPC(gameData, colorVal);
            colorVal++;
            if (colorVal == 6) {
                colorVal = 1;
            }
            world.addEntity(npc);
            npcs[i] = (NPC) npc;
        }
        loadedNPCs = amount;
        return npcs;
    }

    /**
     * Removes all NPCs in the game during unload
     *
     * @param gameData
     * @param world
     */
    @Override
    public void removeAll(GameData gameData, World world) {
        for (Entity npc : world.getEntities(NPC.class)) {
            world.removeEntity(npc);
        }
    }
}
