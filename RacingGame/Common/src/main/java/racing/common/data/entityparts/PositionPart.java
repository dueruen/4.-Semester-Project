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

    /**
     * Create a new position part
     * @param x
     * @param y
     * @param radians
     */
    public PositionPart(float x, float y, float radians) {
        this.x = x;
        this.y = y;
        this.radians = radians;
    }

    /**
     * Get x
     * @return x
     */
    public float getX() {
        return x;
    }

    /**
     * Get Y
     * @return y
     */
    public float getY() {
        return y;
    }

    /**
     * Get Radians
     * @return radians
     */
    public float getRadians() {
        return radians;
    }

    /**
     * set x
     * @param newX
     */
    public void setX(float newX) {
        this.x = newX;
    }

    /**
     * set y
     * @param newY
     */
    public void setY(float newY) {
        this.y = newY;
    }

    /**
     * set position
     * @param newX
     * @param newY
     */
    public void setPosition(float newX, float newY) {
        this.x = newX;
        this.y = newY;
    }

    /**
     * set radians
     * @param radians
     */
    public void setRadians(float radians) {
        this.radians = radians;
    }

    /**
     * process position
     * @param gameData
     * @param entity
     */
    @Override
    public void process(GameData gameData, Entity entity) {
    }
}
