package io.jlabrobot.backend;

public interface Backend {
    void initialize() throws BackendException;
    CommandResult executeCommand(Command cmd) throws BackendException;
    void shutdown();
}
