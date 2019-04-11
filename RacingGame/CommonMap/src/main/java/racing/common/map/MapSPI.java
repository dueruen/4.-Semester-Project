package racing.common.map;

import racing.common.data.GameData;
import racing.common.data.World;
import racing.common.data.entityparts.PositionPart;
import racing.common.data.TileType;

public interface MapSPI {
    double getPositionWeight(PositionPart p, World world);
    void createMap(int[][] d, GameData gameData, World world);
    void createMap(TileType[][] d, GameData gameData, World world);
}