package io.jlabrobot.core;

public interface Container extends Resource {
    Volume getMaxVolume();
    Volume getCurrentVolume();
    void setCurrentVolume(Volume volume);
}
