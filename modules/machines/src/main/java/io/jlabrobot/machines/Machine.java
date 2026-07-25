package io.jlabrobot.machines;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract base class for all laboratory equipment machines.
 * Manages machine lifecycle including setup, operation, and shutdown.
 */
public abstract class Machine {
    private static final Logger log = LoggerFactory.getLogger(Machine.class);

    protected final MachineBackend backend;
    private boolean setupFinished = false;

    /**
     * Constructs a Machine with a specified backend.
     * @param backend the backend for hardware communication
     */
    protected Machine(MachineBackend backend) {
        this.backend = backend;
    }

    /**
     * Initializes and sets up the machine for operation.
     * @throws MachineException if setup fails
     */
    public void setup() throws MachineException {
        log.info("Setting up {}", this.getClass().getSimpleName());
        backend.setup();
        this.setupFinished = true;
    }

    /**
     * Stops the machine and cleans up resources.
     * @throws MachineException if shutdown fails
     */
    public void stop() throws MachineException {
        backend.stop();
        this.setupFinished = false;
    }

    /**
     * Verifies that the machine has been set up before allowing operations.
     * @throws MachineException if machine is not set up
     */
    protected void requireSetup() throws MachineException {
        if (!setupFinished) {
            throw new MachineException("Machine not set up. Call setup() first.");
        }
    }
}
