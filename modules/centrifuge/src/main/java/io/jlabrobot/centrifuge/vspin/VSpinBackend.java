package io.jlabrobot.centrifuge.vspin;

import io.jlabrobot.centrifuge.CentrifugeBackend;
import io.jlabrobot.machines.MachineException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VSpinBackend extends CentrifugeBackend {
    private static final Logger log = LoggerFactory.getLogger(VSpinBackend.class);
    
    private boolean doorOpen = false;
    
    @Override
    public void setup() throws MachineException {
        log.info("Setting up Agilent VSpin (stub)");
        this.setupFinished = true;
    }
    
    @Override
    public void openDoor() throws MachineException {
        log.info("Opening door");
        this.doorOpen = true;
    }
    
    @Override
    public void closeDoor() throws MachineException {
        log.info("Closing door");
        this.doorOpen = false;
    }
    
    @Override
    public void lockDoor() throws MachineException {
        log.info("Locking door");
    }
    
    @Override
    public void spin(double gForce, long durationMs, double acceleration) throws MachineException {
        if (doorOpen) {
            throw new MachineException("Cannot spin with door open");
        }
        log.info("Spinning at {}g for {}ms (acceleration: {})", gForce, durationMs, acceleration);
    }
    
    @Override
    public void stop() throws MachineException {
        log.info("Stopping VSpin");
        this.setupFinished = false;
    }
}
