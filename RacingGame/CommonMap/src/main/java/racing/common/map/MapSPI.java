package racing.common.map;

import racing.common.data.GameData;
import racing.common.data.entityparts.PositionPart;

public interface MapSPI {
    double getPositionWeight(PositionPart p);
    void createMap(GameData gameData, int[][] d);
    void createMap(GameData gameData, TileType[][] d);
}