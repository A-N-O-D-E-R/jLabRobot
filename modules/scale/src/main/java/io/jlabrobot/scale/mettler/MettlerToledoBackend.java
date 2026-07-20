package io.jlabrobot.scale.mettler;

import io.jlabrobot.scale.ScaleBackend;
import io.jlabrobot.machines.MachineException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MettlerToledoBackend extends ScaleBackend {
    private static final Logger log = LoggerFactory.getLogger(MettlerToledoBackend.class);
    
    private double tareWeight = 0.0;
    
    @Override
    public void setup() throws MachineException {
        log.info("Setting up Mettler Toledo scale (stub)");
        this.setupFinished = true;
    }
    
    @Override
    public void zero() throws MachineException {
        log.info("Zeroing scale");
        this.tareWeight = 0.0;
    }
    
    @Override
    public void tare() throws MachineException {
        double current = readWeight();
        log.info("Taring scale at {} g", current);
        this.tareWeight = current;
    }
    
    @Override
    public double readWeight() throws MachineException {
        double raw = Math.random() * 100;
        return raw - tareWeight;
    }
    
    @Override
    public void stop() throws MachineException {
        log.info("Stopping Mettler Toledo scale");
        this.setupFinished = false;
    }
}
