package io.jlabrobot.thermocycler;

import io.jlabrobot.machines.MachineBackend;
import io.jlabrobot.machines.MachineException;

public abstract class ThermocyclerBackend extends MachineBackend {

    public abstract void setLidTemperature(double celsius) throws MachineException;

    public abstract void setBlockTemperature(double celsius) throws MachineException;

    public abstract void openLid() throws MachineException;

    public abstract void closeLid() throws MachineException;

    public abstract void deactivateLid() throws MachineException;

    public abstract void deactivateBlock() throws MachineException;

    public abstract double getLidTemperature() throws MachineException;

    public abstract double getBlockTemperature() throws MachineException;

    public abstract void runCycle(int cycles, double temp, long holdTimeMs) throws MachineException;
}
