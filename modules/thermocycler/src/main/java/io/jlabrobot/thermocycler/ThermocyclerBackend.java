package io.jlabrobot.thermocycler;

import io.jlabrobot.machines.MachineBackend;
import io.jlabrobot.machines.MachineException;

/**
 * Abstract backend for thermocycler hardware supporting PCR and thermal cycling.
 * Implementations handle communication with specific thermocycler brands.
 */
public abstract class ThermocyclerBackend extends MachineBackend {

    /**
     * Sets the lid temperature.
     * @param celsius the target temperature in degrees Celsius
     * @throws MachineException if the operation fails
     */
    public abstract void setLidTemperature(double celsius) throws MachineException;

    /**
     * Sets the sample block temperature.
     * @param celsius the target temperature in degrees Celsius
     * @throws MachineException if the operation fails
     */
    public abstract void setBlockTemperature(double celsius) throws MachineException;

    /**
     * Opens the thermocycler lid.
     * @throws MachineException if the operation fails
     */
    public abstract void openLid() throws MachineException;

    /**
     * Closes the thermocycler lid.
     * @throws MachineException if the operation fails
     */
    public abstract void closeLid() throws MachineException;

    /**
     * Deactivates the lid heater.
     * @throws MachineException if the operation fails
     */
    public abstract void deactivateLid() throws MachineException;

    /**
     * Deactivates the block heater.
     * @throws MachineException if the operation fails
     */
    public abstract void deactivateBlock() throws MachineException;

    /**
     * Gets the current lid temperature.
     * @return the temperature in degrees Celsius
     * @throws MachineException if the operation fails
     */
    public abstract double getLidTemperature() throws MachineException;

    /**
     * Gets the current block temperature.
     * @return the temperature in degrees Celsius
     * @throws MachineException if the operation fails
     */
    public abstract double getBlockTemperature() throws MachineException;

    /**
     * Runs a thermal cycle at the specified temperature.
     * @param cycles the number of cycles
     * @param temp the temperature in degrees Celsius
     * @param holdTimeMs the hold duration in milliseconds
     * @throws MachineException if the operation fails
     */
    public abstract void runCycle(int cycles, double temp, long holdTimeMs) throws MachineException;
}
