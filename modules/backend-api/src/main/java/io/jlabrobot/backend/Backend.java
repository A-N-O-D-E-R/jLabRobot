package io.jlabrobot.backend;

/**
 * Backend abstraction for robot and device control.
 *
 * Defines the contract that all hardware backends must implement, enabling abstract command
 * execution against different lab automation devices (liquid handlers, centrifuges, etc.)
 * without coupling protocol execution to specific hardware implementations.
 */
public interface Backend {
    /**
     * Initializes the backend and establishes connection to the hardware device.
     *
     * @throws BackendException if initialization fails due to connection issues or device errors
     */
    void initialize() throws BackendException;

    /**
     * Executes a command on the hardware device and returns the result.
     *
     * @param cmd the command to execute, containing name and optional parameters
     * @return the result of command execution including success/failure status and response data
     * @throws BackendException if command execution fails
     */
    CommandResult executeCommand(Command cmd) throws BackendException;

    /**
     * Shuts down the backend and releases hardware resources.
     *
     * Closes connections, cleans up state, and prepares the device for disconnection.
     */
    void shutdown();
}
