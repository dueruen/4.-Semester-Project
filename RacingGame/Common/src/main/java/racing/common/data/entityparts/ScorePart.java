package racing.common.data.entityparts;

import racing.common.data.Entity;
import racing.common.data.GameData;

/**
 *
 */
public class ScorePart implements EntityPart{
    private int labs;

    public int getLabs() {
        return labs;
    }

    public void setLabs(int labs) {
        this.labs = labs;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
    }
}
