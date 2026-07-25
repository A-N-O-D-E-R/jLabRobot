package io.jlabrobot.liquidhandling;

import io.jlabrobot.resources.Tip;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a 96-channel pipette head with fixed 96 channels.
 * Each channel can hold a single tip for parallel liquid handling operations.
 */
public class Head96 {
    private static final int NUM_CHANNELS = 96;
    private final List<Tip> tips;

    /**
     * Constructs a Head96 with 96 empty channels.
     */
    public Head96() {
        this.tips = new ArrayList<>(Collections.nCopies(NUM_CHANNELS, null));
    }

    /**
     * Sets tips on all 96 channels.
     * @param newTips the list of exactly 96 tips
     * @throws IllegalArgumentException if the list size is not exactly 96
     */
    public void setTips(List<Tip> newTips) {
        if (newTips.size() != NUM_CHANNELS) {
            throw new IllegalArgumentException("Head96 requires exactly 96 tips, got " + newTips.size());
        }
        for (int i = 0; i < NUM_CHANNELS; i++) {
            tips.set(i, newTips.get(i));
        }
    }

    /**
     * Removes all tips from all 96 channels.
     */
    public void clearTips() {
        Collections.fill(tips, null);
    }

    /**
     * Gets the unmodifiable list of tips on all 96 channels.
     * @return the list of tips
     */
    public List<Tip> getTips() {
        return Collections.unmodifiableList(tips);
    }

    /**
     * Checks whether this head has any tips mounted.
     * @return true if at least one tip is mounted, false otherwise
     */
    public boolean hasTips() {
        return tips.stream().anyMatch(t -> t != null);
    }

    /**
     * Gets the total number of channels.
     * @return 96
     */
    public int getNumChannels() {
        return NUM_CHANNELS;
    }
}
