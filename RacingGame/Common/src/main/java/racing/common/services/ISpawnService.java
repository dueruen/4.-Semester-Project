package racing.common.services;

import racing.common.data.GameData;
import racing.common.data.World;

/**
 * Implemented by class that spawn
 */
public interface ISpawnService {
    void spawn(GameData gameData, World world, String mapName);
}
