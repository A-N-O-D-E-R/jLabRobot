package io.jlabrobot.resources;

import io.jlabrobot.core.AbstractResource;
import io.jlabrobot.core.Coordinate;
import io.jlabrobot.core.Volume;

public class Tip extends AbstractResource {
    private final Volume maxVolume;
    private boolean used;

    public Tip(String name, Coordinate location, Volume maxVolume) {
        super(name, location);
        this.maxVolume = maxVolume;
        this.used = false;
    }

    public Volume getMaxVolume() {
        return maxVolume;
    }

    public boolean isUsed() {
        return used;
    }

    public void markUsed() {
        this.used = true;
    }

    public void markUnused() {
        this.used = false;
    }
}
