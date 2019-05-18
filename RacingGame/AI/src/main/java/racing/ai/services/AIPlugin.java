package racing.ai.services;

import java.util.ArrayList;
import racing.ai.astar.AStar;
import racing.ai.astar.AStarNode;
import racing.common.data.Entity;
import racing.common.data.GameData;
import racing.common.data.World;
import racing.common.data.entityparts.PositionPart;
import racing.common.map.MapSPI;
import racing.common.map.Tile;
import racing.common.services.IGamePluginService;
import racing.common.ai.AISPI;
import racing.common.data.TileType;
import racing.common.data.entityparts.TilePart;

/**
 *
 * @author Victor Gram & Niclas Johansen
 */
public class AIPlugin implements IGamePluginService, AISPI {

    /**
     * MapSPI
     */
    private MapSPI mapSPI;

    /**
     * Instance of AStar
     */
    private AStar ai;

    /**
     * Map currently being worked on
     */
    private Tile[][] map;

    /**
     * Two-dimensional array of AStarNodes in the map
     */
    private AStarNode[][] nodes;

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
    }

    @Override
    public void stop(GameData gameData, World world) {
    }

    /**
     * Method that activates the AI Created aside from start method, to maintain
     * control of when it's called
     */
    @Override
    public void startAI() {
        initializeAI();
    }

    /**
     * Initialises the AI by traversing map, and instantiating a two-dimensional
     * array of AStarNodes, that the AStar can work with
     */
    private void initializeAI() {
        map = mapSPI.getLoadedMap();
        int r = map.length;
        int c = map[0].length;

        nodes = new AStarNode[r][c];

        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                Tile pp = map[i][j];
                TilePart tp = pp.getPart(TilePart.class);
                int[] coordinates = mapSPI.getTileXandY(pp);
                AStarNode node = new AStarNode(coordinates[0], coordinates[1]);
                node.setW((int) tp.getType().getWeight());
                if (tp.getType().isIsStatic()) {
                    node.setBlock(true);
                }
                nodes[i][j] = node;
            }
        }

        AStarNode initNode = new AStarNode((r / 2), (c / 2));
        AStarNode finalNode = findNextTarget(0);
        ai = new AStar(r, c, initNode, finalNode);
        ai.setSearchArea(nodes);

    }

    /**
     * Sets the source node, and calculates the target
     *
     * @param p
     * @param world
     * @param checkpointCount
     */
    @Override
    public void setSourceNode(Entity p, World world, int checkpointCount) {
        Tile t = mapSPI.getTile(p, world);
        int[] coordinates = mapSPI.getTileXandY(t);
        int x = Math.round(coordinates[0]);
        int y = Math.round(coordinates[1]);

        AStarNode source = new AStarNode(x, y);
        ai.setSourceAndTargetNode(source, findNextTarget(checkpointCount));
    }

    /**
     * Calculates what the next target is gonna be, based on how many
     * chackpoints are checked
     *
     * @param type
     * @return Next target node
     */
    private AStarNode findNextTarget(int type) {
        TileType tileType = null;
        switch (type) {
            case 0:
                tileType = TileType.CHECKPOINTONE;
                break;
            case 1:
                tileType = TileType.CHECKPOINTTWO;
                break;
            case 2:
                tileType = TileType.FINISHLINE;
                break;
            default:
                break;
        }
        AStarNode target = null;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                TilePart tp = map[i][j].getPart(TilePart.class);
                TileType entityTileType = tp.getType();

                if (entityTileType == tileType) {
                    int[] newGoalTile = mapSPI.getTileXandY(map[i][j]);
                    target = new AStarNode(newGoalTile[0], newGoalTile[1]);
                    return target;
                }
            }
        }
        return target;
    }

    /**
     * Returns the path from source to target, as list of PositionParts
     *
     * @return list of positions - the path
     */
    @Override
    public ArrayList<PositionPart> getPath() {
        //Gets path from AStar
        ArrayList<AStarNode> nodePath = (ArrayList<AStarNode>) ai.findPath();
        //List to be populated and returned
        ArrayList<PositionPart> path = new ArrayList<>();
        for (AStarNode node : nodePath) {
            Tile[][] map = mapSPI.getLoadedMap();
            float x = node.getRow();
            float y = node.getCol();
            PositionPart pp = new PositionPart(x, y, (3.1415f / 2));

            int xp = Math.round(pp.getX());
            int xy = Math.round(pp.getY());
            Tile gt = map[xp][xy];
            path.add(gt.getPart(PositionPart.class));
        }
        return path;
    }
}
