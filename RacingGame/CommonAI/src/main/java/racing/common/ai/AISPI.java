package racing.common.ai;

import java.util.ArrayList;
import racing.common.data.Entity;
import racing.common.data.World;
import racing.common.data.entityparts.PositionPart;

/**
 * SPI for AI 
 */
public interface AISPI {
    void setSourceNode(Entity p, World world, int checkpointCount);
    void startAI();
    ArrayList<PositionPart> getPath();
}
