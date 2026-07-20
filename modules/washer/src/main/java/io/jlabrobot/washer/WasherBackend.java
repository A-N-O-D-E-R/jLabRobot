package io.jlabrobot.washer;

import io.jlabrobot.machines.MachineBackend;
import io.jlabrobot.machines.MachineException;
import io.jlabrobot.resources.Plate;

public abstract class WasherBackend extends MachineBackend {

    public abstract void loadPlate(Plate plate) throws MachineException;

    public abstract void unloadPlate() throws MachineException;

    public abstract void wash(int cycles, double volumeMl) throws MachineException;

    public abstract void aspirate() throws MachineException;

    public abstract void dispense(double volumeMl) throws MachineException;

    public abstract void prime() throws MachineException;
}
