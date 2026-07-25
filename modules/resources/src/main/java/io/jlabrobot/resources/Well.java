package io.jlabrobot.resources;

import io.jlabrobot.core.AbstractResource;
import io.jlabrobot.core.Container;
import io.jlabrobot.core.Coordinate;
import io.jlabrobot.core.Volume;

/**
 * Represents a single well within a microplate.
 *
 * A Well is a liquid container with a fixed maximum volume and tracks the current
 * volume of liquid it contains. Wells are typically part of a Plate and are accessed
 * by their row and column indices. The well volume is updated during liquid handling
 * operations such as aspiration and dispensing.
 */
public class Well extends AbstractResource implements Container {
    private final Volume maxVolume;
    private Volume currentVolume;

    /**
     * Creates a new well with the specified properties.
     *
     * @param name the unique name identifier for this well
     * @param location the coordinate position of this well relative to its parent plate
     * @param maxVolume the maximum volume capacity of this well
     */
    public Well(String name, Coordinate location, Volume maxVolume) {
        super(name, location);
        this.maxVolume = maxVolume;
        this.currentVolume = new Volume(0);
    }

    /**
     * Returns the maximum volume capacity of this well.
     *
     * @return the maximum volume this well can hold
     */
    @Override
    public Volume getMaxVolume() {
        return maxVolume;
    }

    /**
     * Returns the current volume of liquid in this well.
     *
     * @return the current volume contained in this well
     */
    @Override
    public Volume getCurrentVolume() {
        return currentVolume;
    }

    /**
     * Sets the current volume of liquid in this well.
     *
     * @param volume the new volume to set
     * @throws IllegalArgumentException if the volume exceeds the maximum capacity
     */
    @Override
    public void setCurrentVolume(Volume volume) {
        if (volume.compareTo(maxVolume) > 0) {
            throw new IllegalArgumentException("Volume exceeds max capacity: " + volume + " > " + maxVolume);
        }
        this.currentVolume = volume;
    }

    /**
     * Calculates the remaining capacity available in this well.
     *
     * @return the difference between maximum and current volume
     */
    public Volume getRemainingCapacity() {
        return maxVolume.subtract(currentVolume);
    }
}
