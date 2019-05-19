package racing.common.data.entityparts;

import racing.common.data.Entity;
import racing.common.data.GameData;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

/**
 * Part-object deifning physical attributes related to movement of Entity's
 */
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
     * Penalty
     */
    private float penalty = 1;

    /**
     * Max speed and rotation speed
     */
    private float maxSpeed, rotationSpeed, currentSpeed, reverseSpeed;

    /**
     * Booleans to indicate in which direction to move
     */
    private boolean left, right, up, down, moving, reversing;

    private int pauseTimer = 0;

    /**
     * Create a new instance of a moving part
     *
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
     *
     * @return position on x axis
     */
    public float getDx() {
        return dx;
    }

    /**
     * Get dy
     *
     * @return position on y axis
     */
    public float getDy() {
        return dy;
    }

    /**
     * Set deceleration
     *
     * @param deceleration
     */
    public void setDeceleration(float deceleration) {
        this.deceleration = deceleration;
    }

    /**
     * Set Acceleration
     *
     * @param acceleration
     */
    public void setAcceleration(float acceleration) {
        this.acceleration = acceleration;
    }

    /**
     * Set max speed
     *
     * @param maxSpeed
     */
    public void setMaxSpeed(float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    /**
     * Set speed
     *
     * @param speed
     */
    public void setSpeed(float speed) {
        this.currentSpeed = speed;
    }

    /**
     * Set penalty
     *
     * @param penalty
     */
    public void setPenalty(float penalty) {
        this.penalty = penalty;
    }

    /**
     * Set rotationSpeed
     *
     * @param rotationSpeed
     */
    public void setRotationSpeed(float rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    /**
     * Set left
     *
     * @param left
     */
    public void setLeft(boolean left) {
        this.left = left;
    }

    /**
     * Set right
     *
     * @param right
     */
    public void setRight(boolean right) {
        this.right = right;
    }

    /**
     * Set up
     *
     * @param up
     */
    public void setUp(boolean up) {
        this.up = up;
    }

    /**
     * Set down
     *
     * @param down
     */
    public void setDown(boolean down) {
        this.down = down;
    }

    /**
     * Set moving
     *
     * @param moving
     */
    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    /**
     * Set reversing
     *
     * @param moving
     */
    public void setReversing(boolean reversing) {
        this.reversing = reversing;
    }

    /**
     * Get speed
     *
     */
    public float getSpeed() {
        return this.currentSpeed;
    }

    public int getPauseTimer() {
        return pauseTimer;
    }

    public void setPauseTimer(int pauseTimer) {
        this.pauseTimer = pauseTimer;
    }

    /**
     * Process the MovingPart behaviour
     *
     * @param gameData
     * @param entity
     */
    @Override
    public void process(GameData gameData, Entity entity) {
        if (pauseTimer > 0) {
            pauseTimer--;
        } else {
            PositionPart positionPart = entity.getPart(PositionPart.class);
            float x = positionPart.getX();
            float y = positionPart.getY();
            float radians = positionPart.getRadians();
            float dt = gameData.getDelta();
            float actualMaxSpeed = maxSpeed / penalty;

            if (actualMaxSpeed < currentSpeed && penalty > 1) {
                setSpeed(Math.min(currentSpeed, actualMaxSpeed));
            }

            if (currentSpeed < 0 && penalty > 1) {
                setSpeed(Math.max(currentSpeed, -actualMaxSpeed));
            }

            // turning
            if (left) {
                radians += rotationSpeed * dt;
            }

            if (right) {
                radians -= rotationSpeed * dt;
            }

            // accelerating
            if (up) {
                setReversing(false);
                if (currentSpeed <= actualMaxSpeed) {
                    currentSpeed += acceleration;
                }
                x += cos(radians) * currentSpeed * dt;
                y += sin(radians) * currentSpeed * dt;
                setMoving(true);
            }

            //continuos movement forward with decay in speed
            if (moving && !up) {
                if (currentSpeed > 0) {
                    currentSpeed -= deceleration / 10;
                }
                x += cos(radians) * currentSpeed * dt;
                y += sin(radians) * currentSpeed * dt;
            }

            //Deaccelerating or reversing
            if (down) {
                if (currentSpeed > (0 - actualMaxSpeed)) {
                    currentSpeed -= deceleration;
                }
                x += cos(radians) * currentSpeed * dt;
                y += sin(radians) * currentSpeed * dt;
                setMoving(false);
                setReversing(true);
            }

            //continuous movement backwards with decay in speed
            if (!down && reversing) {
                currentSpeed += acceleration / 10;
                x += cos(radians) * currentSpeed * dt;
                y += sin(radians) * currentSpeed * dt;
            }

            // set position
            x += dx * dt;
            y += dy * dt;

            positionPart.setX(x);
            positionPart.setY(y);

            positionPart.setRadians(radians);
        }
    }

}
