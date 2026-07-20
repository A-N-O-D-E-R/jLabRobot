package io.jlabrobot.machines;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Machine {
    private static final Logger log = LoggerFactory.getLogger(Machine.class);
    
    protected final MachineBackend backend;
    private boolean setupFinished = false;
    
    protected Machine(MachineBackend backend) {
        this.backend = backend;
    }
    
    public void setup() throws MachineException {
        log.info("Setting up {}", this.getClass().getSimpleName());
        backend.setup();
        this.setupFinished = true;
    }
    
    public void stop() throws MachineException {
        backend.stop();
        this.setupFinished = false;
    }
    
    protected void requireSetup() throws MachineException {
        if (!setupFinished) {
            throw new MachineException("Machine not set up. Call setup() first.");
        }
    }
}
