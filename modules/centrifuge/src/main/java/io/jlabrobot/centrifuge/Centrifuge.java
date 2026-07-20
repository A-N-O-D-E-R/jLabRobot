package io.jlabrobot.centrifuge;

import io.jlabrobot.machines.Machine;
import io.jlabrobot.machines.MachineException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Centrifuge extends Machine {
    private static final Logger log = LoggerFactory.getLogger(Centrifuge.class);
    
    public Centrifuge(CentrifugeBackend backend) {
        super(backend);
    }
    
    public void openDoor() throws MachineException {
        requireSetup();
        log.info("Opening door");
        ((CentrifugeBackend) backend).openDoor();
    }
    
    public void closeDoor() throws MachineException {
        requireSetup();
        log.info("Closing door");
        ((CentrifugeBackend) backend).closeDoor();
    }
    
    public void lockDoor() throws MachineException {
        requireSetup();
        ((CentrifugeBackend) backend).lockDoor();
    }
    
    public void spin(double gForce, long durationMs, double acceleration) throws MachineException {
        requireSetup();
        log.info("Spinning at {}g for {}ms", gForce, durationMs);
        ((CentrifugeBackend) backend).spin(gForce, durationMs, acceleration);
    }
}
