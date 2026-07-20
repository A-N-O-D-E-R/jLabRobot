package io.jlabrobot.heatingshaking.inheco;

import io.jlabrobot.heatingshaking.HeaterShakerBackend;
import io.jlabrobot.machines.MachineException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InhecoThermoShakeBackend extends HeaterShakerBackend {
    private static final Logger log = LoggerFactory.getLogger(InhecoThermoShakeBackend.class);
    
    private double currentTemp = 25.0;
    private boolean shaking = false;
    
    @Override
    public void setup() throws MachineException {
        log.info("Setting up Inheco ThermoShake (stub)");
        this.setupFinished = true;
    }
    
    @Override
    public void setTemperature(double celsius) throws MachineException {
        log.info("Setting temperature to {}°C", celsius);
        this.currentTemp = celsius;
    }
    
    @Override
    public double getTemperature() throws MachineException {
        return currentTemp;
    }
    
    @Override
    public void startShaking(double rpm) throws MachineException {
        log.info("Starting shaking at {} RPM", rpm);
        this.shaking = true;
    }
    
    @Override
    public void stopShaking() throws MachineException {
        log.info("Stopping shaking");
        this.shaking = false;
    }
    
    @Override
    public boolean supportsActiveCooling() {
        return true;
    }
    
    @Override
    public boolean supportsLocking() {
        return true;
    }
    
    @Override
    public void stop() throws MachineException {
        log.info("Stopping Inheco ThermoShake");
        this.setupFinished = false;
    }
}
