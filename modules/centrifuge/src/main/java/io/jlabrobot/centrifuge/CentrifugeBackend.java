package io.jlabrobot.centrifuge;

import io.jlabrobot.machines.MachineBackend;
import io.jlabrobot.machines.MachineException;

public abstract class CentrifugeBackend extends MachineBackend {
    public abstract void openDoor() throws MachineException;
    public abstract void closeDoor() throws MachineException;
    public abstract void lockDoor() throws MachineException;
    public abstract void spin(double gForce, long durationMs, double acceleration) throws MachineException;
    public abstract void stop() throws MachineException;
}
