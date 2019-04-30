package racing.common.services;

import java.util.List;
import racing.common.data.Entity;
import racing.common.data.World;

/**
 *
 */
public interface IScoreService {
    List<Entity> getScores(World world);
}
