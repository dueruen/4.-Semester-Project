package racing.common.data;

/**
 * Used to describe a tile type
 * 
 */
public enum TileType {
    /**
     * Finish line
     */
    FINISHLINE(0, 0.0, "tiles/goal.png", false),
    /**
     * Road
     */
    ROAD(1, 0.0, "tiles/road.png", false),
    /**
     * Grass
     */
    GRASS(2, 1.0, "tiles/grass.png", false),
    /**
     * Water
     */

    WATER(3, -1.0, "tiles/water.png", true),
    

    /**
     * Tree
     */
    TREE(4, -1.0, "tiles/tree.png", true),
    
    /**
     * Start
     */
    START(5, 0.0, "tiles/start.png", false),
    
    /**
     * Spawn
     */
    SPAWN(6, 0.0, "tiles/spawn.png", false);
    
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
    
    /**
     * True if the tile is static (Has collision)
     */
    private boolean isStatic;

    private TileType(int value, double weight, String imagePath, boolean isStatic) {
        this.value = value;
        this.weight = weight;
        this.imagePath = imagePath;
        this.isStatic = isStatic;
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

    public boolean isIsStatic() {
        return isStatic;
    }
}