package io.jlabrobot.centrifuge;

import io.jlabrobot.machines.MachineBackend;
import io.jlabrobot.machines.MachineException;

/**
 * Abstract backend for centrifuge hardware supporting spinning and door management.
 * Implementations handle communication with specific centrifuge brands.
 */
public abstract class CentrifugeBackend extends MachineBackend {
    /**
     * Opens the centrifuge door.
     * @throws MachineException if the operation fails
     */
    public abstract void openDoor() throws MachineException;

    /**
     * Closes the centrifuge door.
     * @throws MachineException if the operation fails
     */
    public abstract void closeDoor() throws MachineException;

    /**
     * Locks the door to prevent opening during operation.
     * @throws MachineException if the operation fails
     */
    public abstract void lockDoor() throws MachineException;

    /**
     * Spins the centrifuge at a specified g-force.
     * @param gForce the relative centrifugal force (g-force)
     * @param durationMs the duration in milliseconds
     * @param acceleration the acceleration profile (0.0-1.0)
     * @throws MachineException if the operation fails
     */
    public abstract void spin(double gForce, long durationMs, double acceleration) throws MachineException;

    /**
     * Stops the centrifuge.
     * @throws MachineException if the operation fails
     */
    public abstract void stop() throws MachineException;
}
