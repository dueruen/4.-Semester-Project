package racing.npc;

import racing.common.data.GameData;
import racing.common.data.Entity;
import racing.common.data.GameImage;
import racing.common.data.World;
import racing.common.data.entityparts.MovingPart;
import racing.common.data.entityparts.PositionPart;
import racing.common.data.entityparts.ScorePart;
import racing.common.npc.NPC;
import racing.common.npc.NPCSPI;
import racing.common.services.IGamePluginService;

/**
 * NPC Plugin class, handling adding and rmeoving NPC Entitys from the game
 */
public class NPCPlugin implements IGamePluginService, NPCSPI {

    /**
     * Number for amount of npcs
     */
    private static int loadedNPC;

    /**
     * Creates a number of NPCs and adds it to the world
     * @param gameData
     * @param world
     */
    @Override
    public void start(GameData gameData, World world) {
        if (gameData.isGameRunning()) {
            create(gameData, world, loadedNPC);
        }
    }

    /**
     * Removes all NPCs from the world
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
    private Entity createNPC (GameData gameData, int colorVal) {
        float deacceleration = 10;
        float acceleration = 150;
        float maxSpeed = 200;
        float rotationSpeed = 5;
        float x = (gameData.getDisplayHeight() / 2);
        float y = (gameData.getDisplayWidth() / 2);
        float radians = 0.0f;

        float[] colour = new float[4];
        colour[0] = 1.0f;
        colour[1] = 0.0f;
        colour[2] = 0.0f;
        colour[3] = 1.0f;

        Entity enemyShip = new NPC();
        enemyShip.setImage(new GameImage("cars/car" + colorVal + ".png", 100, 50));
        enemyShip.add(new MovingPart(deacceleration, acceleration, maxSpeed, rotationSpeed));
        enemyShip.add(new PositionPart(x, y, radians));
        enemyShip.add(new ScorePart());

        return enemyShip;
    }

    @Override
    public NPC[] create(GameData gameData, World world, int amount) {
        NPC[] npcs = new NPC[amount];
        int colorVal = 1;
        for (int i = 0; i < amount; i++) {
            Entity npc = createNPC(gameData, colorVal);
            colorVal++;
            if(colorVal == 6) {
                colorVal = 1;
            }
            world.addEntity(npc);
            npcs[i] = (NPC)npc;
        }
        loadedNPC = amount;
        return npcs;
    }

    @Override
    public void removeAll(GameData gameData, World world) {
        for (Entity npc : world.getEntities(NPC.class)) {
            world.removeEntity(npc);
        }
    }
}
