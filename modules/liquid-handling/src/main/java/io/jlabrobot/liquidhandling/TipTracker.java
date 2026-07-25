package io.jlabrobot.liquidhandling;

import io.jlabrobot.resources.Tip;
import java.util.*;

/**
 * Tracks which tips are mounted on each channel of a pipette head.
 * Provides immutable state transitions for tip pickup and drop operations.
 */
public class TipTracker {
    private final Map<Integer, TipSpot> channelTips;

    /**
     * Constructs a TipTracker for a pipette head with the specified number of channels.
     * @param numChannels the number of channels
     */
    public TipTracker(int numChannels) {
        this.channelTips = new HashMap<>();
        for (int i = 0; i < numChannels; i++) {
            channelTips.put(i, TipSpot.empty());
        }
    }

    /**
     * Gets the tip state for a specific channel.
     * @param channel the channel index
     * @return the tip spot state
     */
    public TipSpot getTip(int channel) {
        return channelTips.getOrDefault(channel, TipSpot.empty());
    }

    /**
     * Creates a new tracker with a tip picked up on the specified channel.
     * @param channel the channel index
     * @param tip the tip to pick up
     * @return a new TipTracker with the updated state
     * @throws IllegalStateException if channel already has a tip or tip is already used
     */
    public TipTracker withTipPickedUp(int channel, Tip tip) {
        if (!getTip(channel).isEmpty()) {
            throw new IllegalStateException("Channel " + channel + " already has tip");
        }
        if (tip.isUsed()) {
            throw new IllegalStateException("Tip " + tip.getName() + " already used");
        }

        Map<Integer, TipSpot> newState = new HashMap<>(channelTips);
        newState.put(channel, new TipSpot(tip, true));
        return new TipTracker(newState);
    }

    /**
     * Creates a new tracker with the tip dropped from the specified channel.
     * @param channel the channel index
     * @return a new TipTracker with the updated state
     * @throws IllegalStateException if channel has no tip to drop
     */
    public TipTracker withTipDropped(int channel) {
        if (getTip(channel).isEmpty()) {
            throw new IllegalStateException("Channel " + channel + " has no tip to drop");
        }

        Map<Integer, TipSpot> newState = new HashMap<>(channelTips);
        newState.put(channel, TipSpot.empty());
        return new TipTracker(newState);
    }

    /**
     * Internal constructor for creating a TipTracker from an existing state map.
     * @param state the channel tip state map
     */
    private TipTracker(Map<Integer, TipSpot> state) {
        this.channelTips = new HashMap<>(state);
    }
}
