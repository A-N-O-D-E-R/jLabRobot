package io.jlabrobot.heatingshaking;

import io.jlabrobot.machines.MachineBackend;
import io.jlabrobot.machines.MachineException;

public abstract class HeaterShakerBackend extends MachineBackend {
    public abstract void setTemperature(double celsius) throws MachineException;
    public abstract double getTemperature() throws MachineException;
    public abstract void startShaking(double rpm) throws MachineException;
    public abstract void stopShaking() throws MachineException;
    public abstract boolean supportsActiveCooling();
    public abstract boolean supportsLocking();
}
