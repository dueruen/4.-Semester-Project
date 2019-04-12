package racing.common.map;

import racing.common.data.Entity;
import racing.common.data.GameData;
import racing.common.data.World;
import racing.common.data.TileType;

public interface MapSPI {
    double getPositionWeight(Entity p, World world);
    void createMap(int[][] d, GameData gameData, World world);
    void createMap(TileType[][] d, GameData gameData, World world);
}