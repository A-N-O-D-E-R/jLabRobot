package io.jlabrobot.resources;

import io.jlabrobot.core.AbstractResource;
import io.jlabrobot.core.Container;
import io.jlabrobot.core.Coordinate;
import io.jlabrobot.core.Volume;

public class Well extends AbstractResource implements Container {
    private final Volume maxVolume;
    private Volume currentVolume;

    public Well(String name, Coordinate location, Volume maxVolume) {
        super(name, location);
        this.maxVolume = maxVolume;
        this.currentVolume = new Volume(0);
    }

    @Override
    public Volume getMaxVolume() {
        return maxVolume;
    }

    @Override
    public Volume getCurrentVolume() {
        return currentVolume;
    }

    @Override
    public void setCurrentVolume(Volume volume) {
        if (volume.compareTo(maxVolume) > 0) {
            throw new IllegalArgumentException("Volume exceeds max capacity: " + volume + " > " + maxVolume);
        }
        this.currentVolume = volume;
    }

    public Volume getRemainingCapacity() {
        return maxVolume.subtract(currentVolume);
    }
}
