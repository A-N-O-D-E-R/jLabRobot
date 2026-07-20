package io.jlabrobot.washer;

import io.jlabrobot.machines.Machine;
import io.jlabrobot.machines.MachineException;
import io.jlabrobot.resources.Plate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Washer extends Machine {
    private static final Logger log = LoggerFactory.getLogger(Washer.class);

    private Plate currentPlate;

    public Washer(WasherBackend backend) {
        super(backend);
    }

    public void loadPlate(Plate plate) throws MachineException {
        requireSetup();
        log.info("Loading plate: {}", plate.getName());
        ((WasherBackend) backend).loadPlate(plate);
        this.currentPlate = plate;
    }

    public void unloadPlate() throws MachineException {
        requireSetup();
        if (currentPlate == null) {
            throw new MachineException("No plate loaded");
        }
        log.info("Unloading plate: {}", currentPlate.getName());
        ((WasherBackend) backend).unloadPlate();
        this.currentPlate = null;
    }

    public void wash(int cycles, double volumeMl) throws MachineException {
        requireSetup();
        if (currentPlate == null) {
            throw new MachineException("No plate loaded");
        }
        log.info("Washing {} with {} cycles, {}mL per cycle", currentPlate.getName(), cycles, volumeMl);
        ((WasherBackend) backend).wash(cycles, volumeMl);
    }

    public void washAndUnload(Plate plate, int cycles, double volumeMl) throws MachineException {
        loadPlate(plate);
        wash(cycles, volumeMl);
        unloadPlate();
    }

    public void prime() throws MachineException {
        requireSetup();
        log.info("Priming washer");
        ((WasherBackend) backend).prime();
    }
}
