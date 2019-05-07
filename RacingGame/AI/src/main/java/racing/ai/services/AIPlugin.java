package racing.ai.services;

import java.util.ArrayList;
import java.util.List;
import racing.ai.astar.AStar;
import racing.ai.astar.AStarNode;
import racing.common.data.Entity;
import racing.common.data.GameData;
import racing.common.data.World;
import racing.common.data.entityparts.PositionPart;
import racing.common.map.MapSPI;
import racing.common.map.Tile;
import racing.common.services.IGamePluginService;
import racing.commonai.AISPI;

/**
 *
 * @author Victor Gram
 */
public class AIPlugin implements IGamePluginService, AISPI {
    
    /**
     * MapSPI
     */
    private MapSPI mapSPI;

    private AStar ai;
    /**
     * Declarative service set map service
     *
     * @param map map service
     */
    public void setMapService(MapSPI map) {
        this.mapSPI = map;
    }

    /**
     * Declarative service remove map service
     *
     * @param map map service
     */
    public void removeMapService(MapSPI map) {
        this.mapSPI = null;
    }

    @Override
    public void start(GameData gameData, World world) {
        initializeAI();
    }

    @Override
    public void stop(GameData gameData, World world) {

    }
    
    private void initializeAI() {
        Tile[][] map = mapSPI.getLoadedMap();
        int r = map.length;
        int c = map[0].length;
        
        AStarNode[][] nodes = new AStarNode[r][c];
        
        for(int i = 0; i < r; i++) { 
            for(int j = 0; j < c; j++) { 
                Tile pp = map[i][j];
                //throw new ArrayStoreException(pp.getX() + ", " + pp.getY());
                int[] coordinates = mapSPI.getTileXandY(pp);
                AStarNode node = new AStarNode(coordinates[0], coordinates[1]);
                nodes[i][j] = node;
            }
        }
        
        AStarNode initNode = new AStarNode((r/2), (c/2));
        AStarNode finalNode = new AStarNode(r, c);
        ai = new AStar(r, c, initNode, finalNode);
        ai.setSearchArea(nodes);
        
        
    }

    @Override
    public PositionPart findNextPosition() {
        PositionPart pp = ai.findNextPosition();
        Tile[][]map = mapSPI.getLoadedMap();
        int x = Math.round(pp.getX());
        int y = Math.round(pp.getY());
        Tile gt = map[x][y];
        return gt.getPart(PositionPart.class);
        
        //return ai.findNextPosition();
    }

//    @Override
//    public void setInitialPosition(Entity p, World world) {
//        
//        Tile t = mapSPI.getTileClosestToEntity(p, world);
//        int[] coordinates = mapSPI.getTileXandY(t);
//        int x = Math.round(coordinates[0]);
//        int y = Math.round(coordinates[1]);
//   
//        float radians = 3.1415f / 2;
//        
//   
//        ai.setInitialPosition(x,y);
//    }

    @Override
    public void setSourceAndTargetNodes(Entity p, World world) {
        Tile t = mapSPI.getTileClosestToEntity(p, world);
        int[] coordinates = mapSPI.getTileXandY(t);
        int x = Math.round(coordinates[0]);
        int y = Math.round(coordinates[1]);
   
        float radians = 3.1415f / 2;
        
        Tile[][] map = mapSPI.getLoadedMap();
        Tile gt = map[6][10]; //Starting point for eksemplets skyld, kan ændres
        int[] gtc = mapSPI.getTileXandY(gt);
                
        AStarNode source = new AStarNode(x,y);
        AStarNode target = new AStarNode(gtc[0], gtc[1]);
        
   
        ai.setSourceAndTargetNodes(source, target);
    }
}
