package racing.common.data;

public enum TileType {
    FINISHLINE(0, 0.0, "tiles/goal.png"),
    ROAD(1, 0.0, "tiles/road.png"),
    GRASS(2, 1.0, "tiles/grass.png"),
    WATER(3, -1.0, "tiles/water.png");

    private int value;
    private double weight;
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