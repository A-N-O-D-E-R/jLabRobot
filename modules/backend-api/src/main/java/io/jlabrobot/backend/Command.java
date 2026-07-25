package io.jlabrobot.backend;

import java.util.Map;

/**
 * Represents a command to be executed on a hardware backend.
 *
 * Encapsulates the command name and its associated parameters for transmission
 * to and execution by hardware devices. Immutable record supporting builder-like
 * construction with optional parameters.
 *
 * @param name the command identifier (e.g., "pick_up_tips", "aspirate")
 * @param parameters optional command parameters as key-value pairs
 */
public record Command(String name, Map<String, Object> parameters) {
    /**
     * Constructs a Command with no parameters.
     *
     * @param name the command identifier
     */
    public Command(String name) {
        this(name, Map.of());
    }
}
