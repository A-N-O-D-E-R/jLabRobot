package io.jlabrobot.incubator;

import io.jlabrobot.machines.Machine;
import io.jlabrobot.machines.MachineException;
import io.jlabrobot.resources.Plate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controls incubator operations for cell culture including temperature, CO2, and humidity.
 * Manages multiple slots for simultaneous incubation of multiple plates.
 */
public class Incubator extends Machine {
    private static final Logger log = LoggerFactory.getLogger(Incubator.class);

    private final int numSlots;

    /**
     * Constructs an Incubator with a specified backend and number of slots.
     * @param backend the incubator backend
     * @param numSlots the number of plate slots available
     */
    public Incubator(IncubatorBackend backend, int numSlots) {
        super(backend);
        this.numSlots = numSlots;
    }

    /**
     * Sets the incubator chamber temperature.
     * @param celsius the target temperature in degrees Celsius
     * @throws MachineException if the machine is not set up or operation fails
     */
    public void setTemperature(double celsius) throws MachineException {
        requireSetup();
        log.info("Setting temperature to {}°C", celsius);
        ((IncubatorBackend) backend).setTemperature(celsius);
    }

    /**
     * Sets the CO2 concentration in the incubator chamber.
     * @param percent the target CO2 concentration as a percentage
     * @throws MachineException if the machine is not set up or operation fails
     */
    public void setCO2(double percent) throws MachineException {
        requireSetup();
        log.info("Setting CO2 to {}%", percent);
        ((IncubatorBackend) backend).setCO2(percent);
    }

    /**
     * Sets the humidity in the incubator chamber.
     * @param percent the target humidity as a percentage
     * @throws MachineException if the machine is not set up or operation fails
     */
    public void setHumidity(double percent) throws MachineException {
        requireSetup();
        log.info("Setting humidity to {}%", percent);
        ((IncubatorBackend) backend).setHumidity(percent);
    }

    /**
     * Loads a plate into the specified slot.
     * @param plate the plate to load
     * @param slot the slot index (0-based)
     * @throws MachineException if the machine is not set up, slot is invalid, or operation fails
     */
    public void loadPlate(Plate plate, int slot) throws MachineException {
        requireSetup();
        if (slot < 0 || slot >= numSlots) {
            throw new MachineException("Invalid slot: " + slot + " (must be 0-" + (numSlots - 1) + ")");
        }
        log.info("Loading plate {} into slot {}", plate.getName(), slot);
        ((IncubatorBackend) backend).openDoor();
        ((IncubatorBackend) backend).loadPlate(plate, slot);
        ((IncubatorBackend) backend).closeDoor();
    }

    /**
     * Unloads a plate from the specified slot.
     * @param slot the slot index (0-based)
     * @throws MachineException if the machine is not set up, slot is invalid, or operation fails
     */
    public void unloadPlate(int slot) throws MachineException {
        requireSetup();
        if (slot < 0 || slot >= numSlots) {
            throw new MachineException("Invalid slot: " + slot);
        }
        log.info("Unloading plate from slot {}", slot);
        ((IncubatorBackend) backend).openDoor();
        ((IncubatorBackend) backend).unloadPlate(slot);
        ((IncubatorBackend) backend).closeDoor();
    }

    /**
     * Incubates a plate at specified environmental conditions for a duration.
     * @param plate the plate to incubate
     * @param slot the slot index (0-based)
     * @param tempCelsius the incubation temperature in degrees Celsius
     * @param co2Percent the CO2 concentration as a percentage
     * @param durationMs the incubation duration in milliseconds
     * @throws MachineException if the machine is not set up, slot is invalid, or operation fails
     * @throws InterruptedException if the incubation is interrupted
     */
    public void incubate(Plate plate, int slot, double tempCelsius, double co2Percent, long durationMs)
            throws MachineException, InterruptedException {
        requireSetup();
        log.info("Incubating {} at {}°C, {}% CO2 for {}ms", plate.getName(), tempCelsius, co2Percent, durationMs);

        setTemperature(tempCelsius);
        setCO2(co2Percent);
        loadPlate(plate, slot);

        Thread.sleep(durationMs);

        unloadPlate(slot);
    }

    /**
     * Gets the current chamber temperature.
     * @return the temperature in degrees Celsius
     * @throws MachineException if the machine is not set up or operation fails
     */
    public double getTemperature() throws MachineException {
        requireSetup();
        return ((IncubatorBackend) backend).getTemperature();
    }

    /**
     * Gets the current CO2 concentration.
     * @return the CO2 concentration as a percentage
     * @throws MachineException if the machine is not set up or operation fails
     */
    public double getCO2() throws MachineException {
        requireSetup();
        return ((IncubatorBackend) backend).getCO2();
    }

    /**
     * Gets the number of available slots.
     * @return the number of slots
     */
    public int getNumSlots() {
        return numSlots;
    }
}
