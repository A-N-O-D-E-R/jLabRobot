package io.jlabrobot.liquidhandling;

import io.jlabrobot.core.Volume;
import io.jlabrobot.resources.Well;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class VolumeTracker {
    private static final Logger log = LoggerFactory.getLogger(VolumeTracker.class);

    private final Map<Well, Volume> wellVolumes;

    public VolumeTracker() {
        this.wellVolumes = new HashMap<>();
    }

    public void recordAspirate(Well well, Volume volume) {
        Volume currentVolume = wellVolumes.getOrDefault(well, well.getCurrentVolume());
        Volume newVolume = currentVolume.subtract(volume);

        if (newVolume.compareTo(Volume.ul(0)) < 0) {
            log.warn("Aspirating more than available from {}: {} > {}", well.getName(), volume, currentVolume);
        }

        wellVolumes.put(well, newVolume);
        well.setCurrentVolume(newVolume);
    }

    public void recordDispense(Well well, Volume volume) {
        Volume currentVolume = wellVolumes.getOrDefault(well, well.getCurrentVolume());
        Volume newVolume = currentVolume.add(volume);

        if (newVolume.compareTo(well.getMaxVolume()) > 0) {
            log.warn("Dispensing more than capacity to {}: {} > {}", well.getName(), newVolume, well.getMaxVolume());
        }

        wellVolumes.put(well, newVolume);
        well.setCurrentVolume(newVolume);
    }

    public Volume getVolume(Well well) {
        return wellVolumes.getOrDefault(well, well.getCurrentVolume());
    }

    public void reset() {
        wellVolumes.clear();
    }
}
