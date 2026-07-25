package io.jlabrobot.pump.masterflex;

import io.jlabrobot.pump.PumpBackend;
import io.jlabrobot.machines.MachineException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Backend implementation for Masterflex peristaltic pump.
 * Supports variable flow rates and volume-based dispensing.
 */
public class MasterflexBackend extends PumpBackend {
    private static final Logger log = LoggerFactory.getLogger(MasterflexBackend.class);

    private double flowRate = 0.0;
    private boolean pumping = false;

    /**
     * Initializes the Masterflex pump hardware.
     * @throws MachineException if initialization fails
     */
    @Override
    public void setup() throws MachineException {
        log.info("Setting up Masterflex pump (stub)");
        this.setupFinished = true;
    }

    /**
     * Sets the flow rate.
     * @param mlPerMinute the flow rate in milliliters per minute
     * @throws MachineException if the operation fails
     */
    @Override
    public void setFlowRate(double mlPerMinute) throws MachineException {
        log.info("Flow rate set to {} mL/min", mlPerMinute);
        this.flowRate = mlPerMinute;
    }

    /**
     * Starts pumping.
     * @throws MachineException if the operation fails
     */
    @Override
    public void startPumping() throws MachineException {
        log.info("Pump started at {} mL/min", flowRate);
        this.pumping = true;
    }

    /**
     * Stops pumping.
     * @throws MachineException if the operation fails
     */
    @Override
    public void stopPumping() throws MachineException {
        log.info("Pump stopped");
        this.pumping = false;
    }

    /**
     * Dispenses a specific volume at the current flow rate.
     * @param volumeMl the volume to dispense in milliliters
     * @throws MachineException if flow rate is not set or operation fails
     */
    @Override
    public void dispense(double volumeMl) throws MachineException {
        if (flowRate <= 0) {
            throw new MachineException("Flow rate not set");
        }
        double durationMin = volumeMl / flowRate;
        log.info("Dispensing {} mL (will take {} minutes)", volumeMl, durationMin);
    }

    /**
     * Shuts down the Masterflex pump.
     * @throws MachineException if shutdown fails
     */
    @Override
    public void stop() throws MachineException {
        log.info("Stopping Masterflex pump");
        this.setupFinished = false;
    }
}
