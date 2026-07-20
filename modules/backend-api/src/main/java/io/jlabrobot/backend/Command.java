package io.jlabrobot.backend;

import java.util.Map;

public record Command(String name, Map<String, Object> parameters) {
    public Command(String name) {
        this(name, Map.of());
    }
}
