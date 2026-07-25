package io.jlabrobot.machines;

/**
 * Exception thrown when a machine operation fails or is in an invalid state.
 */
public class MachineException extends Exception {
    /**
     * Constructs a MachineException with a descriptive message.
     * @param message the error message
     */
    public MachineException(String message) {
        super(message);
    }

    /**
     * Constructs a MachineException with a message and underlying cause.
     * @param message the error message
     * @param cause the underlying exception
     */
    public MachineException(String message, Throwable cause) {
        super(message, cause);
    }
}
