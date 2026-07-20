package io.jlabrobot.heatingshaking;

import io.jlabrobot.machines.Machine;
import io.jlabrobot.machines.MachineException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HeaterShaker extends Machine {
    private static final Logger log = LoggerFactory.getLogger(HeaterShaker.class);
    
    public HeaterShaker(HeaterShakerBackend backend) {
        super(backend);
    }
    
    public void setTemperature(double celsius) throws MachineException {
        requireSetup();
        log.info("Setting temperature to {}°C", celsius);
        ((HeaterShakerBackend) backend).setTemperature(celsius);
    }
    
    public double getTemperature() throws MachineException {
        requireSetup();
        return ((HeaterShakerBackend) backend).getTemperature();
    }
    
    public void startShaking(double rpm) throws MachineException {
        requireSetup();
        log.info("Starting shaking at {} RPM", rpm);
        ((HeaterShakerBackend) backend).startShaking(rpm);
    }
    
    public void stopShaking() throws MachineException {
        requireSetup();
        log.info("Stopping shaking");
        ((HeaterShakerBackend) backend).stopShaking();
    }
    
    public void shake(double rpm, long durationMs) throws MachineException, InterruptedException {
        requireSetup();
        startShaking(rpm);
        Thread.sleep(durationMs);
        stopShaking();
    }
}
