package racing.common.player;

import racing.common.data.GameData;
import racing.common.data.World;
import racing.common.data.entityparts.PositionPart;

/**
 * The SPI for a player
 *
 */
public interface PlayerSPI {

    PositionPart getPosition();

    Player create(GameData gameData, World world);

    void removeAll(GameData gameData, World world);
}
