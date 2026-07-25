package io.jlabrobot.thermocycler.inheco;

import io.jlabrobot.machines.MachineException;
import io.jlabrobot.thermocycler.ThermocyclerBackend;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Backend implementation for Inheco thermocycler.
 * Supports independent lid and block temperature control with thermal cycling.
 */
public class InhecoBackend extends ThermocyclerBackend {
    private static final Logger log = LoggerFactory.getLogger(InhecoBackend.class);

    private double lidTemp = 25.0;
    private double blockTemp = 25.0;
    private boolean lidOpen = true;

    /**
     * Initializes the Inheco thermocycler hardware.
     * @throws MachineException if initialization fails
     */
    @Override
    public void setup() throws MachineException {
        log.info("Setting up Inheco thermocycler (stub)");
        this.setupFinished = true;
    }

    /**
     * Shuts down the Inheco thermocycler.
     * @throws MachineException if shutdown fails
     */
    @Override
    public void stop() throws MachineException {
        log.info("Stopping Inheco thermocycler");
        deactivateLid();
        deactivateBlock();
        this.setupFinished = false;
    }

    /**
     * Sets the lid temperature.
     * @param celsius the target temperature in degrees Celsius
     * @throws MachineException if the operation fails
     */
    @Override
    public void setLidTemperature(double celsius) throws MachineException {
        log.info("Setting lid temperature: {}°C", celsius);
        this.lidTemp = celsius;
    }

    /**
     * Sets the sample block temperature.
     * @param celsius the target temperature in degrees Celsius
     * @throws MachineException if the operation fails
     */
    @Override
    public void setBlockTemperature(double celsius) throws MachineException {
        log.info("Setting block temperature: {}°C", celsius);
        this.blockTemp = celsius;
    }

    /**
     * Opens the thermocycler lid.
     * @throws MachineException if the operation fails
     */
    @Override
    public void openLid() throws MachineException {
        log.info("Opening lid");
        this.lidOpen = true;
    }

    /**
     * Closes the thermocycler lid.
     * @throws MachineException if the operation fails
     */
    @Override
    public void closeLid() throws MachineException {
        log.info("Closing lid");
        this.lidOpen = false;
    }

    /**
     * Deactivates the lid heater.
     * @throws MachineException if the operation fails
     */
    @Override
    public void deactivateLid() throws MachineException {
        log.info("Deactivating lid heater");
        this.lidTemp = 25.0;
    }

    /**
     * Deactivates the block heater.
     * @throws MachineException if the operation fails
     */
    @Override
    public void deactivateBlock() throws MachineException {
        log.info("Deactivating block heater");
        this.blockTemp = 25.0;
    }

    /**
     * Gets the current lid temperature.
     * @return the temperature in degrees Celsius
     * @throws MachineException if the operation fails
     */
    @Override
    public double getLidTemperature() throws MachineException {
        return lidTemp;
    }

    /**
     * Gets the current block temperature.
     * @return the temperature in degrees Celsius
     * @throws MachineException if the operation fails
     */
    @Override
    public double getBlockTemperature() throws MachineException {
        return blockTemp;
    }

    /**
     * Runs a thermal cycle at the specified temperature.
     * @param cycles the number of cycles
     * @param temp the temperature in degrees Celsius
     * @param holdTimeMs the hold duration in milliseconds
     * @throws MachineException if the operation fails
     */
    @Override
    public void runCycle(int cycles, double temp, long holdTimeMs) throws MachineException {
        log.debug("Running {} cycle(s) at {}°C for {}ms", cycles, temp, holdTimeMs);
        setBlockTemperature(temp);
    }
}
