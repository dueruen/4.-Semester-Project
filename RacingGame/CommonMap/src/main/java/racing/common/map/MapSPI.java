package racing.common.map;

import racing.common.data.Entity;
import racing.common.data.GameData;
import racing.common.data.World;
import racing.common.data.TileType;

/**
 * The SPI for a map
 * 
 */
public interface MapSPI {
    Tile getTile(Entity p, World world);
    double getPositionWeight(Entity p, World world);
    void loadFromFile(String filePath, GameData gameData, World world);
    void createMap(TileType[][] d, GameData gameData, World world);
    Tile[][] getLoadedMap();
}