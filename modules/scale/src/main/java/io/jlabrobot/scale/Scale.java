package io.jlabrobot.scale;

import io.jlabrobot.machines.Machine;
import io.jlabrobot.machines.MachineException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controls weighing operations on a precision scale.
 * Supports zeroing, taring, and weight measurement.
 */
public class Scale extends Machine {
    private static final Logger log = LoggerFactory.getLogger(Scale.class);

    /**
     * Constructs a Scale with a specified backend.
     * @param backend the scale backend
     */
    public Scale(ScaleBackend backend) {
        super(backend);
    }

    /**
     * Zeros the scale.
     * @throws MachineException if the machine is not set up or operation fails
     */
    public void zero() throws MachineException {
        requireSetup();
        log.info("Zeroing scale");
        ((ScaleBackend) backend).zero();
    }

    /**
     * Tares the scale with the current content.
     * @throws MachineException if the machine is not set up or operation fails
     */
    public void tare() throws MachineException {
        requireSetup();
        log.info("Taring scale");
        ((ScaleBackend) backend).tare();
    }

    /**
     * Reads the current weight.
     * @return the weight in grams
     * @throws MachineException if the machine is not set up or operation fails
     */
    public double readWeight() throws MachineException {
        requireSetup();
        double weight = ((ScaleBackend) backend).readWeight();
        log.info("Weight: {} g", weight);
        return weight;
    }
}
