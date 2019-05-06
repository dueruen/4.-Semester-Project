package racing.common.services;

import java.util.List;
import racing.common.data.Entity;
import racing.common.data.World;

/**
 * Implemented by class that calculates score
 */
public interface IScoreService {
    List<Entity> getScores(World world);
}
