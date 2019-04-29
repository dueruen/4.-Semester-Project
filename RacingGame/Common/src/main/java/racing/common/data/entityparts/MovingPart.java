
package racing.common.data.entityparts;

import racing.common.data.Entity;
import racing.common.data.GameData;
import static racing.common.data.GameKeys.LEFT;
import static racing.common.data.GameKeys.RIGHT;
import static racing.common.data.GameKeys.UP;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

public class MovingPart implements EntityPart {
    /**
     * Delta x and y describes a coordinate in time
     */
    private float dx, dy;

    /**
     * Acceleration and deceleration
     */
    private float deceleration, acceleration;

    /**
     * Max speed and rotation speed
     */
    private float maxSpeed, rotationSpeed;

    /**
     * Booleans to indicate in which direction to move
     */
    private boolean left, right, up;

    /**
     * Create a new instance of a moving part
     * @param deceleration
     * @param acceleration
     * @param maxSpeed
     * @param rotationSpeed
     */
    public MovingPart(float deceleration, float acceleration, float maxSpeed, float rotationSpeed) {
        this.deceleration = deceleration;
        this.acceleration = acceleration;
        this.maxSpeed = maxSpeed;
        this.rotationSpeed = rotationSpeed;
    }

    /**
     * Get dx
     * @return position on x axis
     */
    public float getDx() {
        return dx;
    }

    /**
     * Get dy
     * @return position on y axis
     */
    public float getDy() {
        return dy;
    }

    /**
     * Set deceleration
     * @param deceleration
     */
    public void setDeceleration(float deceleration) {
        this.deceleration = deceleration;
    }

    /**
     * Set Acceleration
     * @param acceleration
     */
    public void setAcceleration(float acceleration) {
        this.acceleration = acceleration;
    }

    /**
     * Set max speed
     * @param maxSpeed
     */
    public void setMaxSpeed(float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    /**
     * Set speed
     * @param speed
     */
    public void setSpeed(float speed) {
        this.acceleration = speed;
        this.maxSpeed = speed;
    }

    /**
     * Set rotationSpeed
     * @param rotationSpeed
     */
    public void setRotationSpeed(float rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    /**
     * Set left
     * @param left
     */
    public void setLeft(boolean left) {
        this.left = left;
    }

    /**
     * Set right
     * @param right
     */
    public void setRight(boolean right) {
        this.right = right;
    }

    /**
     * Set up
     * @param up
     */
    public void setUp(boolean up) {
        this.up = up;
    }

    /**
     * Process the MovingPart behaviour
     * @param gameData
     * @param entity
     */
    @Override
    public void process(GameData gameData, Entity entity) {
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();
        float dt = gameData.getDelta();

        // turning
        if (left) {
            radians += rotationSpeed * dt;
        }

        if (right) {
            radians -= rotationSpeed * dt;
        }

        // accelerating
        if (up) {
            dx += cos(radians) * acceleration * dt;
            dy += sin(radians) * acceleration * dt;
        }

        // deccelerating
        float vec = (float) sqrt(dx * dx + dy * dy);
        if (vec > 0) {
            dx -= (dx / vec) * deceleration * dt;
            dy -= (dy / vec) * deceleration * dt;
        }
        if (vec > maxSpeed) {
            dx = (dx / vec) * maxSpeed;
            dy = (dy / vec) * maxSpeed;
        }

        // set position
        x += dx * dt;
        if (x > gameData.getDisplayWidth()) {
            x = 0;
        } else if (x < 0) {
            x = gameData.getDisplayWidth();
        }

        y += dy * dt;
        if (y > gameData.getDisplayHeight()) {
            y = 0;
        } else if (y < 0) {
            y = gameData.getDisplayHeight();
        }

        positionPart.setX(x);
        positionPart.setY(y);

        positionPart.setRadians(radians);
    }

}
