package io.jlabrobot.protocol;

/**
 * Exception thrown when a protocol communication error occurs.
 *
 * <p>This is the base exception for all protocol-related failures, including connection errors,
 * transmission failures, timeout violations, and protocol violations. Applications should catch
 * this exception to handle communication issues gracefully.
 */
public class ProtocolException extends Exception {
    /**
     * Constructs a ProtocolException with the specified error message.
     *
     * @param message a descriptive message explaining the protocol error
     */
    public ProtocolException(String message) {
        super(message);
    }

    /**
     * Constructs a ProtocolException with the specified error message and root cause.
     *
     * @param message a descriptive message explaining the protocol error
     * @param cause the underlying exception that triggered this error
     */
    public ProtocolException(String message, Throwable cause) {
        super(message, cause);
    }
}
