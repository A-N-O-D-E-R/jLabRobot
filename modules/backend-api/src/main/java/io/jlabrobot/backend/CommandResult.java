package io.jlabrobot.backend;

/**
 * Result of a command execution on a hardware backend.
 *
 * Captures the outcome of a command including success/failure status, descriptive message,
 * and optional response data from the device. Immutable record with factory methods for
 * convenient construction.
 *
 * @param success true if the command executed successfully, false otherwise
 * @param message a human-readable message describing the result or error
 * @param data optional command-specific response data (e.g., parsed sensor readings)
 */
public record CommandResult(boolean success, String message, Object data) {
    /**
     * Factory method to create a successful CommandResult without response data.
     *
     * @param message a status message describing the successful outcome
     * @return a CommandResult with success=true
     */
    public static CommandResult success(String message) {
        return new CommandResult(true, message, null);
    }

    /**
     * Factory method to create a successful CommandResult with response data.
     *
     * @param message a status message describing the successful outcome
     * @param data the command response data returned from the device
     * @return a CommandResult with success=true and populated data
     */
    public static CommandResult success(String message, Object data) {
        return new CommandResult(true, message, data);
    }

    /**
     * Factory method to create a failed CommandResult.
     *
     * @param message an error message describing the failure
     * @return a CommandResult with success=false
     */
    public static CommandResult failure(String message) {
        return new CommandResult(false, message, null);
    }
}
