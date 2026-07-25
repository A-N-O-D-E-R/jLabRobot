package io.jlabrobot.resources;

import io.jlabrobot.core.AbstractResource;
import io.jlabrobot.core.Coordinate;
import io.jlabrobot.core.Volume;

/**
 * Represents a single pipette tip within a tip rack.
 *
 * A Tip is a disposable consumable resource used for liquid handling operations.
 * Each tip has a maximum volume capacity and tracks its usage state (used/unused).
 * Tips are typically picked up from a TipRack, used for liquid transfer, and then
 * discarded. A tip should not be reused once marked as used.
 */
public class Tip extends AbstractResource {
    private final Volume maxVolume;
    private boolean used;

    /**
     * Creates a new tip with the specified properties.
     *
     * @param name the unique name identifier for this tip
     * @param location the coordinate position of this tip relative to its parent tip rack
     * @param maxVolume the maximum volume capacity of this tip
     */
    public Tip(String name, Coordinate location, Volume maxVolume) {
        super(name, location);
        this.maxVolume = maxVolume;
        this.used = false;
    }

    /**
     * Returns the maximum volume capacity of this tip.
     *
     * @return the maximum volume this tip can hold
     */
    public Volume getMaxVolume() {
        return maxVolume;
    }

    /**
     * Returns whether this tip has been used.
     *
     * @return {@code true} if the tip is marked as used, {@code false} otherwise
     */
    public boolean isUsed() {
        return used;
    }

    /**
     * Marks this tip as used, indicating it should not be reused.
     *
     * This is typically called after the tip has been used for a liquid handling operation.
     */
    public void markUsed() {
        this.used = true;
    }

    /**
     * Marks this tip as unused.
     *
     * This method can be used to reset a tip's usage state, though in practice tips are
     * typically replaced with fresh ones rather than being reset.
     */
    public void markUnused() {
        this.used = false;
    }
}
