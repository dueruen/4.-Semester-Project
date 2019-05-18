package racing.npc;

import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import racing.common.data.Entity;
import racing.common.data.GameData;
import racing.common.data.entityparts.PositionPart;
import racing.common.data.World;
import racing.common.data.entityparts.MovingPart;
import racing.common.services.IEntityProcessingService;
import racing.common.ai.AISPI;
import racing.common.data.TileType;
import racing.common.data.entityparts.TilePart;
import racing.common.map.MapSPI;
import racing.common.map.Tile;
import racing.common.npc.NPC;

/**
 *Processing system for NPC entity
 */
public class NPCProcessingSystem implements IEntityProcessingService {
    
    /**
     * AISPI
     */
    private AISPI ai;
    
    private Map<Entity, ArrayList<PositionPart>> pathMap = new HashMap<>();
    
    private MapSPI map;
    

    @Override
    public void process(GameData gameData, World world) {
          for (Entity NPC : world.getEntities(NPC.class)) {
            PositionPart positionPart = NPC.getPart(PositionPart.class);
            MovingPart movingPart = NPC.getPart(MovingPart.class);
            
              
              if(!pathMap.containsKey(NPC)) { 
                  ai.setSourceNode(NPC, world, 0);
                  pathMap.put(NPC, ai.getPath());
                  
              }
              
              
            
            Tile t = map.getTile(NPC, world);
            PositionPart tp = t.getPart(PositionPart.class);
            TilePart atp = t.getPart(TilePart.class);
            
            
            ArrayList<PositionPart> path = pathMap.get(NPC);
              //System.out.println("Processing path: " + path.size());
            if(path.size() == 1) { 
                System.out.println(atp.getType());
                    if(atp.getType() == TileType.CHECKPOINTONE) {
                    ai.setSourceNode(NPC, world, 1);
                    pathMap.put(NPC, ai.getPath());
                    }
                    if(atp.getType() == TileType.CHECKPOINTTWO) {
                    ai.setSourceNode(NPC, world, 2);
                    pathMap.put(NPC, ai.getPath());
                        //System.out.println(ai.getPath().size());
                    }
                    
                    if(atp.getType() == TileType.FINISHLINE) {
                    ai.setSourceNode(NPC, world, 0);
                    pathMap.put(NPC, ai.getPath());
                    }
                    path = pathMap.get(NPC);
                    
                }

            PositionPart pp = path.get(0);
            
            double carAng = Math.toDegrees(positionPart.getRadians());

            double angle = Math.atan2((pp.getY() - positionPart.getY()),(pp.getX() - positionPart.getX()) )
                    *180.0d / Math.PI;
            
              
              if(carAng > angle-4 && carAng< angle+4) { 
                  movingPart.setUp(true);
              }
              if(carAng < angle -4) { 
                  movingPart.setLeft(true);
              }
              if(carAng > angle + 4) { 
                  movingPart.setRight(true);
              }
              else {
                  movingPart.setUp(true);
              }
              
            
            if(isOverlapping(tp, positionPart)) {
                
                if(path.contains(tp)){
                    path.remove(tp);
                }
            }
       
          
     
            movingPart.process(gameData, NPC);
            positionPart.process(gameData, NPC);   
            
            movingPart.setRight(false);
            movingPart.setLeft(false);
            movingPart.setUp(false);
            
            
        }
    }
    
    private boolean isOverlapping(PositionPart tilePos, PositionPart npcPos) {
        int tileSize = 200;
        int half = tileSize/2;
        if (tilePos.getX() - half > npcPos.getX() + 35 || tilePos.getX() + half < npcPos.getX()) {
            return false;
        }
        if (tilePos.getY() - half > npcPos.getY() + 35 || tilePos.getY() + half < npcPos.getY()) {
            return false;
        }
        return true;
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
    
    public void setMapService(MapSPI map) {
        this.map = map;

    }

    /**
     * Declarative service remove AI service
     *
     * @param ai AI service
     */
    public void removeMapService(MapSPI map) {
        this.map = null;
    }
    
}
