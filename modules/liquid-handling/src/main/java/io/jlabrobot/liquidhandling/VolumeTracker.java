package io.jlabrobot.liquidhandling;

import io.jlabrobot.core.Volume;
import io.jlabrobot.resources.Well;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Tracks volume changes in wells during liquid handling operations.
 * Logs warnings when aspirations or dispenses exceed well capacity.
 */
public class VolumeTracker {
    private static final Logger log = LoggerFactory.getLogger(VolumeTracker.class);

    private final Map<Well, Volume> wellVolumes;

    /**
     * Constructs an empty VolumeTracker.
     */
    public VolumeTracker() {
        this.wellVolumes = new HashMap<>();
    }

    /**
     * Records an aspiration operation from a well.
     * @param well the well being aspirated from
     * @param volume the volume being aspirated
     */
    public void recordAspirate(Well well, Volume volume) {
        Volume currentVolume = wellVolumes.getOrDefault(well, well.getCurrentVolume());
        Volume newVolume = currentVolume.subtract(volume);

        if (newVolume.compareTo(Volume.ul(0)) < 0) {
            log.warn("Aspirating more than available from {}: {} > {}", well.getName(), volume, currentVolume);
        }

        wellVolumes.put(well, newVolume);
        well.setCurrentVolume(newVolume);
    }

    /**
     * Records a dispense operation to a well.
     * @param well the well being dispensed to
     * @param volume the volume being dispensed
     */
    public void recordDispense(Well well, Volume volume) {
        Volume currentVolume = wellVolumes.getOrDefault(well, well.getCurrentVolume());
        Volume newVolume = currentVolume.add(volume);

        if (newVolume.compareTo(well.getMaxVolume()) > 0) {
            log.warn("Dispensing more than capacity to {}: {} > {}", well.getName(), newVolume, well.getMaxVolume());
        }

        wellVolumes.put(well, newVolume);
        well.setCurrentVolume(newVolume);
    }

    /**
     * Gets the current volume in a well.
     * @param well the well
     * @return the volume
     */
    public Volume getVolume(Well well) {
        return wellVolumes.getOrDefault(well, well.getCurrentVolume());
    }

    /**
     * Clears all tracked volume data.
     */
    public void reset() {
        wellVolumes.clear();
    }
}
