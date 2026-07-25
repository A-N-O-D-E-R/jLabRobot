package io.jlabrobot.core;

/**
 * Represents a volume measurement in microliters.
 * Provides factory methods for creating volumes in different units and supports arithmetic operations.
 * Volumes cannot be negative.
 *
 * @param microliters the volume measurement in microliters
 */
public record Volume(double microliters) implements Comparable<Volume> {
    /**
     * Validates that the volume is not negative.
     *
     * @throws IllegalArgumentException if the volume is negative
     */
    public Volume {
        if (microliters < 0) {
            throw new IllegalArgumentException("Volume cannot be negative");
        }
    }

    /**
     * Creates a Volume from a value in microliters.
     *
     * @param microliters the volume in microliters
     * @return a new Volume instance
     */
    public static Volume ul(double microliters) {
        return new Volume(microliters);
    }

    /**
     * Creates a Volume from a value in milliliters, converting to microliters.
     *
     * @param milliliters the volume in milliliters
     * @return a new Volume instance
     */
    public static Volume ml(double milliliters) {
        return new Volume(milliliters * 1000.0);
    }

    /**
     * Returns a new volume representing the sum of this volume and another.
     *
     * @param other the volume to add to this volume
     * @return a new Volume with the summed microliters
     */
    public Volume add(Volume other) {
        return new Volume(microliters + other.microliters);
    }

    /**
     * Returns a new volume representing the difference between this volume and another.
     *
     * @param other the volume to subtract from this volume
     * @return a new Volume with the difference in microliters
     */
    public Volume subtract(Volume other) {
        return new Volume(microliters - other.microliters);
    }

    /**
     * Compares this volume with another volume for ordering.
     *
     * @param other the volume to compare with
     * @return a negative integer, zero, or a positive integer as this volume is less than, equal to, or greater than the other volume
     */
    @Override
    public int compareTo(Volume other) {
        return Double.compare(microliters, other.microliters);
    }
}
