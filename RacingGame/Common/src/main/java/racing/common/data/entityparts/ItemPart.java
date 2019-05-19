package racing.common.data.entityparts;

import racing.common.data.Entity;
import racing.common.data.GameData;

public class ItemPart implements EntityPart {

    private boolean isUsing;
    private int chargesLeft;
    private Class itemClass;
    private int delay;
    private int pauseTimer;

    public ItemPart() {
        itemClass = null;
    }

    public void useCharge() {
        chargesLeft--;
        isUsing = false;
        if (chargesLeft <= 0) {
            itemClass = null;
        }
        pauseTimer = delay;
    }

    public boolean readyToUse(Class c) {
        if (c == itemClass && isUsing && chargesLeft > 0 && pauseTimer == 0) {
            return true;
        }
        return false;
    }

    public boolean isUsing() {
        return this.isUsing;
    }

    public void setIsUsing(boolean b) {
        this.isUsing = b;
    }

    public int getChargesLeft() {
        return chargesLeft;
    }

    public void setChargesLeft(int chargesLeft) {
        this.chargesLeft = chargesLeft;
    }

    public Class getItemClass() {
        return itemClass;
    }

    public void setItemClass(Class itemClass) {
        this.itemClass = itemClass;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public int getPauseTimer() {
        return pauseTimer;
    }

    public void setPauseTimer(int pauseTimer) {
        this.pauseTimer = pauseTimer;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
        if (pauseTimer > 0) {
            pauseTimer--;
        }
    }
}
