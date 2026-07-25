package io.jlabrobot.liquidhandling;

import io.jlabrobot.backend.Backend;
import io.jlabrobot.backend.BackendException;
import io.jlabrobot.backend.Command;
import io.jlabrobot.backend.CommandResult;
import io.jlabrobot.core.Volume;
import io.jlabrobot.liquidhandling.liquidclass.*;
import io.jlabrobot.resources.Well;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Extended liquid handler that applies liquid class parameters to aspiration and dispensing operations.
 * Liquid classes define flow rates, air gaps, and settling times for different liquid types.
 */
public class LiquidHandlerWithClasses extends LiquidHandler {
    private static final Logger log = LoggerFactory.getLogger(LiquidHandlerWithClasses.class);

    private LiquidClass currentLiquidClass = LiquidClass.water();

    /**
     * Constructs a LiquidHandlerWithClasses with a specified deck and backend.
     * @param deck the deck containing resources
     * @param backend the backend for executing commands
     */
    public LiquidHandlerWithClasses(io.jlabrobot.resources.Deck deck, Backend backend) {
        super(deck, backend);
    }

    /**
     * Sets the active liquid class for subsequent operations.
     * @param liquidClass the liquid class to apply
     */
    public void setLiquidClass(LiquidClass liquidClass) {
        log.info("Setting liquid class: {}", liquidClass.name());
        this.currentLiquidClass = liquidClass;
    }

    /**
     * Aspirates from wells using the current liquid class parameters.
     * @param wells the list of wells to aspirate from
     * @param volumes the volumes to aspirate from each well
     * @throws BackendException if the operation fails
     */
    public void aspirateWithClass(List<Well> wells, List<Volume> volumes) throws BackendException {
        if (wells.size() != volumes.size()) {
            throw new IllegalArgumentException("Wells and volumes must have same size");
        }

        log.info("Aspirating {} with liquid class: {}", wells.size(), currentLiquidClass.name());

        for (int i = 0; i < wells.size(); i++) {
            AspirateParameters params = AspirateParameters.fromLiquidClass(
                volumes.get(i),
                currentLiquidClass
            );

            Map<String, Object> cmdParams = new HashMap<>();
            cmdParams.put("well", wells.get(i).getAbsoluteLocation());
            cmdParams.put("volume", params.volume().microliters());
            cmdParams.put("flow_rate", params.flowRate());
            cmdParams.put("air_gap", params.airGap().microliters());
            cmdParams.put("settling_time", params.settlingTime().microliters());

            Command cmd = new Command("aspirate_with_params", cmdParams);
            CommandResult result = super.backend.executeCommand(cmd);

            if (!result.success()) {
                throw new BackendException("Failed to aspirate: " + result.message());
            }
        }
    }

    /**
     * Dispenses to wells using the current liquid class parameters.
     * @param wells the list of wells to dispense to
     * @param volumes the volumes to dispense to each well
     * @throws BackendException if the operation fails
     */
    public void dispenseWithClass(List<Well> wells, List<Volume> volumes) throws BackendException {
        if (wells.size() != volumes.size()) {
            throw new IllegalArgumentException("Wells and volumes must have same size");
        }

        log.info("Dispensing {} with liquid class: {}", wells.size(), currentLiquidClass.name());

        for (int i = 0; i < wells.size(); i++) {
            DispenseParameters params = DispenseParameters.fromLiquidClass(
                volumes.get(i),
                currentLiquidClass
            );

            Map<String, Object> cmdParams = new HashMap<>();
            cmdParams.put("well", wells.get(i).getAbsoluteLocation());
            cmdParams.put("volume", params.volume().microliters());
            cmdParams.put("flow_rate", params.flowRate());
            cmdParams.put("blowout_volume", params.blowoutVolume());
            cmdParams.put("settling_time", params.settlingTime().microliters());

            Command cmd = new Command("dispense_with_params", cmdParams);
            CommandResult result = super.backend.executeCommand(cmd);

            if (!result.success()) {
                throw new BackendException("Failed to dispense: " + result.message());
            }
        }
    }
}
