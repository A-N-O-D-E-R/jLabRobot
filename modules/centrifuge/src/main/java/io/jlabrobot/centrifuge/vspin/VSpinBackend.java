package io.jlabrobot.centrifuge.vspin;

import io.jlabrobot.centrifuge.CentrifugeBackend;
import io.jlabrobot.machines.MachineException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Backend implementation for Agilent VSpin centrifuge.
 * Supports variable g-force spinning with door locking.
 */
public class VSpinBackend extends CentrifugeBackend {
    private static final Logger log = LoggerFactory.getLogger(VSpinBackend.class);

    private boolean doorOpen = false;

    /**
     * Initializes the VSpin centrifuge hardware.
     * @throws MachineException if initialization fails
     */
    @Override
    public void setup() throws MachineException {
        log.info("Setting up Agilent VSpin (stub)");
        this.setupFinished = true;
    }

    /**
     * Opens the centrifuge door.
     * @throws MachineException if the operation fails
     */
    @Override
    public void openDoor() throws MachineException {
        log.info("Opening door");
        this.doorOpen = true;
    }

    /**
     * Closes the centrifuge door.
     * @throws MachineException if the operation fails
     */
    @Override
    public void closeDoor() throws MachineException {
        log.info("Closing door");
        this.doorOpen = false;
    }

    /**
     * Locks the door.
     * @throws MachineException if the operation fails
     */
    @Override
    public void lockDoor() throws MachineException {
        log.info("Locking door");
    }

    /**
     * Spins at the specified g-force and duration.
     * @param gForce the relative centrifugal force (g-force)
     * @param durationMs the duration in milliseconds
     * @param acceleration the acceleration profile (0.0-1.0)
     * @throws MachineException if door is open or operation fails
     */
    @Override
    public void spin(double gForce, long durationMs, double acceleration) throws MachineException {
        if (doorOpen) {
            throw new MachineException("Cannot spin with door open");
        }
        log.info("Spinning at {}g for {}ms (acceleration: {})", gForce, durationMs, acceleration);
    }

    /**
     * Shuts down the VSpin centrifuge.
     * @throws MachineException if shutdown fails
     */
    @Override
    public void stop() throws MachineException {
        log.info("Stopping VSpin");
        this.setupFinished = false;
    }
}
