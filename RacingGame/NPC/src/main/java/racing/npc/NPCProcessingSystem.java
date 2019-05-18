package racing.npc;

import racing.common.data.Entity;
import racing.common.data.GameData;
import racing.common.data.entityparts.PositionPart;
import racing.common.data.World;
import racing.common.data.entityparts.MovingPart;
import racing.common.services.IEntityProcessingService;
import racing.common.ai.AISPI;
import racing.common.npc.NPC;

/**
 *
 * @author Victor Gram & Niclas Johansen
 */

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
            MovingPart movingPart = NPC.getPart(MovingPart.class);
            ai.setSourceNode(NPC, world);
            PositionPart pp = ai.findNextPosition();
            
            double carAng = Math.toDegrees(positionPart.getRadians());

            double angle = Math.atan2((pp.getY() - positionPart.getY()),(pp.getX() - positionPart.getX()) )
                    *180.0d / Math.PI;
            
              
              if(carAng > angle-1 && carAng< angle+1) { 
                  movingPart.setUp(true);
              }
              if(carAng < angle -1) { 
                  movingPart.setLeft(true);
        
              }
              if(carAng > angle + 1) { 
                  movingPart.setRight(true);
            
              }
              else {
                  movingPart.setUp(true);
              }
              

       
          
     
            movingPart.process(gameData, NPC);
            positionPart.process(gameData, NPC);   
            
            movingPart.setRight(false);
            movingPart.setLeft(false);
            movingPart.setUp(false);
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
