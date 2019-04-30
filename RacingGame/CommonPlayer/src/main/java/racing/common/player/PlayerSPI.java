package racing.common.player;

import racing.common.data.entityparts.PositionPart;

/**
 * The SPI for a map
 * 
 */
public interface PlayerSPI {
    PositionPart getPosition();
}