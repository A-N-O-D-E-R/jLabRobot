package io.jlabrobot.pump;

import io.jlabrobot.machines.MachineBackend;
import io.jlabrobot.machines.MachineException;

public abstract class PumpBackend extends MachineBackend {
    public abstract void setFlowRate(double mlPerMinute) throws MachineException;
    public abstract void startPumping() throws MachineException;
    public abstract void stopPumping() throws MachineException;
    public abstract void dispense(double volumeMl) throws MachineException;
}
