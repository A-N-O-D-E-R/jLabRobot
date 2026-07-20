package io.jlabrobot.scale;

import io.jlabrobot.machines.MachineBackend;
import io.jlabrobot.machines.MachineException;

public abstract class ScaleBackend extends MachineBackend {
    public abstract void zero() throws MachineException;
    public abstract void tare() throws MachineException;
    public abstract double readWeight() throws MachineException;
}
