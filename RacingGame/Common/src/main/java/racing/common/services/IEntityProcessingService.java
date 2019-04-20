package racing.common.services;

import racing.common.data.GameData;
import racing.common.data.World;

/**
 * EntityProcessingService interface
 * 
 */
public interface IEntityProcessingService {

    void process(GameData gameData, World world);
}
