package io.jlabrobot.washer;

import io.jlabrobot.machines.MachineBackend;
import io.jlabrobot.machines.MachineException;
import io.jlabrobot.resources.Plate;

/**
 * Abstract backend for plate washer hardware supporting wash cycles and priming.
 * Implementations handle communication with specific washer brands.
 */
public abstract class WasherBackend extends MachineBackend {

    /**
     * Loads a plate into the washer.
     * @param plate the plate to load
     * @throws MachineException if the operation fails
     */
    public abstract void loadPlate(Plate plate) throws MachineException;

    /**
     * Unloads the current plate from the washer.
     * @throws MachineException if the operation fails
     */
    public abstract void unloadPlate() throws MachineException;

    /**
     * Washes the loaded plate.
     * @param cycles the number of wash cycles
     * @param volumeMl the wash volume per cycle in milliliters
     * @throws MachineException if the operation fails
     */
    public abstract void wash(int cycles, double volumeMl) throws MachineException;

    /**
     * Aspirates liquid from the loaded plate.
     * @throws MachineException if the operation fails
     */
    public abstract void aspirate() throws MachineException;

    /**
     * Dispenses liquid to the loaded plate.
     * @param volumeMl the volume to dispense in milliliters
     * @throws MachineException if the operation fails
     */
    public abstract void dispense(double volumeMl) throws MachineException;

    /**
     * Primes the washer pumps and lines.
     * @throws MachineException if the operation fails
     */
    public abstract void prime() throws MachineException;
}
