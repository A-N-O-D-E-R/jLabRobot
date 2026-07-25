package io.jlabrobot.thermocycler;

import io.jlabrobot.machines.Machine;
import io.jlabrobot.machines.MachineException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controls thermocycler operations for PCR and other thermal cycling experiments.
 * Manages independent lid and block temperatures, and PCR cycle execution.
 */
public class Thermocycler extends Machine {
    private static final Logger log = LoggerFactory.getLogger(Thermocycler.class);

    /**
     * Constructs a Thermocycler with a specified backend.
     * @param backend the thermocycler backend
     */
    public Thermocycler(ThermocyclerBackend backend) {
        super(backend);
    }

    /**
     * Sets the temperature of the lid.
     * @param celsius the target lid temperature in degrees Celsius
     * @throws MachineException if the machine is not set up or operation fails
     */
    public void setLidTemperature(double celsius) throws MachineException {
        requireSetup();
        log.info("Setting lid temperature to {}°C", celsius);
        ((ThermocyclerBackend) backend).setLidTemperature(celsius);
    }

    /**
     * Sets the temperature of the sample block.
     * @param celsius the target block temperature in degrees Celsius
     * @throws MachineException if the machine is not set up or operation fails
     */
    public void setBlockTemperature(double celsius) throws MachineException {
        requireSetup();
        log.info("Setting block temperature to {}°C", celsius);
        ((ThermocyclerBackend) backend).setBlockTemperature(celsius);
    }

    /**
     * Opens the thermocycler lid.
     * @throws MachineException if the machine is not set up or operation fails
     */
    public void openLid() throws MachineException {
        requireSetup();
        log.info("Opening lid");
        ((ThermocyclerBackend) backend).openLid();
    }

    /**
     * Closes the thermocycler lid.
     * @throws MachineException if the machine is not set up or operation fails
     */
    public void closeLid() throws MachineException {
        requireSetup();
        log.info("Closing lid");
        ((ThermocyclerBackend) backend).closeLid();
    }

    /**
     * Deactivates both lid and block heating.
     * @throws MachineException if the machine is not set up or operation fails
     */
    public void deactivate() throws MachineException {
        requireSetup();
        log.info("Deactivating thermocycler");
        ((ThermocyclerBackend) backend).deactivateLid();
        ((ThermocyclerBackend) backend).deactivateBlock();
    }

    /**
     * Gets the current lid temperature.
     * @return the lid temperature in degrees Celsius
     * @throws MachineException if the machine is not set up or operation fails
     */
    public double getLidTemperature() throws MachineException {
        requireSetup();
        return ((ThermocyclerBackend) backend).getLidTemperature();
    }

    /**
     * Gets the current block temperature.
     * @return the block temperature in degrees Celsius
     * @throws MachineException if the machine is not set up or operation fails
     */
    public double getBlockTemperature() throws MachineException {
        requireSetup();
        return ((ThermocyclerBackend) backend).getBlockTemperature();
    }

    /**
     * Runs a standard three-temperature PCR cycle.
     * @param cycles the number of cycles to run
     * @param denatureTemp the denaturation temperature in degrees Celsius
     * @param annealTemp the annealing temperature in degrees Celsius
     * @param extendTemp the extension temperature in degrees Celsius
     * @param denatureMs the denaturation duration in milliseconds
     * @param annealMs the annealing duration in milliseconds
     * @param extendMs the extension duration in milliseconds
     * @throws MachineException if the machine is not set up or operation fails
     */
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
