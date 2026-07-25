package io.jlabrobot.machines;

/**
 * Abstract base class for hardware backends that communicate with laboratory machines.
 * Handles low-level hardware operations and communication.
 */
public abstract class MachineBackend {
    protected boolean setupFinished = false;

    /**
     * Initializes the machine hardware.
     * @throws MachineException if hardware initialization fails
     */
    public abstract void setup() throws MachineException;

    /**
     * Shuts down the machine hardware.
     * @throws MachineException if hardware shutdown fails
     */
    public abstract void stop() throws MachineException;

    /**
     * Checks if the machine is initialized and ready for operations.
     * @return true if setup is complete, false otherwise
     */
    public boolean isSetupFinished() {
        return setupFinished;
    }
}
