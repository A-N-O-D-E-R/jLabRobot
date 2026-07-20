package io.jlabrobot.core;

public record Coordinate(double x, double y, double z) {
    public Coordinate(double x, double y) {
        this(x, y, 0.0);
    }
    
    public Coordinate add(Coordinate other) {
        return new Coordinate(x + other.x, y + other.y, z + other.z);
    }
    
    public Coordinate subtract(Coordinate other) {
        return new Coordinate(x - other.x, y - other.y, z - other.z);
    }
}
