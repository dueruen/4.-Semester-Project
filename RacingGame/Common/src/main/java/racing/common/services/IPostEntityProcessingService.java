package racing.common.services;

import racing.common.data.GameData;
import racing.common.data.World;

public interface IPostEntityProcessingService  {
        void process(GameData gameData, World world);
}
