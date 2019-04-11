package racing.common.data.entityparts;

import racing.common.data.Entity;
import racing.common.data.GameData;

public class PositionPart implements EntityPart {

    private float x;
    private float y;
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
    
    public double distance(PositionPart p2) {
        double x = Math.pow(p2.x - this.x, 2);
        double y = Math.pow(p2.y - this.y, 2);
        return Math.sqrt(x + y);
    }

    @Override
    public void process(GameData gameData, Entity entity) {
    }    
}
