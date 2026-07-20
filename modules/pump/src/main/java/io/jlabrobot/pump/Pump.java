package io.jlabrobot.pump;

import io.jlabrobot.machines.Machine;
import io.jlabrobot.machines.MachineException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Pump extends Machine {
    private static final Logger log = LoggerFactory.getLogger(Pump.class);
    
    public Pump(PumpBackend backend) {
        super(backend);
    }
    
    public void setFlowRate(double mlPerMinute) throws MachineException {
        requireSetup();
        log.info("Setting flow rate to {} mL/min", mlPerMinute);
        ((PumpBackend) backend).setFlowRate(mlPerMinute);
    }
    
    public void startPumping() throws MachineException {
        requireSetup();
        log.info("Starting pump");
        ((PumpBackend) backend).startPumping();
    }
    
    public void stopPumping() throws MachineException {
        requireSetup();
        log.info("Stopping pump");
        ((PumpBackend) backend).stopPumping();
    }
    
    public void dispense(double volumeMl) throws MachineException {
        requireSetup();
        log.info("Dispensing {} mL", volumeMl);
        ((PumpBackend) backend).dispense(volumeMl);
    }
}
