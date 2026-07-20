package io.jlabrobot.thermocycler;

import io.jlabrobot.machines.Machine;
import io.jlabrobot.machines.MachineException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Thermocycler extends Machine {
    private static final Logger log = LoggerFactory.getLogger(Thermocycler.class);

    public Thermocycler(ThermocyclerBackend backend) {
        super(backend);
    }

    public void setLidTemperature(double celsius) throws MachineException {
        requireSetup();
        log.info("Setting lid temperature to {}°C", celsius);
        ((ThermocyclerBackend) backend).setLidTemperature(celsius);
    }

    public void setBlockTemperature(double celsius) throws MachineException {
        requireSetup();
        log.info("Setting block temperature to {}°C", celsius);
        ((ThermocyclerBackend) backend).setBlockTemperature(celsius);
    }

    public void openLid() throws MachineException {
        requireSetup();
        log.info("Opening lid");
        ((ThermocyclerBackend) backend).openLid();
    }

    public void closeLid() throws MachineException {
        requireSetup();
        log.info("Closing lid");
        ((ThermocyclerBackend) backend).closeLid();
    }

    public void deactivate() throws MachineException {
        requireSetup();
        log.info("Deactivating thermocycler");
        ((ThermocyclerBackend) backend).deactivateLid();
        ((ThermocyclerBackend) backend).deactivateBlock();
    }

    public double getLidTemperature() throws MachineException {
        requireSetup();
        return ((ThermocyclerBackend) backend).getLidTemperature();
    }

    public double getBlockTemperature() throws MachineException {
        requireSetup();
        return ((ThermocyclerBackend) backend).getBlockTemperature();
    }

    public void runPCRCycle(int cycles, double denatureTemp, double annealTemp, double extendTemp,
                            long denatureMs, long annealMs, long extendMs) throws MachineException {
        requireSetup();
        log.info("Running {} PCR cycles: denature {}°C ({}ms), anneal {}°C ({}ms), extend {}°C ({}ms)",
            cycles, denatureTemp, denatureMs, annealTemp, annealMs, extendTemp, extendMs);

        setLidTemperature(105.0);
        closeLid();

        for (int i = 1; i <= cycles; i++) {
            log.debug("Cycle {}/{}", i, cycles);
            ((ThermocyclerBackend) backend).runCycle(1, denatureTemp, denatureMs);
            ((ThermocyclerBackend) backend).runCycle(1, annealTemp, annealMs);
            ((ThermocyclerBackend) backend).runCycle(1, extendTemp, extendMs);
        }

        log.info("PCR complete");
    }
}
