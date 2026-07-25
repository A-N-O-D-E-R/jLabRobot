package io.jlabrobot.backend.chatterbox;

import io.jlabrobot.backend.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simulation backend for testing and development purposes.
 *
 * Provides a mock implementation of the Backend interface that logs all operations
 * without requiring actual hardware. Useful for protocol development, testing,
 * and verifying command flows in isolation.
 *
 * All commands are immediately successful and return simulated responses.
 */
public class ChatterboxBackend implements Backend {
    private static final Logger log = LoggerFactory.getLogger(ChatterboxBackend.class);

    private boolean initialized = false;

    /**
     * Initializes the Chatterbox simulation backend.
     *
     * Marks the backend as ready to execute commands. No actual hardware initialization occurs.
     *
     * @throws BackendException never thrown in this implementation
     */
    @Override
    public void initialize() throws BackendException {
        log.info("🤖 [CHATTERBOX] Initializing simulation backend");
        this.initialized = true;
    }

    /**
     * Executes a command in simulation mode.
     *
     * Logs the command and parameters, then immediately returns a success result.
     * No actual hardware communication occurs.
     *
     * @param cmd the command to simulate
     * @return a success result with a simulated response message
     * @throws BackendException if the backend is not initialized
     */
    @Override
    public CommandResult executeCommand(Command cmd) throws BackendException {
        if (!initialized) {
            throw new BackendException("Backend not initialized");
        }

        log.info("🤖 [CHATTERBOX] Command: {} with params: {}",
            cmd.name(), cmd.parameters());

        return CommandResult.success("Simulated " + cmd.name());
    }

    /**
     * Shuts down the Chatterbox simulation backend.
     *
     * Marks the backend as no longer available for command execution.
     */
    @Override
    public void shutdown() {
        log.info("🤖 [CHATTERBOX] Shutting down simulation backend");
        this.initialized = false;
    }
}
