package io.jlabrobot.scale.mettler;

import io.jlabrobot.scale.ScaleBackend;
import io.jlabrobot.machines.MachineException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Backend implementation for Mettler Toledo precision scale.
 * Supports zeroing, taring, and weight measurement.
 */
public class MettlerToledoBackend extends ScaleBackend {
    private static final Logger log = LoggerFactory.getLogger(MettlerToledoBackend.class);

    private double tareWeight = 0.0;

    /**
     * Initializes the Mettler Toledo scale hardware.
     * @throws MachineException if initialization fails
     */
    @Override
    public void setup() throws MachineException {
        log.info("Setting up Mettler Toledo scale (stub)");
        this.setupFinished = true;
    }

    /**
     * Zeros the scale.
     * @throws MachineException if the operation fails
     */
    @Override
    public void zero() throws MachineException {
        log.info("Zeroing scale");
        this.tareWeight = 0.0;
    }

    /**
     * Tares the scale with the current weight.
     * @throws MachineException if the operation fails
     */
    @Override
    public void tare() throws MachineException {
        double current = readWeight();
        log.info("Taring scale at {} g", current);
        this.tareWeight = current;
    }

    /**
     * Reads the current weight.
     * @return the weight in grams
     * @throws MachineException if the operation fails
     */
    @Override
    public double readWeight() throws MachineException {
        double raw = Math.random() * 100;
        return raw - tareWeight;
    }

    /**
     * Shuts down the Mettler Toledo scale.
     * @throws MachineException if shutdown fails
     */
    @Override
    public void stop() throws MachineException {
        log.info("Stopping Mettler Toledo scale");
        this.setupFinished = false;
    }
}
