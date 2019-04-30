package racing.labsystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import racing.common.data.Entity;
import racing.common.data.GameData;
import racing.common.data.TileType;
import racing.common.data.World;
import racing.common.data.entityparts.ScorePart;
import racing.common.data.entityparts.TilePart;
import racing.common.map.MapSPI;
import racing.common.map.Tile;
import racing.common.services.IPostEntityProcessingService;
import racing.common.services.IScoreService;

/**
 * Control system for the lap system.
 */
public class LapControllSystem implements IPostEntityProcessingService, IScoreService {

    private Map<String, Wrapper> entities = new HashMap<>();

    /**
     * MapSPI
     */
    private MapSPI map;

    /**
     * Declarative service set map service
     *
     * @param map map service
     */
    public void setMapService(MapSPI map) {
        this.map = map;
    }

    /**
     * Declarative service remove map service
     *
     * @param map map service
     */
    public void removeMapService(MapSPI map) {
        this.map = null;
    }

    @Override
    public void process(GameData gameData, World world) {
        for (Entity entity : world.getEntities()) {
            ScorePart sp = entity.getPart(ScorePart.class);
            if (sp == null) {
                continue;
            }

            if (!entities.containsKey(entity.getID())) {
                entities.put(entity.getID(), new Wrapper());
            }

            Wrapper w = entities.get(entity.getID());
            Tile currentTile = map.getTile(entity, world);
            if (w.current != null && w.previous != null) {
                if (currentTile.getID().equals(w.current.getID())) {
                    continue;
                }

                TilePart currentPart = w.current.getPart(TilePart.class);
                TilePart previousPart = w.previous.getPart(TilePart.class);
                if ((currentPart.getType() == TileType.FINISHLINE && previousPart.getType() == TileType.START)
                        && (!w.current.getID().equals(currentTile.getID()) && !w.previous.getID().equals(currentTile.getID()))) {
                    if (w.start) {
                        w.start = false;
                    } else {
                        sp.setLabs(sp.getLabs() + 1);
                    }
                }
            }
            w.previous = w.current;
            w.current = currentTile;
        }
    }

    @Override
    public List<Entity> getScores(World world) {
        List<Entity> e = new ArrayList<>();
        for (Entity entity : world.getEntities()) {
            ScorePart sp = entity.getPart(ScorePart.class);
            if (sp == null) {
                continue;
            }
            e.add(entity);
        }
        return e;
    }

    /**
     * Contains local information
     */
    private class Wrapper {

        /**
         * Current tile
         */
        Tile current;
        /**
         * previous tile
         */
        Tile previous;
        /**
         * True until the first time the start line is crossed
         */
        boolean start = true;
    }
}
