package io.jlabrobot.washer.biotek;

import io.jlabrobot.machines.MachineException;
import io.jlabrobot.resources.Plate;
import io.jlabrobot.washer.WasherBackend;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BioTekBackend extends WasherBackend {
    private static final Logger log = LoggerFactory.getLogger(BioTekBackend.class);

    private Plate currentPlate;

    @Override
    public void setup() throws MachineException {
        log.info("Setting up BioTek washer (stub)");
        this.setupFinished = true;
    }

    @Override
    public void stop() throws MachineException {
        log.info("Stopping BioTek washer");
        this.setupFinished = false;
    }

    @Override
    public void loadPlate(Plate plate) throws MachineException {
        log.info("Loading plate: {}", plate.getName());
        this.currentPlate = plate;
    }

    @Override
    public void unloadPlate() throws MachineException {
        log.info("Unloading plate");
        this.currentPlate = null;
    }

    @Override
    public void wash(int cycles, double volumeMl) throws MachineException {
        log.info("Washing {} cycles with {}mL per cycle", cycles, volumeMl);
        for (int i = 1; i <= cycles; i++) {
            log.debug("Wash cycle {}/{}", i, cycles);
            dispense(volumeMl);
            aspirate();
        }
    }

    @Override
    public void aspirate() throws MachineException {
        log.debug("Aspirating wells");
    }

    @Override
    public void dispense(double volumeMl) throws MachineException {
        log.debug("Dispensing {}mL", volumeMl);
    }

    @Override
    public void prime() throws MachineException {
        log.info("Priming washer lines");
    }
}
