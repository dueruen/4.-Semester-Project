package racing.map;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import racing.common.data.Entity;
import racing.common.data.GameData;
import racing.common.data.GameImage;
import racing.common.data.World;
import racing.common.data.entityparts.PositionPart;
import racing.common.data.entityparts.TilePart;
import racing.common.map.MapSPI;
import racing.common.map.Tile;
import racing.common.data.TileType;
import racing.common.services.IGamePluginService;

/**
 * Plugin used to control the map
 *
 */
public class MapPlugin implements IGamePluginService, MapSPI {

    /**
     * Tile in current map
     */
    private Tile[][] map;
    
    /**
     * Current map name
     */
    private static String currentMapName;

    @Override
    public void start(GameData gameData, World world) {
        if(gameData.isGameRunning()) {
            loadFromFile(currentMapName, gameData, world);
        }
    }

    @Override
    public void stop(GameData gameData, World world) {
        removeAll(world);
    }

 

      /**
     * Finds the tile weight for the tile closed to the entity
     *
     * @param p the entity
     * @param world the world
     * @return the weight of the closed tile
     */
    @Override
    public double getPositionWeight(Entity p, World world) {
        Entity closedTile = getTile(p, world);
        TilePart tp = closedTile.getPart(TilePart.class);
        return tp.getType().getWeight();
    }

    @Override
    public int[] getTileXandY(Tile t) {
        int[] result = new int[2];

        int r = map.length;
        int c = map[0].length;

        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (map[i][j] == t) {
                    result[0] = i;
                    result[1] = j;
                }
            }
        }
        return result;
    }

    
       /**
     * Finds the tile the entity is on
     *
     * @param p
     * @param world
     * @return the tile the entity is on
     */
    @Override
    public Tile getTile(Entity p, World world) {
        Entity closedTile = null;
        double shortestDistance = Double.MAX_VALUE;
        for (Entity tile : world.getEntities(Tile.class)) {
            if (closedTile == null) {
                closedTile = tile;
                shortestDistance = calculateDistance(p, tile);
                continue;
            }

            double nextDistance = calculateDistance(p, tile);
            if (nextDistance < shortestDistance) {
                closedTile = tile;
                shortestDistance = nextDistance;
            }
        }
        return (Tile) closedTile;
    }


    /**
     * Calculates the distance between to entities based center of the image
     *
     * @param t1 entity one
     * @param t2 entity two
     * @return the distance between the centers of the two entities
     */
    private double calculateDistance(Entity t1, Entity t2) {
        PositionPart p1 = t1.getPart(PositionPart.class);
        PositionPart p2 = t2.getPart(PositionPart.class);

        GameImage i1 = t1.getImage();
        GameImage i2 = t2.getImage();

        double x = Math.pow((p2.getX() + (i2.getWidth() / 2)) - (p1.getX() + (i1.getWidth() / 2)), 2);
        double y = Math.pow((p2.getY() + (i2.getHeight() / 2)) - (p1.getY() + (i1.getHeight() / 2)), 2);
        return Math.sqrt(x + y);
    }

    /**
     * Creates a new map based on a comma separated file
     *
     * @param fileName the file name
     * @param gameData the game data
     * @param world the world data
     */
    @Override
    public void loadFromFile(String fileName, GameData gameData, World world) {
        try {
            InputStream is;
            if (fileName.equals("DefaultMap.txt")) {
                is = getClass().getClassLoader().getResourceAsStream("/maps/" + fileName);
            } else {
                String path = System.getProperty("user.home") + "/racing_game/maps/";
                String file = path + fileName;

                is = new FileInputStream(file);
            }
            ArrayList<int[]> data = new ArrayList<>();
            try (Scanner sc = new Scanner(is)) {
                while (sc.hasNextLine()) {
                    String nextLine = sc.nextLine();
                    String[] values = nextLine.split(",");
                    int[] row = new int[values.length];
                    for (int i = 0; i < values.length; i++) {
                        row[i] = Integer.parseInt(values[i]);
                    }
                    data.add(row);
                }
                int[][] map = new int[data.size()][data.get(0).length];
                for (int i = 0; i < data.size(); i++) {
                    map[i] = data.get(i);
                }
                createMap(map, gameData, world);
                currentMapName = fileName;
            }
            is.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MapPlugin.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MapPlugin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Creates a new map based on the input arguments
     *
     * @param d a two dimensional array with TileTypes
     * @param gameData the GameData
     * @param world the World
     */
    @Override
    public void createMap(TileType[][] d, GameData gameData, World world) {
        float tileHeight = 70;
        float tileWeight = 70;

        map = new Tile[d.length][d[0].length];

        int rOffSet = d.length - 1;
        for (int r = 0; r < d.length; r++) {
            for (int c = 0; c < d[r].length; c++) {
                Tile t = new Tile();
                PositionPart p = new PositionPart(c * tileWeight, (r + rOffSet) * tileHeight, 0);
                t.add(p);
                t.add(new TilePart(d[r][c]));
                t.setImage(new GameImage(d[r][c].getImagePath(), tileWeight, tileHeight));
                world.addEntity(t);
                map[r][c] = t;
            }
            rOffSet -= 2;
        }
    }

    /**
     * Creates a new map based on the input arguments
     *
     * @param d a two dimensional array with integers representing the TileType
     * @param gameData the GameData
     * @param world the World
     */
    private void createMap(int[][] d, GameData gameData, World world) {
        TileType[][] tmpTiles = new TileType[d.length][d[0].length];
        for (int r = 0; r < d.length; r++) {
            for (int c = 0; c < d[r].length; c++) {
                tmpTiles[r][c] = TileType.values()[d[r][c]];
            }
        }
        createMap(tmpTiles, gameData, world);
    }

    @Override
    public Tile[][] getLoadedMap() {
        return map;
    }

    @Override
    public void saveMapToFile(int[][] data, String mapName) {
        String path = System.getProperty("user.home") + "/racing_game/maps/";
        String fileName = path + mapName + ".txt";

        new File(path).mkdirs();

        try (FileOutputStream out = new FileOutputStream(fileName)) {
            for (int r = 0; r < data.length; r++) {
                for (int c = 0; c < data[0].length; c++) {
                    if (c == data[0].length - 1) {
                        String s = "" + data[r][c];
                        out.write(s.getBytes());
                    } else {
                        String s = data[r][c] + ",";
                        out.write(s.getBytes());
                    }
                }
                out.write("\n".getBytes());
            }
        } catch (IOException ex) {
            Logger.getLogger(MapPlugin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String[] getMapNames() {
        File folder = new File(System.getProperty("user.home") + "/racing_game/maps/");
        File[] listOfFiles = folder.listFiles();
        
        String[] s = new String[listOfFiles.length + 1];
        s[0] = "DefaultMap.txt";
        int i = 1;
        for (File f : listOfFiles) {
            s[i] = f.getName();
            i++;
        }
        return s;
    }

    @Override
    public void removeAll(World world) {
        for (Entity tile : world.getEntities(Tile.class)) {
            world.removeEntity(tile);
        }
    }
}
