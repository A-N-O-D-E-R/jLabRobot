package io.jlabrobot.machines;

public abstract class MachineBackend {
    protected boolean setupFinished = false;
    
    public abstract void setup() throws MachineException;
    public abstract void stop() throws MachineException;
    
    public boolean isSetupFinished() {
        return setupFinished;
    }
}
