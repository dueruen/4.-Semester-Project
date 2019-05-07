package racing.common.player;

import racing.common.data.entityparts.PositionPart;

/**
 * The SPI for a player
 * 
 */
public interface PlayerSPI {
    PositionPart getPosition();
    public boolean hasItem();
}