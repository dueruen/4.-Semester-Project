/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package racing.common.data;

/**
 *
 * @author yodamaster42
 */
public class GameImage {
    private String ImagePath;
    private float width;
    private float height;

    public GameImage() {
        this("default.png", 20, 20);
    }
    
    public GameImage(String ImagePath, float width, float height) {
        this.ImagePath = ImagePath;
        this.width = width;
        this.height = height;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String ImagePath) {
        this.ImagePath = ImagePath;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }
}
