package io.jlabrobot.liquidhandling;

import io.jlabrobot.resources.Tip;
import java.util.*;

public class TipTracker {
    private final Map<Integer, TipSpot> channelTips;
    
    public TipTracker(int numChannels) {
        this.channelTips = new HashMap<>();
        for (int i = 0; i < numChannels; i++) {
            channelTips.put(i, TipSpot.empty());
        }
    }
    
    public TipSpot getTip(int channel) {
        return channelTips.getOrDefault(channel, TipSpot.empty());
    }
    
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
    
    public TipTracker withTipDropped(int channel) {
        if (getTip(channel).isEmpty()) {
            throw new IllegalStateException("Channel " + channel + " has no tip to drop");
        }
        
        Map<Integer, TipSpot> newState = new HashMap<>(channelTips);
        newState.put(channel, TipSpot.empty());
        return new TipTracker(newState);
    }
    
    private TipTracker(Map<Integer, TipSpot> state) {
        this.channelTips = new HashMap<>(state);
    }
}
