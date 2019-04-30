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
        initializeAI(gameData);
    }

    @Override
    public void stop(GameData gameData, World world) {

    }
    
    private void initializeAI(GameData gameData) {
        Tile[][] map = mapSPI.getLoadedMap();
        int r = map.length;
        int c = map[0].length;
        
        AStarNode[][] nodes = new AStarNode[r][c];
        
        for(int i = 0; i < r; i++) { 
            for(int j = 0; j < c; j++) { 
                PositionPart pp = map[i][j].getPart(PositionPart.class);
                AStarNode node = new AStarNode(Math.round(pp.getX()), Math.round(pp.getY()));
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
        return ai.findNextPosition();
    }

    @Override
    public void setInitialPosition(Entity p, World world) {
        
        Tile t = mapSPI.getTileClosestToEntity(p, world);
        int[] coordinates = mapSPI.getTileXandY(t);
        float x = coordinates[0];
        float y = coordinates[1];
   
        float radians = 3.1415f / 2;
        
   
        
        PositionPart pp = new PositionPart(x, y, radians);
        ai.setInitialPosition(pp);
    }
}
