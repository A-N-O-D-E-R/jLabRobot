package io.jlabrobot.thermocycler.inheco;

import io.jlabrobot.machines.MachineException;
import io.jlabrobot.thermocycler.ThermocyclerBackend;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InhecoBackend extends ThermocyclerBackend {
    private static final Logger log = LoggerFactory.getLogger(InhecoBackend.class);

    private double lidTemp = 25.0;
    private double blockTemp = 25.0;
    private boolean lidOpen = true;

    @Override
    public void setup() throws MachineException {
        log.info("Setting up Inheco thermocycler (stub)");
        this.setupFinished = true;
    }

    @Override
    public void stop() throws MachineException {
        log.info("Stopping Inheco thermocycler");
        deactivateLid();
        deactivateBlock();
        this.setupFinished = false;
    }

    @Override
    public void setLidTemperature(double celsius) throws MachineException {
        log.info("Setting lid temperature: {}°C", celsius);
        this.lidTemp = celsius;
    }

    @Override
    public void setBlockTemperature(double celsius) throws MachineException {
        log.info("Setting block temperature: {}°C", celsius);
        this.blockTemp = celsius;
    }

    @Override
    public void openLid() throws MachineException {
        log.info("Opening lid");
        this.lidOpen = true;
    }

    @Override
    public void closeLid() throws MachineException {
        log.info("Closing lid");
        this.lidOpen = false;
    }

    @Override
    public void deactivateLid() throws MachineException {
        log.info("Deactivating lid heater");
        this.lidTemp = 25.0;
    }

    @Override
    public void deactivateBlock() throws MachineException {
        log.info("Deactivating block heater");
        this.blockTemp = 25.0;
    }

    @Override
    public double getLidTemperature() throws MachineException {
        return lidTemp;
    }

    @Override
    public double getBlockTemperature() throws MachineException {
        return blockTemp;
    }

    @Override
    public void runCycle(int cycles, double temp, long holdTimeMs) throws MachineException {
        log.debug("Running {} cycle(s) at {}°C for {}ms", cycles, temp, holdTimeMs);
        setBlockTemperature(temp);
    }
}
