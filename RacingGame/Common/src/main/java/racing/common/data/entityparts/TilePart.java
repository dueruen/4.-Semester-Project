package racing.common.data.entityparts;

import racing.common.data.Entity;
import racing.common.data.GameData;
import racing.common.data.TileType;

/**
 * Object containing information about a tile
 *
 */
public class TilePart implements EntityPart {

    /**
     * The tile type
     */
    private TileType type;

    public TilePart(TileType type) {
        this.type = type;
    }

    public TileType getType() {
        return type;
    }

    public void setType(TileType type) {
        this.type = type;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
    }
}
