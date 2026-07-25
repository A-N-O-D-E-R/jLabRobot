package io.jlabrobot.heatingshaking;

import io.jlabrobot.machines.Machine;
import io.jlabrobot.machines.MachineException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controls heating and shaking operations on a plate incubator.
 * Supports independent temperature and shaking speed control.
 */
public class HeaterShaker extends Machine {
    private static final Logger log = LoggerFactory.getLogger(HeaterShaker.class);

    /**
     * Constructs a HeaterShaker with a specified backend.
     * @param backend the heater-shaker backend
     */
    public HeaterShaker(HeaterShakerBackend backend) {
        super(backend);
    }

    /**
     * Sets the heating temperature.
     * @param celsius the target temperature in degrees Celsius
     * @throws MachineException if the machine is not set up or operation fails
     */
    public void setTemperature(double celsius) throws MachineException {
        requireSetup();
        log.info("Setting temperature to {}°C", celsius);
        ((HeaterShakerBackend) backend).setTemperature(celsius);
    }

    /**
     * Gets the current temperature setting.
     * @return the current temperature in degrees Celsius
     * @throws MachineException if the machine is not set up or operation fails
     */
    public double getTemperature() throws MachineException {
        requireSetup();
        return ((HeaterShakerBackend) backend).getTemperature();
    }

    /**
     * Starts the shaker at the specified speed.
     * @param rpm the rotation speed in revolutions per minute
     * @throws MachineException if the machine is not set up or operation fails
     */
    public void startShaking(double rpm) throws MachineException {
        requireSetup();
        log.info("Starting shaking at {} RPM", rpm);
        ((HeaterShakerBackend) backend).startShaking(rpm);
    }

    /**
     * Stops the shaker.
     * @throws MachineException if the machine is not set up or operation fails
     */
    public void stopShaking() throws MachineException {
        requireSetup();
        log.info("Stopping shaking");
        ((HeaterShakerBackend) backend).stopShaking();
    }

    /**
     * Shakes the plate for a specified duration.
     * @param rpm the rotation speed in revolutions per minute
     * @param durationMs the duration in milliseconds
     * @throws MachineException if the machine is not set up or operation fails
     * @throws InterruptedException if the thread is interrupted
     */
    public void shake(double rpm, long durationMs) throws MachineException, InterruptedException {
        requireSetup();
        startShaking(rpm);
        Thread.sleep(durationMs);
        stopShaking();
    }
}
