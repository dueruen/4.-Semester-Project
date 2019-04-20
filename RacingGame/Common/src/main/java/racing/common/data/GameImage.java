package racing.common.data;

/**
 * Object used to store information about a image/sprite
 *
 */
public class GameImage {

    /**
     * The path to the image, this path is relative to the OSGiLibGDX
     * src/main/resources folder
     */
    private String ImagePath;

    /**
     * Image width
     */
    private float width;

    /**
     * Image height
     */
    private float height;

    /**
     * No-arg constructor, set the images default values
     */
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
