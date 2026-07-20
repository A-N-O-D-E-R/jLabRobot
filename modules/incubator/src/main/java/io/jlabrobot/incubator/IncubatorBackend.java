package io.jlabrobot.incubator;

import io.jlabrobot.machines.MachineBackend;
import io.jlabrobot.machines.MachineException;
import io.jlabrobot.resources.Plate;

public abstract class IncubatorBackend extends MachineBackend {

    public abstract void setTemperature(double celsius) throws MachineException;

    public abstract void setCO2(double percent) throws MachineException;

    public abstract void setHumidity(double percent) throws MachineException;

    public abstract void openDoor() throws MachineException;

    public abstract void closeDoor() throws MachineException;

    public abstract void loadPlate(Plate plate, int slot) throws MachineException;

    public abstract void unloadPlate(int slot) throws MachineException;

    public abstract double getTemperature() throws MachineException;

    public abstract double getCO2() throws MachineException;

    public abstract double getHumidity() throws MachineException;
}
