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
          for (Entity NPC : world.getEntities(NPC.class)) {
            PositionPart positionPart = NPC.getPart(PositionPart.class);
            MoveToPointPart moveToPointPart = NPC.getPart(MoveToPointPart.class);
            ai.setSourceAndTargetNodes(NPC, world);
            PositionPart pp = ai.findNextPosition();

            moveToPointPart.setTargetPoint(pp);
            moveToPointPart.process(gameData, NPC);
            positionPart.process(gameData, NPC);
        
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
    
}
