package io.jlabrobot.incubator;

import io.jlabrobot.machines.Machine;
import io.jlabrobot.machines.MachineException;
import io.jlabrobot.resources.Plate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Incubator extends Machine {
    private static final Logger log = LoggerFactory.getLogger(Incubator.class);

    private final int numSlots;

    public Incubator(IncubatorBackend backend, int numSlots) {
        super(backend);
        this.numSlots = numSlots;
    }

    public void setTemperature(double celsius) throws MachineException {
        requireSetup();
        log.info("Setting temperature to {}°C", celsius);
        ((IncubatorBackend) backend).setTemperature(celsius);
    }

    public void setCO2(double percent) throws MachineException {
        requireSetup();
        log.info("Setting CO2 to {}%", percent);
        ((IncubatorBackend) backend).setCO2(percent);
    }

    public void setHumidity(double percent) throws MachineException {
        requireSetup();
        log.info("Setting humidity to {}%", percent);
        ((IncubatorBackend) backend).setHumidity(percent);
    }

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

    public double getTemperature() throws MachineException {
        requireSetup();
        return ((IncubatorBackend) backend).getTemperature();
    }

    public double getCO2() throws MachineException {
        requireSetup();
        return ((IncubatorBackend) backend).getCO2();
    }

    public int getNumSlots() {
        return numSlots;
    }
}
