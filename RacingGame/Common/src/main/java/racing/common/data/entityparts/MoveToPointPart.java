/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package racing.common.data.entityparts;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import racing.common.data.Entity;
import racing.common.data.GameData;

/**
 *
 * @author Victor Gram
 */
public class MoveToPointPart implements EntityPart {

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
    
    private PositionPart targetPoint;

    /**
     * Create a new instance of a moving part
     * @param deceleration
     * @param acceleration
     * @param maxSpeed
     * @param rotationSpeed
     */
    public MoveToPointPart(float deceleration, float acceleration, float maxSpeed, float rotationSpeed) {
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
        PositionPart currentPosition = entity.getPart(PositionPart.class);
        PositionPart targetPosition = targetPoint;
        
        //Initializes source position data
        float x = currentPosition.getX();
        float y = currentPosition.getY();
        float radians = currentPosition.getRadians();
        float dt = gameData.getDelta();

        //Initializes target position data
        float tX = targetPosition.getX();
        float tY= targetPosition.getY();
        float tRadians = targetPosition.getRadians();
        
        float deltaX = tX - x;
        float deltaY = tY - y;
        float angle = (float) Math.atan2(deltaY, deltaX);
        
        

        currentPosition.setX((float) (maxSpeed * Math.cos(radians)));
        currentPosition.setY((float) (maxSpeed * Math.sin(radians)));
        
        //currentPosition.setX(maxSpeed * x);
        //currentPosition.setY(maxSpeed * y);

        currentPosition.setRadians(angle);
    }
    
    public void setTargetPoint(PositionPart pp) { 
        this.targetPoint = pp;
    }

}

