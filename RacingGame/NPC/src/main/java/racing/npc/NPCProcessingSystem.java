package racing.npc;

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
            float x = pp.getX();
            float y = pp.getY();
            
            //TODO Find the next position so we can finish this Vector calculations
            double[] course = { pp.getX() - positionPart.getX(), pp.getY() - positionPart.getY() };
            double[] heading = { course[0] + 1, course[0] };
            
            heading[0] = (heading[0] * Math.cos(Math.toDegrees(positionPart.getRadians()))) - (heading[1] * Math.sin(Math.toDegrees(positionPart.getRadians())));
            heading[1] = (heading[0] * Math.sin(Math.toDegrees(positionPart.getRadians()))) - (heading[1] * Math.cos(Math.toDegrees(positionPart.getRadians())));
            
            double coursemagnitude = Math.sqrt(Math.pow(course[0], 2) + Math.pow(course[1], 2));
            double headingmagnitude = Math.sqrt(Math.pow(heading[0] + 2, 2) + Math.pow(positionPart.getY(), 2));
            
            double angle = ((course[0] * heading[0]) + (course[1] * heading[1])) / (coursemagnitude * headingmagnitude);
            
            double acosAngle = Math.acos(angle);
            
            System.out.println(positionPart.getX() + " positonpart " + positionPart.getY());
            
            positionPart.setX(x);
            positionPart.setY(y);
            
            movingPart.process(gameData, NPC);
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
