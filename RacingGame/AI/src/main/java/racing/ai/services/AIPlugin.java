package racing.ai.services;

import java.util.ArrayList;
import java.util.Random;
import racing.ai.astar.AStar;
import racing.ai.astar.AStarNode;
import racing.common.data.Entity;
import racing.common.data.World;
import racing.common.data.entityparts.PositionPart;
import racing.common.map.MapSPI;
import racing.common.map.Tile;
import racing.common.ai.AISPI;
import racing.common.data.TileType;
import racing.common.data.entityparts.TilePart;

/**
 * Plugin handling the addition and removal of the plugin from the system
 */
public class AIPlugin implements AISPI {

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
     * List of nodes of the Checkpoint One type
     */
    private ArrayList<AStarNode> checkOneList = new ArrayList<>();

    /**
     * List of nodes of Checkpoint Two type
     */
    private ArrayList<AStarNode> checkTwoList = new ArrayList<>();
    /**
     * List of nodes of FInishline type
     */
    private ArrayList<AStarNode> goalList = new ArrayList<>();

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

    /**
     * Method that creates the AI system, by clearing 
     * datastructures that need to be cleared, and by 
     * initializing the AI data 
     */
    @Override
    public void startAI() {
        checkOneList.clear();
        checkTwoList.clear();
        goalList.clear();
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
                Tile t = map[i][j];
                TilePart tp = t.getPart(TilePart.class);
                int[] coordinates = mapSPI.getTileXandY(t);
                AStarNode node = new AStarNode(coordinates[0], coordinates[1]);
                node.setW((int) tp.getType().getWeight());
                if (tp.getType().isIsStatic()) {
                    node.setBlock(true);
                }
                if (tp.getType() == TileType.CHECKPOINTONE) {
                    checkOneList.add(node);
                }
                if (tp.getType() == TileType.CHECKPOINTTWO) {
                    checkTwoList.add(node);
                }
                if (tp.getType() == TileType.FINISHLINE) {
                    goalList.add(node);
                }
                nodes[i][j] = node;
            }
        }

        AStarNode initNode = goalList.get(0);
        AStarNode finalNode = checkOneList.get(0);
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
        int x = coordinates[0];
        int y = coordinates[1];

        AStarNode source = new AStarNode(x, y);
        ai.setSourceAndTargetNode(source, findNextTarget(checkpointCount));
    }

    /**
     * Calculates what the next target is gonna be, based on how many
     * checkpoints are checked, and one a random variable, for more natural
     * movement
     *
     * @param type
     * @return Next target node
     */
    private AStarNode findNextTarget(int type) {
        ArrayList<AStarNode> targetList = null;
        switch (type) {
            case 0:
                targetList = checkOneList;
                break;
            case 1:
                targetList = checkTwoList;
                break;
            case 2:
                targetList = goalList;
                break;
            default:
                break;
        }

        Random r = new Random();
        int targetVal = r.nextInt(targetList.size());
        AStarNode target = targetList.get(targetVal);
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
            int x = node.getRow();
            int y = node.getCol();

            Tile gt = map[x][y];
            path.add(gt.getPart(PositionPart.class));
        }
        return path;
    }
}
