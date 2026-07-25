package io.jlabrobot.pump;

import io.jlabrobot.machines.Machine;
import io.jlabrobot.machines.MachineException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controls peristaltic pump operations for precise liquid dispensing.
 * Supports variable flow rates and volume-based dispensing.
 */
public class Pump extends Machine {
    private static final Logger log = LoggerFactory.getLogger(Pump.class);

    /**
     * Constructs a Pump with a specified backend.
     * @param backend the pump backend
     */
    public Pump(PumpBackend backend) {
        super(backend);
    }

    /**
     * Sets the flow rate for the pump.
     * @param mlPerMinute the flow rate in milliliters per minute
     * @throws MachineException if the machine is not set up or operation fails
     */
    public void setFlowRate(double mlPerMinute) throws MachineException {
        requireSetup();
        log.info("Setting flow rate to {} mL/min", mlPerMinute);
        ((PumpBackend) backend).setFlowRate(mlPerMinute);
    }

    /**
     * Starts the pump at the current flow rate.
     * @throws MachineException if the machine is not set up or operation fails
     */
    public void startPumping() throws MachineException {
        requireSetup();
        log.info("Starting pump");
        ((PumpBackend) backend).startPumping();
    }

    /**
     * Stops the pump.
     * @throws MachineException if the machine is not set up or operation fails
     */
    public void stopPumping() throws MachineException {
        requireSetup();
        log.info("Stopping pump");
        ((PumpBackend) backend).stopPumping();
    }

    /**
     * Dispenses a specific volume.
     * @param volumeMl the volume to dispense in milliliters
     * @throws MachineException if the machine is not set up or operation fails
     */
    public void dispense(double volumeMl) throws MachineException {
        requireSetup();
        log.info("Dispensing {} mL", volumeMl);
        ((PumpBackend) backend).dispense(volumeMl);
    }
}
