package io.jlabrobot.centrifuge;

import io.jlabrobot.machines.Machine;
import io.jlabrobot.machines.MachineException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controls centrifuge operations including door management and spinning.
 * Manages acceleration profiles and g-force settings for sample separation.
 */
public class Centrifuge extends Machine {
    private static final Logger log = LoggerFactory.getLogger(Centrifuge.class);

    /**
     * Constructs a Centrifuge with a specified backend.
     * @param backend the centrifuge backend
     */
    public Centrifuge(CentrifugeBackend backend) {
        super(backend);
    }

    /**
     * Opens the centrifuge door.
     * @throws MachineException if the machine is not set up or operation fails
     */
    public void openDoor() throws MachineException {
        requireSetup();
        log.info("Opening door");
        ((CentrifugeBackend) backend).openDoor();
    }

    /**
     * Closes the centrifuge door.
     * @throws MachineException if the machine is not set up or operation fails
     */
    public void closeDoor() throws MachineException {
        requireSetup();
        log.info("Closing door");
        ((CentrifugeBackend) backend).closeDoor();
    }

    /**
     * Locks the door to prevent opening during operation.
     * @throws MachineException if the machine is not set up or operation fails
     */
    public void lockDoor() throws MachineException {
        requireSetup();
        ((CentrifugeBackend) backend).lockDoor();
    }

    /**
     * Spins the centrifuge at a specified g-force for a specified duration.
     * @param gForce the relative centrifugal force (g-force)
     * @param durationMs the duration in milliseconds
     * @param acceleration the acceleration profile (0.0-1.0)
     * @throws MachineException if the machine is not set up or operation fails
     */
    public void spin(double gForce, long durationMs, double acceleration) throws MachineException {
        requireSetup();
        log.info("Spinning at {}g for {}ms", gForce, durationMs);
        ((CentrifugeBackend) backend).spin(gForce, durationMs, acceleration);
    }
}
