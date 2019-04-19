package racing.common.data.entityparts;

import racing.common.data.Entity;
import racing.common.data.GameData;

/**
 * Object containing information about a position, based on a 2D 
 * 
 */
public class PositionPart implements EntityPart {

    /**
     * The x
     */
    private float x;
    
    /**
     * The y
     */
    private float y;
    
    /**
     * The rotation
     */
    private float radians;

    public PositionPart(float x, float y, float radians) {
        this.x = x;
        this.y = y;
        this.radians = radians;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getRadians() {
        return radians;
    }
    
    public void setX(float newX) {
        this.x = newX;
    }
    
    public void setY(float newY) {
        this.y = newY;
    }

    public void setPosition(float newX, float newY) {
        this.x = newX;
        this.y = newY;
    }

    public void setRadians(float radians) {
        this.radians = radians;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
    }    
}
