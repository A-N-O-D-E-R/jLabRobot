package io.jlabrobot.scale;

import io.jlabrobot.machines.Machine;
import io.jlabrobot.machines.MachineException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Scale extends Machine {
    private static final Logger log = LoggerFactory.getLogger(Scale.class);
    
    public Scale(ScaleBackend backend) {
        super(backend);
    }
    
    public void zero() throws MachineException {
        requireSetup();
        log.info("Zeroing scale");
        ((ScaleBackend) backend).zero();
    }
    
    public void tare() throws MachineException {
        requireSetup();
        log.info("Taring scale");
        ((ScaleBackend) backend).tare();
    }
    
    public double readWeight() throws MachineException {
        requireSetup();
        double weight = ((ScaleBackend) backend).readWeight();
        log.info("Weight: {} g", weight);
        return weight;
    }
}
