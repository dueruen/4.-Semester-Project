package racing.common.data.entityparts;

import racing.common.data.Entity;
import racing.common.data.GameData;

/**
 * EntityPart describes a behaviour of an entity
 */
public interface EntityPart {
    /**
     * How should the behaviour be processed
     * @param gameData 
     * @param entity   
     */
    void process(GameData gameData, Entity entity);
}
