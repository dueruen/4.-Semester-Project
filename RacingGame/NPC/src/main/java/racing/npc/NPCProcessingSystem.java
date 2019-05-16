package racing.npc;

import com.badlogic.gdx.math.Vector2;
import java.util.Random;
import racing.common.data.Entity;
import racing.common.data.GameData;
import racing.common.data.entityparts.PositionPart;
import racing.common.data.World;
import racing.common.data.entityparts.MovingPart;
import racing.common.services.IEntityProcessingService;
import racing.common.ai.AISPI;
import racing.common.npc.NPC;

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
            ai.setSourceAndTargetNodes(NPC, world);
            PositionPart pp = ai.findNextPosition();

//            double[] course = { pp.getX() - positionPart.getX(), pp.getY() - positionPart.getY() };
//            double[] heading = { course[0] + 1, course[0] };
//            
//            heading[0] = (heading[0] * Math.cos(Math.toDegrees(positionPart.getRadians()))) - (heading[1] * Math.sin(Math.toDegrees(positionPart.getRadians())));
//            heading[1] = (heading[0] * Math.sin(Math.toDegrees(positionPart.getRadians()))) - (heading[1] * Math.cos(Math.toDegrees(positionPart.getRadians())));
//            
//            double coursemagnitude = Math.sqrt(Math.pow(course[0], 2) + Math.pow(course[1], 2));
//            double headingmagnitude = Math.sqrt(Math.pow(heading[0] + 2, 2) + Math.pow(positionPart.getY(), 2));
//            
//            double angle = ((course[0] * heading[0]) + (course[1] * heading[1])) / (coursemagnitude * headingmagnitude);
//            
//            double acosAngle = Math.acos(angle);

           //TODO Calculate the angle to the target Vector and move on this.
//            Vector2 npc_pos = new Vector2(positionPart.getX(),positionPart.getY());
//            Vector2 target_pos = new Vector2(pp.getX(),pp.getY());
//           
//            
//            float diff_vect = npc_pos.dot(target_pos);
          
            
            double carAng = Math.toDegrees(positionPart.getRadians());

            double angle = Math.atan2((pp.getY() - positionPart.getY()),(pp.getX() - positionPart.getX()) )
                    *180.0d / Math.PI;
            
            
              System.out.println("Pointangle: " + angle);
              System.out.println("Carangle: " + carAng);
              
              if(carAng == angle) { 
                  movingPart.setUp(true);
              }
              if(carAng < angle) { 
                  movingPart.setLeft(true);
              }
              if(carAng > angle) { 
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
