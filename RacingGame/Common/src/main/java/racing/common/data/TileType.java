package racing.common.data;

/**
 * Used to describe a tile type
 * 
 */
public enum TileType {
    /**
     * Finish line
     */
    FINISHLINE(0, 0.0, "tiles/goal.png"),
    /**
     * Road
     */
    ROAD(1, 0.0, "tiles/road.png"),
    /**
     * Grass
     */
    GRASS(2, 1.0, "tiles/grass.png"),
    /**
     * Item
     */
    Item(1, 0.0, "tiles/Item.png"),
   
    /**
     * Water
     */
    WATER(3, -1.0, "tiles/water.png");
    
    /**
     * Used to identify the tile type
     */
    private int value;
    
    /**
     * The type weight
     */
    private double weight;
    
    /**
     * The path to the image, this path is relative to the OSGiLibGDX
     * src/main/resources folder
     */
    private String imagePath;

    private TileType(int value, double weight, String imagePath) {
        this.value = value;
        this.weight = weight;
        this.imagePath = imagePath;
    }

    public int getValue() {
        return value;
    }

    public double getWeight() {
        return weight;
    }

    public String getImagePath() {
        return imagePath;
    }
    
}