/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package racing.map;

import racing.common.data.Entity;
import racing.common.data.GameData;
import racing.common.data.World;
import racing.common.data.entityparts.PositionPart;
import racing.common.data.entityparts.TilePart;
import racing.common.map.MapSPI;
import racing.common.map.Tile;
import racing.common.data.TileType;
import racing.common.services.IGamePluginService;

/**
 *
 * @author yodamaster42
 */
public class MapPlugin implements IGamePluginService, MapSPI {

    @Override
    public void start(GameData gameData, World world) {
        //world.addEntity(new Map());
    }

    @Override
    public void stop(GameData gameData, World world) {
        for (Entity tile : world.getEntities(Tile.class)) {
            world.removeEntity(tile);
        }
    }

    @Override
    public double getPositionWeight(PositionPart p, World world) {
        Entity closedTile = null;
        double shortestDistance = Double.MAX_VALUE;
        for (Entity tile : world.getEntities(Tile.class)) {
            if (closedTile == null) {
                closedTile = tile;
                shortestDistance = p.distance(tile.getPart(PositionPart.class));
                continue;
            }

            PositionPart positionNext = tile.getPart(PositionPart.class);
            double nextDistance = p.distance(positionNext);
            if (nextDistance < shortestDistance) {
                closedTile = tile;
                shortestDistance = nextDistance;
            }
        }
        TilePart tp = closedTile.getPart(TilePart.class);
        return tp.getType().getWeight();
    }

    @Override
    public void createMap(int[][] d, GameData gameData, World world) {
        TileType[][] tmpTiles = new TileType[d.length][d[0].length];
        for (int r = 0; r < d.length; r++) {
            for (int c = 0; c < d[r].length; c++) {
                tmpTiles[r][c] = TileType.values()[d[r][c]];
            }
        }
        createMap(tmpTiles, gameData, world);
    }

    @Override
    public void createMap(TileType[][] d, GameData gameData, World world) {
        float tileHeight = gameData.getDisplayHeight() / d.length;
        float tileWeight = gameData.getDisplayWidth() / d[0].length;
        
        //throw new AbstractMethodError("width: " + tileWeight + ", " + gameData.getDisplayWidth() + " height: " + tileHeight + ", " + gameData.getDisplayHeight());
        for (int r = 0; r < d.length; r++) {
            for (int c = 0; c < d[r].length; c++) {
                //Tile t = new Tile(d[r][c]);
                Tile t = new Tile();
                //PositionPart p = new PositionPart(c * d.length, r * d[0].length, 0);
                PositionPart p = new PositionPart(c * tileWeight, r * tileHeight, 0);
                t.add(p);
                t.setImagePath(d[r][c].getImagePath());
//                setShape(t, tileWeight, tileHeight);
                world.addEntity(t);
            }
        }
    }

//    private void setShape(Tile tile, float width, float height) {
//        float[] shapex = tile.getShapeX();
//        float[] shapey = tile.getShapeY();
//        PositionPart positionPart = tile.getPart(PositionPart.class);
//        float x = positionPart.getX();
//        float y = positionPart.getY();
//        float radians = positionPart.getRadians();
//
//        shapex[0] = x;
//        shapey[0] = y;
//
//        shapex[1] = x;
//        shapey[1] = y + height;
//
//        shapex[2] = x + width;
//        shapey[2] = y + height;
//
//        shapex[3] = x + width;
//        shapey[3] = y;
//
//        tile.setShapeX(shapex);
//        tile.setShapeY(shapey);
//        
//        //throw new AbstractMethodError("x: " + x + " y: " + y + " width: " + width + " height: " + height);
//    }
}
