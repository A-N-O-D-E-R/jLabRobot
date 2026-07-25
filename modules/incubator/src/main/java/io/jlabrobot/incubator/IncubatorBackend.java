package io.jlabrobot.incubator;

import io.jlabrobot.machines.MachineBackend;
import io.jlabrobot.machines.MachineException;
import io.jlabrobot.resources.Plate;

/**
 * Abstract backend for incubator hardware supporting temperature, CO2, and humidity control.
 * Implementations handle communication with specific incubator brands.
 */
public abstract class IncubatorBackend extends MachineBackend {

    /**
     * Sets the chamber temperature.
     * @param celsius the target temperature in degrees Celsius
     * @throws MachineException if the operation fails
     */
    public abstract void setTemperature(double celsius) throws MachineException;

    /**
     * Sets the CO2 concentration in the chamber.
     * @param percent the target CO2 concentration as a percentage
     * @throws MachineException if the operation fails
     */
    public abstract void setCO2(double percent) throws MachineException;

    /**
     * Sets the humidity in the chamber.
     * @param percent the target humidity as a percentage
     * @throws MachineException if the operation fails
     */
    public abstract void setHumidity(double percent) throws MachineException;

    /**
     * Opens the incubator door.
     * @throws MachineException if the operation fails
     */
    public abstract void openDoor() throws MachineException;

    /**
     * Closes the incubator door.
     * @throws MachineException if the operation fails
     */
    public abstract void closeDoor() throws MachineException;

    /**
     * Loads a plate into the specified slot.
     * @param plate the plate to load
     * @param slot the slot index
     * @throws MachineException if the operation fails
     */
    public abstract void loadPlate(Plate plate, int slot) throws MachineException;

    /**
     * Unloads a plate from the specified slot.
     * @param slot the slot index
     * @throws MachineException if the operation fails
     */
    public abstract void unloadPlate(int slot) throws MachineException;

    /**
     * Gets the current chamber temperature.
     * @return the temperature in degrees Celsius
     * @throws MachineException if the operation fails
     */
    public abstract double getTemperature() throws MachineException;

    /**
     * Gets the current CO2 concentration.
     * @return the CO2 concentration as a percentage
     * @throws MachineException if the operation fails
     */
    public abstract double getCO2() throws MachineException;

    /**
     * Gets the current humidity.
     * @return the humidity as a percentage
     * @throws MachineException if the operation fails
     */
    public abstract double getHumidity() throws MachineException;
}
