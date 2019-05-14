package racing.spawnsystem;

import java.util.LinkedList;
import java.util.List;
import racing.common.ai.AISPI;
import racing.common.data.Entity;
import racing.common.data.GameData;
import racing.common.data.TileType;
import racing.common.data.World;
import racing.common.data.entityparts.PositionPart;
import racing.common.data.entityparts.TilePart;
import racing.common.map.MapSPI;
import racing.common.map.Tile;
import racing.common.npc.NPC;
import racing.common.npc.NPCSPI;
import racing.common.player.Player;
import racing.common.player.PlayerSPI;
import racing.common.services.ISpawnService;

/**
 * Control system for the lap system.
 */
public class SpawnService implements ISpawnService {
    
    /**
     * AISPI
     */
    private static AISPI ai;
    
    /**
     * MapSPI
     */
    private static MapSPI map;

    /**
     * NPCSPI
     */
    private static NPCSPI npc;

    /**
     * PlayerSPI
     */
    private static PlayerSPI player;

    @Override
    public void spawn(GameData gameData, World world, String mapName) {
        if (map != null) {
            map.loadFromFile(mapName, gameData, world);
        }
        
        LinkedList<PositionPart> positionsParts = new LinkedList<>();
        for (Entity tile : world.getEntities(Tile.class)) {
            TilePart tp = tile.getPart(TilePart.class);
            if (tp.getType() == TileType.SPAWN) {
                positionsParts.add(tile.getPart(PositionPart.class));
            }
        }
        if (positionsParts.isEmpty()) {
            return;
        }

        Player p = player.create(gameData, world);
        PositionPart po = p.getPart(PositionPart.class);
        PositionPart pl = positionsParts.pop();
        po.setPosition(pl.getX(), pl.getY());
        
        //Initialize the ai
        ai.startAI();

        NPC[] npcs = npc.create(gameData, world, positionsParts.size());

        int i = 0;
        for (PositionPart pp : positionsParts) {
            po = npcs[i].getPart(PositionPart.class);
            po.setPosition(pp.getX(), pp.getY());
            i++;
        }
    }

    /**
     * Declarative service set AI service
     *
     * @param ai AI service
     */
    public void setAIService(AISPI ai) {
        this.ai = ai;

    }

    /**
     * Declarative service remove AI service
     *
     * @param ai AI service
     */
    public void removeAIService(AISPI ai) {
        this.ai = null;
    }
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

    /**
     * Declarative service set NPC service
     *
     * @param npc NPC service
     */
    public void setNPCService(NPCSPI npc) {
        this.npc = npc;
    }

    /**
     * Declarative service remove npc service
     *
     * @param npc npc service
     */
    public void removeNPCService(NPCSPI npc) {
        this.npc = null;
    }

    /**
     * Declarative service set player service
     *
     * @param player player service
     */
    public void setPlayerService(PlayerSPI player) {
        this.player = player;
    }

    /**
     * Declarative service remove player service
     *
     * @param player player service
     */
    public void removePlayerService(PlayerSPI player) {
        this.player = null;
    }
}
