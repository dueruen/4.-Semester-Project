/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package racing.common.data.entityparts;

import racing.common.data.Entity;
import racing.common.data.GameData;
import racing.common.data.TileType;

/**
 *
 * @author yodamaster42
 */
public class TilePart implements EntityPart {

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
