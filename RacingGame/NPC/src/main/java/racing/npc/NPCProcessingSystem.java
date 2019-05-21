package racing.npc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import racing.common.data.Entity;
import racing.common.data.GameData;
import racing.common.data.entityparts.PositionPart;
import racing.common.data.World;
import racing.common.data.entityparts.MovingPart;
import racing.common.services.IEntityProcessingService;
import racing.common.ai.AISPI;
import racing.common.data.TileType;
import racing.common.data.entityparts.ItemPart;
import racing.common.data.entityparts.TilePart;
import racing.common.map.MapSPI;
import racing.common.map.Tile;
import racing.common.npc.NPC;

/**
 * Processing system for NPC entity
 */
public class NPCProcessingSystem implements IEntityProcessingService {

    /**
     * AISPI, handling access to AI functions
     */
    private AISPI ai;

    /**
     * MapSPIinstance, handling acces sto map functionality
     */
    private MapSPI map;

    /**
     * Map containing the NPC and their current path
     */
    private Map<Entity, ArrayList<PositionPart>> pathMap = new HashMap<>();
    
    /**
     * Map containing the NPC, and theamount of checkpoint sthey've beeen 
     * over during their current lap
     */
    private Map<Entity, Integer> checkpointCount = new HashMap<>();

    @Override
    public void process(GameData gameData, World world) {
        for (Entity NPC : world.getEntities(NPC.class)) {
            PositionPart positionPart = NPC.getPart(PositionPart.class);
            MovingPart movingPart = NPC.getPart(MovingPart.class);
            ItemPart itemPart = NPC.getPart(ItemPart.class);

            
            if(!checkpointCount.containsKey(NPC)) { 
                checkpointCount.put(NPC, 0);
                
            }
            //If NPC is currently not present in pathMap
            if (!pathMap.containsKey(NPC)) {
                //Initialize AI
                ai.startAI();
                //Set new spurce node
                ai.setSourceNode(NPC, world, checkpointCount.get(NPC));
                //Add NPC along with path to pathMap
                pathMap.put(NPC, ai.getPath());

            }

            Tile t = map.getTile(NPC, world);
            PositionPart tp = t.getPart(PositionPart.class);
            TilePart atp = t.getPart(TilePart.class);

            ArrayList<PositionPart> path = pathMap.get(NPC);

            //If only position left in current path
            if (path.size() == 1) {
                //If currently on Tile of type CheckpointOne
                if (atp.getType() == TileType.CHECKPOINTONE) {
                    //Reset AI
                    ai.startAI();
                    //Set new source node and target, checkpoint counter = 1
                    ai.setSourceNode(NPC, world, 1);
                    checkpointCount.put(NPC, 1);
                    //Retrieve new paht to replace the old one
                    pathMap.put(NPC, ai.getPath());

                }
                //If currently on Tile of Type CheckpointTwo
                if (atp.getType() == TileType.CHECKPOINTTWO) {
                    //Reset AI
                    ai.startAI();
                    //Set new source node and target, checkpoint counter = 2
                    ai.setSourceNode(NPC, world, 2);
                    checkpointCount.put(NPC, 2);
                    pathMap.put(NPC, ai.getPath());

                }

                //If currently on Tile of type Finishline
                if (atp.getType() == TileType.FINISHLINE) {
                    //Reset AI
                    ai.startAI();
                    //Set new source node and target, checkpoint counter = 0
                    ai.setSourceNode(NPC, world, 0);
                    checkpointCount.put(NPC, 0);
                    //Retrieve new paht to replace the old one
                    pathMap.put(NPC, ai.getPath());

                }
                //Replace path with the new one in the Map
                path = pathMap.get(NPC);

            }

            //Get next position to travel towards
            PositionPart pp = path.get(0);
   

            double carAng = Math.abs(Math.toDegrees(positionPart.getRadians() * 1));

            double angle = getAngle(positionPart, pp);

            //If the NPC surpasses it's target, recalculate route based on current
            //position
            if (Math.abs((angle - carAng)) > 95) {
                PositionPart missedPP = pp;
                if (path.contains(missedPP)) {
                    ai.startAI();
                    ai.setSourceNode(NPC, world, checkpointCount.get(NPC));
                    pathMap.put(NPC, ai.getPath());
                    path = pathMap.get(NPC);
              
                    //Give movement system new data
                    pp = path.get(1);
                    angle = getAngle(positionPart, pp);

                }

            }

            if (carAng > angle - 4 && carAng < angle + 4) {
                movingPart.setUp(true);
            }
            if (carAng < angle - 4 || Math.abs(carAng - angle) > 330) {
                movingPart.setLeft(true);
            }
            if (carAng > angle + 4 && Math.abs(carAng - angle) < 330) {
                movingPart.setRight(true);
            }
            
            //If the NPC has an item, shoot
            if(itemPart.getChargesLeft() > 0) { 
                itemPart.setIsUsing(true);
            }

            //If NPC overlaps with target position
            if (isOverlapping(tp, positionPart)) {
                //If target position is still in path
                if (path.contains(tp)) {
                    //Remove from path
                    path.remove(tp);
                }
            }

            movingPart.process(gameData, NPC);
            positionPart.process(gameData, NPC);
            itemPart.process(gameData, NPC);

            
            movingPart.setRight(false);
            movingPart.setLeft(false);
            movingPart.setUp(false);

        }
    }

    /**
     * Calculates angle betweens points
     *
     * @param source
     * @param target
     * @return angle between two points
     */
    public double getAngle(PositionPart source, PositionPart target) {
        double angle = (double) Math.toDegrees(Math.atan2(target.getY() - source.getY(), target.getX() - source.getX()));

        if (angle < 0) {
            angle += 360;
        }

        return angle;
    }

    /**
     * Checks if there's an overlap between the NPC and the position
     *
     * @param tilePos
     * @param npcPos
     * @return boolean describing overlap or not
     */
    private boolean isOverlapping(PositionPart tilePos, PositionPart npcPos) {
        int tileSize = 200;
        int half = tileSize / 2;
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

    /**
     * Declarative service set map service
     *
     * @param map map service
     */
    public void setMapService(MapSPI map) {
        this.map = map;

    }

    /**
     * Declarative service remove map service
     *
     * @param map map service
     */
    public void removeMapService(MapSPI map) {
        this.map = null;
    }

}
