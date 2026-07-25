package io.jlabrobot.washer.biotek;

import io.jlabrobot.machines.MachineException;
import io.jlabrobot.resources.Plate;
import io.jlabrobot.washer.WasherBackend;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Backend implementation for BioTek plate washer.
 * Supports multi-cycle washing with independent aspirate and dispense operations.
 */
public class BioTekBackend extends WasherBackend {
    private static final Logger log = LoggerFactory.getLogger(BioTekBackend.class);

    private Plate currentPlate;

    /**
     * Initializes the BioTek washer hardware.
     * @throws MachineException if initialization fails
     */
    @Override
    public void setup() throws MachineException {
        log.info("Setting up BioTek washer (stub)");
        this.setupFinished = true;
    }

    /**
     * Shuts down the BioTek washer.
     * @throws MachineException if shutdown fails
     */
    @Override
    public void stop() throws MachineException {
        log.info("Stopping BioTek washer");
        this.setupFinished = false;
    }

    /**
     * Loads a plate into the washer.
     * @param plate the plate to load
     * @throws MachineException if the operation fails
     */
    @Override
    public void loadPlate(Plate plate) throws MachineException {
        log.info("Loading plate: {}", plate.getName());
        this.currentPlate = plate;
    }

    /**
     * Unloads the current plate.
     * @throws MachineException if the operation fails
     */
    @Override
    public void unloadPlate() throws MachineException {
        log.info("Unloading plate");
        this.currentPlate = null;
    }

    /**
     * Washes the loaded plate with specified cycles.
     * @param cycles the number of wash cycles
     * @param volumeMl the wash volume per cycle in milliliters
     * @throws MachineException if the operation fails
     */
    @Override
    public void wash(int cycles, double volumeMl) throws MachineException {
        log.info("Washing {} cycles with {}mL per cycle", cycles, volumeMl);
        for (int i = 1; i <= cycles; i++) {
            log.debug("Wash cycle {}/{}", i, cycles);
            dispense(volumeMl);
            aspirate();
        }
    }

    /**
     * Aspirates liquid from the loaded plate.
     * @throws MachineException if the operation fails
     */
    @Override
    public void aspirate() throws MachineException {
        log.debug("Aspirating wells");
    }

    /**
     * Dispenses liquid to the loaded plate.
     * @param volumeMl the volume to dispense in milliliters
     * @throws MachineException if the operation fails
     */
    @Override
    public void dispense(double volumeMl) throws MachineException {
        log.debug("Dispensing {}mL", volumeMl);
    }

    /**
     * Primes the washer lines.
     * @throws MachineException if the operation fails
     */
    @Override
    public void prime() throws MachineException {
        log.info("Priming washer lines");
    }
}
