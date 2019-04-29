package racing.npc;

import java.util.Random;
import racing.common.data.Entity;
import racing.common.data.GameData;
import racing.common.data.entityparts.PositionPart;
import racing.common.data.World;
import racing.common.data.entityparts.MovingPart;
import racing.common.services.IEntityProcessingService;
import racing.commonnpc.NPC;

/**
 *Processing system for NPC entity
 */
public class NPCProcessingSystem implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
          for (Entity enemy : world.getEntities(NPC.class)) {
            PositionPart positionPart = enemy.getPart(PositionPart.class);
            MovingPart movingPart = enemy.getPart(MovingPart.class);

            Random rand = new Random();

            float rng = rand.nextFloat();

            if (rng > 0.1f && rng < 0.9f) {
                movingPart.setUp(true);
            }

            if (rng < 0.2f) {
                movingPart.setLeft(true);
            }

            if (rng > 0.8f) {
                movingPart.setRight(true);
            }

            movingPart.process(gameData, enemy);
            positionPart.process(gameData, enemy);

            updateShape(enemy);

            movingPart.setRight(false);
            movingPart.setLeft(false);
            movingPart.setUp(false);
        
    }
    }
    
        /**
         * Updates position of the NPC
         * @param entity 
         */
        private void updateShape(Entity entity) {
        float[] shapex = new float[4];
        float[] shapey = new float[4];
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();
    }
    
}
