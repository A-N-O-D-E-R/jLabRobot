package io.jlabrobot.core;

/**
 * Represents a 3D coordinate point with x, y, and z components.
 * Coordinates are used to track the location of resources within the laboratory automation system.
 *
 * @param x the x-axis coordinate
 * @param y the y-axis coordinate
 * @param z the z-axis coordinate
 */
public record Coordinate(double x, double y, double z) {
    /**
     * Constructs a 2D coordinate with z defaulting to 0.
     *
     * @param x the x-axis coordinate
     * @param y the y-axis coordinate
     */
    public Coordinate(double x, double y) {
        this(x, y, 0.0);
    }

    /**
     * Returns a new coordinate representing the sum of this coordinate and another.
     *
     * @param other the coordinate to add to this coordinate
     * @return a new coordinate with summed components
     */
    public Coordinate add(Coordinate other) {
        return new Coordinate(x + other.x, y + other.y, z + other.z);
    }

    /**
     * Returns a new coordinate representing the difference between this coordinate and another.
     *
     * @param other the coordinate to subtract from this coordinate
     * @return a new coordinate with the differences as components
     */
    public Coordinate subtract(Coordinate other) {
        return new Coordinate(x - other.x, y - other.y, z - other.z);
    }
}
