package racing.common.map;

import racing.common.data.entityparts.PositionPart;

public interface MapSPI {
    double getPositionWeight(PositionPart p);
    void createMap(int[][] d);
    void createMap(TileType[][] d);
}