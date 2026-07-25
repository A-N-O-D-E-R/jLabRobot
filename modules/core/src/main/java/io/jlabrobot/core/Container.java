package io.jlabrobot.core;

/**
 * Represents a resource that can hold liquid and track volume.
 * A container has a maximum capacity and tracks its current volume.
 */
public interface Container extends Resource {
    /**
     * Returns the maximum volume this container can hold.
     *
     * @return the maximum volume
     */
    Volume getMaxVolume();

    /**
     * Returns the current volume of liquid in this container.
     *
     * @return the current volume
     */
    Volume getCurrentVolume();

    /**
     * Sets the current volume of liquid in this container.
     *
     * @param volume the new current volume
     */
    void setCurrentVolume(Volume volume);
}
