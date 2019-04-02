package racing.common.map;

public enum TileType {
    FINISHLINE(0, 0.0),
    ROAD(1, 0.0),
    GRASS(2, 1.0),
    WATER(3, -1.0);

    private int value;
    private double weight;

    private TileType(int value, double weight) {
        this.value = value;
        this.weight = weight;
    }

    public int getValue() {
        return value;
    }

    public double getWeight() {
        return weight;
    }
}