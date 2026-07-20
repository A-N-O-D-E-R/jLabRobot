package io.jlabrobot.backend.chatterbox;

import io.jlabrobot.backend.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChatterboxBackend implements Backend {
    private static final Logger log = LoggerFactory.getLogger(ChatterboxBackend.class);
    
    private boolean initialized = false;
    
    @Override
    public void initialize() throws BackendException {
        log.info("🤖 [CHATTERBOX] Initializing simulation backend");
        this.initialized = true;
    }
    
    @Override
    public CommandResult executeCommand(Command cmd) throws BackendException {
        if (!initialized) {
            throw new BackendException("Backend not initialized");
        }
        
        log.info("🤖 [CHATTERBOX] Command: {} with params: {}", 
            cmd.name(), cmd.parameters());
        
        return CommandResult.success("Simulated " + cmd.name());
    }
    
    @Override
    public void shutdown() {
        log.info("🤖 [CHATTERBOX] Shutting down simulation backend");
        this.initialized = false;
    }
}
