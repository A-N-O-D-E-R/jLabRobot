package io.jlabrobot.washer;

import io.jlabrobot.machines.Machine;
import io.jlabrobot.machines.MachineException;
import io.jlabrobot.resources.Plate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controls plate washing operations for multi-well plate cleaning.
 * Manages plate loading/unloading and washing cycles with priming capability.
 */
public class Washer extends Machine {
    private static final Logger log = LoggerFactory.getLogger(Washer.class);

    private Plate currentPlate;

    /**
     * Constructs a Washer with a specified backend.
     * @param backend the washer backend
     */
    public Washer(WasherBackend backend) {
        super(backend);
    }

    /**
     * Loads a plate into the washer.
     * @param plate the plate to load
     * @throws MachineException if the machine is not set up or operation fails
     */
    public void loadPlate(Plate plate) throws MachineException {
        requireSetup();
        log.info("Loading plate: {}", plate.getName());
        ((WasherBackend) backend).loadPlate(plate);
        this.currentPlate = plate;
    }

    /**
     * Unloads the current plate from the washer.
     * @throws MachineException if the machine is not set up, no plate is loaded, or operation fails
     */
    public void unloadPlate() throws MachineException {
        requireSetup();
        if (currentPlate == null) {
            throw new MachineException("No plate loaded");
        }
        log.info("Unloading plate: {}", currentPlate.getName());
        ((WasherBackend) backend).unloadPlate();
        this.currentPlate = null;
    }

    /**
     * Washes the loaded plate.
     * @param cycles the number of wash cycles
     * @param volumeMl the wash volume per cycle in milliliters
     * @throws MachineException if the machine is not set up, no plate is loaded, or operation fails
     */
    public void wash(int cycles, double volumeMl) throws MachineException {
        requireSetup();
        if (currentPlate == null) {
            throw new MachineException("No plate loaded");
        }
        log.info("Washing {} with {} cycles, {}mL per cycle", currentPlate.getName(), cycles, volumeMl);
        ((WasherBackend) backend).wash(cycles, volumeMl);
    }

    /**
     * Loads a plate, washes it, and unloads it in one operation.
     * @param plate the plate to wash
     * @param cycles the number of wash cycles
     * @param volumeMl the wash volume per cycle in milliliters
     * @throws MachineException if any operation fails
     */
    public void washAndUnload(Plate plate, int cycles, double volumeMl) throws MachineException {
        loadPlate(plate);
        wash(cycles, volumeMl);
        unloadPlate();
    }

    /**
     * Primes the washer pumps and lines.
     * @throws MachineException if the machine is not set up or operation fails
     */
    public void prime() throws MachineException {
        requireSetup();
        log.info("Priming washer");
        ((WasherBackend) backend).prime();
    }
}
