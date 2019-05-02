package racing.npc;

import java.util.Random;
import racing.common.data.Entity;
import racing.common.data.GameData;
import racing.common.data.entityparts.PositionPart;
import racing.common.data.World;
import racing.common.data.entityparts.MoveToPointPart;
import racing.common.services.IEntityProcessingService;
import racing.commonai.AISPI;
import racing.commonnpc.NPC;

/**
 *Processing system for NPC entity
 */
public class NPCProcessingSystem implements IEntityProcessingService {
    
    /**
     * AISPI
     */
    private AISPI ai;
    

    @Override
    public void process(GameData gameData, World world) {
          for (Entity enemy : world.getEntities(NPC.class)) {
            PositionPart positionPart = enemy.getPart(PositionPart.class);
            MoveToPointPart moveToPointPart = enemy.getPart(MoveToPointPart.class);
            ai.setSourceAndTargetNodes(enemy, world);
            PositionPart pp = ai.findNextPosition();

            moveToPointPart.setTargetPoint(pp);
            moveToPointPart.process(gameData, enemy);
            positionPart.process(gameData, enemy);

            updateShape(enemy);
        
    }
    }
    
     /**
     * Declarative service set AI service
     *
     * @param ai AI service
     */
    public void setAIService(AISPI ai) {
        this.ai = ai;

    }

    /**
     * Declarative service remove AI service
     *
     * @param ai AI service
     */
    public void removeAIService(AISPI ai) {
        this.ai = null;
    }
    
        /**
         * Updates position of the NPC
         * @param entity 
         */
        private void updateShape(Entity entity) {
        float[] shapex = new float[4];
        float[] shapey = new float[4];
        PositionPart positionPart = entity.getPart(PositionPart.class);
        //ai.setInitialPosition(positionPart);
        //ai.findNextPosition();
        
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();
    }
    
}
