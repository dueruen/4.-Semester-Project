package racing.map;

import java.util.HashMap;
import java.util.Map;
import racing.common.data.Entity;
import racing.common.data.GameData;
import racing.common.data.World;
import racing.common.data.entityparts.PositionPart;
import racing.common.map.MapSPI;
import racing.common.map.TileType;
import racing.common.services.IEntityProcessingService;

public class MapSystem implements IEntityProcessingService, MapSPI {

    private Map<PositionPart, Tile> tiles = new HashMap<>();

    @Override
    public void process(GameData gameData, World world) {
        for (Tile t : tiles.values()) {
            t.setShape();
        }
    }

    @Override
    public double getPositionWeight(PositionPart p) {
        return tiles.get(p).type.getWeight();
    }

    @Override
    public void createMap(GameData gameData, int[][] d) {
        TileType[][] tmpTiles = new TileType[d.length][d[0].length];
        for (int r = 0; r < d.length; r++) {
            for (int c = 0; c < d[r].length; c++) {
                tmpTiles[r][c] = TileType.values()[d[r][c]];
            }
        }
        createMap(gameData, tmpTiles);
    }

    @Override
    public void createMap(GameData gameData, TileType[][] d) {
        double tileHeight = gameData.getDisplayHeight() / d.length;
        double tileWeight = gameData.getDisplayWidth() / d[0].length;

        for (int r = 0; r < d.length; r++) {
            for (int c = 0; c < d[r].length; c++) {
                Tile t = new Tile(d[r][c]);
                PositionPart p = new PositionPart(c * d.length, r * d[0].length, 0);
                t.add(p);
                t.setShape();
                tiles.put(p, t);
            }
        }
    }

    private class Tile extends Entity {

        public TileType type;

        public Tile(TileType type) {
            this.type = type;
        }

        public void setShape() {
            float[] shapex = getShapeX();
            float[] shapey = getShapeY();
            PositionPart positionPart = getPart(PositionPart.class);
            float x = positionPart.getX();
            float y = positionPart.getY();
            float radians = positionPart.getRadians();

            shapex[0] = x;
            shapey[0] = y;

            shapex[1] = (float) (x + Math.cos(radians - 4 * 3.1415f / 5));
            shapey[1] = (float) (y + Math.sin(radians - 4 * 3.1145f / 5));

            setShapeX(shapex);
            setShapeY(shapey);
        }
    }
}
