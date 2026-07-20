package io.jlabrobot.pump.masterflex;

import io.jlabrobot.pump.PumpBackend;
import io.jlabrobot.machines.MachineException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MasterflexBackend extends PumpBackend {
    private static final Logger log = LoggerFactory.getLogger(MasterflexBackend.class);
    
    private double flowRate = 0.0;
    private boolean pumping = false;
    
    @Override
    public void setup() throws MachineException {
        log.info("Setting up Masterflex pump (stub)");
        this.setupFinished = true;
    }
    
    @Override
    public void setFlowRate(double mlPerMinute) throws MachineException {
        log.info("Flow rate set to {} mL/min", mlPerMinute);
        this.flowRate = mlPerMinute;
    }
    
    @Override
    public void startPumping() throws MachineException {
        log.info("Pump started at {} mL/min", flowRate);
        this.pumping = true;
    }
    
    @Override
    public void stopPumping() throws MachineException {
        log.info("Pump stopped");
        this.pumping = false;
    }
    
    @Override
    public void dispense(double volumeMl) throws MachineException {
        if (flowRate <= 0) {
            throw new MachineException("Flow rate not set");
        }
        double durationMin = volumeMl / flowRate;
        log.info("Dispensing {} mL (will take {} minutes)", volumeMl, durationMin);
    }
    
    @Override
    public void stop() throws MachineException {
        log.info("Stopping Masterflex pump");
        this.setupFinished = false;
    }
}
