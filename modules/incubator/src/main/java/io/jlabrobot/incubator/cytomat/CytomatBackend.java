package io.jlabrobot.incubator.cytomat;

import io.jlabrobot.incubator.IncubatorBackend;
import io.jlabrobot.machines.MachineException;
import io.jlabrobot.resources.Plate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class CytomatBackend extends IncubatorBackend {
    private static final Logger log = LoggerFactory.getLogger(CytomatBackend.class);

    private double temperature = 37.0;
    private double co2 = 5.0;
    private double humidity = 95.0;
    private final Map<Integer, Plate> slots = new HashMap<>();

    @Override
    public void setup() throws MachineException {
        log.info("Setting up Cytomat incubator (stub)");
        this.setupFinished = true;
    }

    @Override
    public void stop() throws MachineException {
        log.info("Stopping Cytomat incubator");
        slots.clear();
        this.setupFinished = false;
    }

    @Override
    public void setTemperature(double celsius) throws MachineException {
        log.info("Setting temperature: {}°C", celsius);
        this.temperature = celsius;
    }

    @Override
    public void setCO2(double percent) throws MachineException {
        log.info("Setting CO2: {}%", percent);
        this.co2 = percent;
    }

    @Override
    public void setHumidity(double percent) throws MachineException {
        log.info("Setting humidity: {}%", percent);
        this.humidity = percent;
    }

    @Override
    public void openDoor() throws MachineException {
        log.debug("Opening door");
    }

    @Override
    public void closeDoor() throws MachineException {
        log.debug("Closing door");
    }

    @Override
    public void loadPlate(Plate plate, int slot) throws MachineException {
        log.info("Loading plate {} into slot {}", plate.getName(), slot);
        slots.put(slot, plate);
    }

    @Override
    public void unloadPlate(int slot) throws MachineException {
        log.info("Unloading plate from slot {}", slot);
        slots.remove(slot);
    }

    @Override
    public double getTemperature() throws MachineException {
        return temperature;
    }

    @Override
    public double getCO2() throws MachineException {
        return co2;
    }

    @Override
    public double getHumidity() throws MachineException {
        return humidity;
    }
}
