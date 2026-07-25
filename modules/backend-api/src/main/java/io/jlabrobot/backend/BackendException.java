package io.jlabrobot.backend;

/**
 * Exception thrown when backend initialization, command execution, or shutdown fails.
 *
 * Wraps hardware-specific errors, communication failures, and protocol violations
 * with a consistent exception type across all backend implementations.
 */
public class BackendException extends Exception {
    /**
     * Constructs a BackendException with a message.
     *
     * @param message a description of the error
     */
    public BackendException(String message) {
        super(message);
    }

    /**
     * Constructs a BackendException with a message and underlying cause.
     *
     * @param message a description of the error
     * @param cause the underlying exception that triggered this error
     */
    public BackendException(String message, Throwable cause) {
        super(message, cause);
    }
}
