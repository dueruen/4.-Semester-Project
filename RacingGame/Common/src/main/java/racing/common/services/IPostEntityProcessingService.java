package racing.common.services;

import racing.common.data.GameData;
import racing.common.data.World;

/**
 * PostEntityProcessingService interface
 * 
 */
public interface IPostEntityProcessingService  {
        void process(GameData gameData, World world);
}
