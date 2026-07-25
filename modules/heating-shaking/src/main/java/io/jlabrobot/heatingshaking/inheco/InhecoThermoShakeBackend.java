package io.jlabrobot.heatingshaking.inheco;

import io.jlabrobot.heatingshaking.HeaterShakerBackend;
import io.jlabrobot.machines.MachineException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Backend implementation for Inheco ThermoShake heater-shaker.
 * Supports heating, shaking, active cooling, and plate locking.
 */
public class InhecoThermoShakeBackend extends HeaterShakerBackend {
    private static final Logger log = LoggerFactory.getLogger(InhecoThermoShakeBackend.class);

    private double currentTemp = 25.0;
    private boolean shaking = false;

    /**
     * Initializes the ThermoShake hardware.
     * @throws MachineException if initialization fails
     */
    @Override
    public void setup() throws MachineException {
        log.info("Setting up Inheco ThermoShake (stub)");
        this.setupFinished = true;
    }

    /**
     * Sets the plate temperature.
     * @param celsius the target temperature in degrees Celsius
     * @throws MachineException if the operation fails
     */
    @Override
    public void setTemperature(double celsius) throws MachineException {
        log.info("Setting temperature to {}°C", celsius);
        this.currentTemp = celsius;
    }

    /**
     * Gets the current temperature setting.
     * @return the current temperature in degrees Celsius
     * @throws MachineException if the operation fails
     */
    @Override
    public double getTemperature() throws MachineException {
        return currentTemp;
    }

    /**
     * Starts the shaker.
     * @param rpm the rotation speed in revolutions per minute
     * @throws MachineException if the operation fails
     */
    @Override
    public void startShaking(double rpm) throws MachineException {
        log.info("Starting shaking at {} RPM", rpm);
        this.shaking = true;
    }

    /**
     * Stops the shaker.
     * @throws MachineException if the operation fails
     */
    @Override
    public void stopShaking() throws MachineException {
        log.info("Stopping shaking");
        this.shaking = false;
    }

    /**
     * Checks if active cooling is supported.
     * @return true as ThermoShake supports active cooling
     */
    @Override
    public boolean supportsActiveCooling() {
        return true;
    }

    /**
     * Checks if plate locking is supported.
     * @return true as ThermoShake supports plate locking
     */
    @Override
    public boolean supportsLocking() {
        return true;
    }

    /**
     * Shuts down the ThermoShake hardware.
     * @throws MachineException if shutdown fails
     */
    @Override
    public void stop() throws MachineException {
        log.info("Stopping Inheco ThermoShake");
        this.setupFinished = false;
    }
}
