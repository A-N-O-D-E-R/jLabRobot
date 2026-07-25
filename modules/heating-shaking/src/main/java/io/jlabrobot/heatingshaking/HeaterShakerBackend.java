package io.jlabrobot.heatingshaking;

import io.jlabrobot.machines.MachineBackend;
import io.jlabrobot.machines.MachineException;

/**
 * Abstract backend for heater-shaker hardware supporting heating and shaking operations.
 * Implementations handle communication with specific heater-shaker brands.
 */
public abstract class HeaterShakerBackend extends MachineBackend {
    /**
     * Sets the plate temperature.
     * @param celsius the target temperature in degrees Celsius
     * @throws MachineException if the operation fails
     */
    public abstract void setTemperature(double celsius) throws MachineException;

    /**
     * Gets the current plate temperature.
     * @return the current temperature in degrees Celsius
     * @throws MachineException if the operation fails
     */
    public abstract double getTemperature() throws MachineException;

    /**
     * Starts the shaker at the specified speed.
     * @param rpm the rotation speed in revolutions per minute
     * @throws MachineException if the operation fails
     */
    public abstract void startShaking(double rpm) throws MachineException;

    /**
     * Stops the shaker.
     * @throws MachineException if the operation fails
     */
    public abstract void stopShaking() throws MachineException;

    /**
     * Checks if this device supports active cooling.
     * @return true if active cooling is supported
     */
    public abstract boolean supportsActiveCooling();

    /**
     * Checks if this device supports plate locking during operation.
     * @return true if plate locking is supported
     */
    public abstract boolean supportsLocking();
}
