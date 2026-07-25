package io.jlabrobot.liquidhandling;

import io.jlabrobot.resources.Tip;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a pipette head with a variable number of channels.
 * Manages the tips currently mounted on the head.
 */
public class Head {
    private List<Tip> currentTips;

    /**
     * Constructs an empty Head.
     */
    public Head() {
        this.currentTips = new ArrayList<>();
    }

    /**
     * Sets the tips on this head.
     * @param tips the list of tips to set
     */
    public void setTips(List<Tip> tips) {
        this.currentTips = new ArrayList<>(tips);
    }

    /**
     * Gets the unmodifiable list of tips on this head.
     * @return the list of tips
     */
    public List<Tip> getTips() {
        return Collections.unmodifiableList(currentTips);
    }

    /**
     * Removes all tips from this head.
     */
    public void clearTips() {
        this.currentTips.clear();
    }

    /**
     * Checks whether this head has any tips.
     * @return true if tips are mounted, false otherwise
     */
    public boolean hasTips() {
        return !currentTips.isEmpty();
    }

    /**
     * Gets the number of channels (tips) on this head.
     * @return the channel count
     */
    public int getChannelCount() {
        return currentTips.size();
    }
}
