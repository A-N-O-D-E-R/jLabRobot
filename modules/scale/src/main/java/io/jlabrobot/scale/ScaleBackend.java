package io.jlabrobot.scale;

import io.jlabrobot.machines.MachineBackend;
import io.jlabrobot.machines.MachineException;

/**
 * Abstract backend for precision scale hardware.
 * Implementations handle communication with specific scale brands.
 */
public abstract class ScaleBackend extends MachineBackend {
    /**
     * Zeros the scale to remove any systematic offset.
     * @throws MachineException if the operation fails
     */
    public abstract void zero() throws MachineException;

    /**
     * Tares the scale to account for container or sample weight.
     * @throws MachineException if the operation fails
     */
    public abstract void tare() throws MachineException;

    /**
     * Reads the current weight measurement.
     * @return the weight in grams
     * @throws MachineException if the operation fails
     */
    public abstract double readWeight() throws MachineException;
}
