package io.jlabrobot.liquidhandling;

import io.jlabrobot.resources.Tip;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Head96 {
    private static final int NUM_CHANNELS = 96;
    private final List<Tip> tips;

    public Head96() {
        this.tips = new ArrayList<>(Collections.nCopies(NUM_CHANNELS, null));
    }

    public void setTips(List<Tip> newTips) {
        if (newTips.size() != NUM_CHANNELS) {
            throw new IllegalArgumentException("Head96 requires exactly 96 tips, got " + newTips.size());
        }
        for (int i = 0; i < NUM_CHANNELS; i++) {
            tips.set(i, newTips.get(i));
        }
    }

    public void clearTips() {
        Collections.fill(tips, null);
    }

    public List<Tip> getTips() {
        return Collections.unmodifiableList(tips);
    }

    public boolean hasTips() {
        return tips.stream().anyMatch(t -> t != null);
    }

    public int getNumChannels() {
        return NUM_CHANNELS;
    }
}
