package io.jlabrobot.incubator.cytomat;

import io.jlabrobot.incubator.IncubatorBackend;
import io.jlabrobot.machines.MachineException;
import io.jlabrobot.resources.Plate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Backend implementation for Cytomat incubator.
 * Supports multi-slot plate storage with temperature, CO2, and humidity control.
 */
public class CytomatBackend extends IncubatorBackend {
    private static final Logger log = LoggerFactory.getLogger(CytomatBackend.class);

    private double temperature = 37.0;
    private double co2 = 5.0;
    private double humidity = 95.0;
    private final Map<Integer, Plate> slots = new HashMap<>();

    /**
     * Initializes the Cytomat incubator hardware.
     * @throws MachineException if initialization fails
     */
    @Override
    public void setup() throws MachineException {
        log.info("Setting up Cytomat incubator (stub)");
        this.setupFinished = true;
    }

    /**
     * Shuts down the Cytomat incubator.
     * @throws MachineException if shutdown fails
     */
    @Override
    public void stop() throws MachineException {
        log.info("Stopping Cytomat incubator");
        slots.clear();
        this.setupFinished = false;
    }

    /**
     * Sets the chamber temperature.
     * @param celsius the target temperature in degrees Celsius
     * @throws MachineException if the operation fails
     */
    @Override
    public void setTemperature(double celsius) throws MachineException {
        log.info("Setting temperature: {}°C", celsius);
        this.temperature = celsius;
    }

    /**
     * Sets the CO2 concentration.
     * @param percent the target CO2 concentration as a percentage
     * @throws MachineException if the operation fails
     */
    @Override
    public void setCO2(double percent) throws MachineException {
        log.info("Setting CO2: {}%", percent);
        this.co2 = percent;
    }

    /**
     * Sets the humidity.
     * @param percent the target humidity as a percentage
     * @throws MachineException if the operation fails
     */
    @Override
    public void setHumidity(double percent) throws MachineException {
        log.info("Setting humidity: {}%", percent);
        this.humidity = percent;
    }

    /**
     * Opens the incubator door.
     * @throws MachineException if the operation fails
     */
    @Override
    public void openDoor() throws MachineException {
        log.debug("Opening door");
    }

    /**
     * Closes the incubator door.
     * @throws MachineException if the operation fails
     */
    @Override
    public void closeDoor() throws MachineException {
        log.debug("Closing door");
    }

    /**
     * Loads a plate into the specified slot.
     * @param plate the plate to load
     * @param slot the slot index
     * @throws MachineException if the operation fails
     */
    @Override
    public void loadPlate(Plate plate, int slot) throws MachineException {
        log.info("Loading plate {} into slot {}", plate.getName(), slot);
        slots.put(slot, plate);
    }

    /**
     * Unloads a plate from the specified slot.
     * @param slot the slot index
     * @throws MachineException if the operation fails
     */
    @Override
    public void unloadPlate(int slot) throws MachineException {
        log.info("Unloading plate from slot {}", slot);
        slots.remove(slot);
    }

    /**
     * Gets the current temperature setting.
     * @return the temperature in degrees Celsius
     * @throws MachineException if the operation fails
     */
    @Override
    public double getTemperature() throws MachineException {
        return temperature;
    }

    /**
     * Gets the current CO2 concentration setting.
     * @return the CO2 concentration as a percentage
     * @throws MachineException if the operation fails
     */
    @Override
    public double getCO2() throws MachineException {
        return co2;
    }

    /**
     * Gets the current humidity setting.
     * @return the humidity as a percentage
     * @throws MachineException if the operation fails
     */
    @Override
    public double getHumidity() throws MachineException {
        return humidity;
    }
}
