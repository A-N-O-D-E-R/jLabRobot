package io.jlabrobot.pump;

import io.jlabrobot.machines.MachineBackend;
import io.jlabrobot.machines.MachineException;

/**
 * Abstract backend for peristaltic pump hardware.
 * Implementations handle communication with specific pump brands.
 */
public abstract class PumpBackend extends MachineBackend {
    /**
     * Sets the flow rate for pumping.
     * @param mlPerMinute the flow rate in milliliters per minute
     * @throws MachineException if the operation fails
     */
    public abstract void setFlowRate(double mlPerMinute) throws MachineException;

    /**
     * Starts pumping at the current flow rate.
     * @throws MachineException if the operation fails
     */
    public abstract void startPumping() throws MachineException;

    /**
     * Stops pumping.
     * @throws MachineException if the operation fails
     */
    public abstract void stopPumping() throws MachineException;

    /**
     * Dispenses a specific volume.
     * @param volumeMl the volume to dispense in milliliters
     * @throws MachineException if the operation fails
     */
    public abstract void dispense(double volumeMl) throws MachineException;
}
