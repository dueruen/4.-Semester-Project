package racing.common.data.entityparts;

import racing.common.data.Entity;
import racing.common.data.GameData;

public interface EntityPart {

    void process(GameData gameData, Entity entity);
}
