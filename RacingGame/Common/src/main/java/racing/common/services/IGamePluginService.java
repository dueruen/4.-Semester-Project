package racing.common.services;

import racing.common.data.GameData;
import racing.common.data.World;

public interface IGamePluginService {
    void start(GameData gameData, World world);

    void stop(GameData gameData, World world);
}
