package io.jlabrobot.liquidhandling;

import io.jlabrobot.resources.Tip;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Head {
    private List<Tip> currentTips;

    public Head() {
        this.currentTips = new ArrayList<>();
    }

    public void setTips(List<Tip> tips) {
        this.currentTips = new ArrayList<>(tips);
    }

    public List<Tip> getTips() {
        return Collections.unmodifiableList(currentTips);
    }

    public void clearTips() {
        this.currentTips.clear();
    }

    public boolean hasTips() {
        return !currentTips.isEmpty();
    }

    public int getChannelCount() {
        return currentTips.size();
    }
}
