package racing.common.ai;

import racing.common.data.Entity;
import racing.common.data.World;
import racing.common.data.entityparts.PositionPart;

/**
 *
 * @author Victor Gram & Niclas Johansen
 */
public interface AISPI {
    PositionPart findNextPosition();
    void setSourceNode(Entity p, World world);
    void startAI();
    
}
