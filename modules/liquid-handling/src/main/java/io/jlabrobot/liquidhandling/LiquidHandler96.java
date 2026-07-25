package io.jlabrobot.liquidhandling;

import io.jlabrobot.backend.Backend;
import io.jlabrobot.backend.BackendException;
import io.jlabrobot.backend.Command;
import io.jlabrobot.backend.CommandResult;
import io.jlabrobot.core.Volume;
import io.jlabrobot.resources.Deck;
import io.jlabrobot.resources.Plate;
import io.jlabrobot.resources.TipRack;
import io.jlabrobot.resources.Well;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Specialized liquid handler for 96-channel pipette operations.
 * Supports full plate operations and pattern-based aspirations/dispensing.
 */
public class LiquidHandler96 extends LiquidHandler {
    private static final Logger log = LoggerFactory.getLogger(LiquidHandler96.class);

    private final Head96 head96;
    private final TipTracker tipTracker;

    /**
     * Constructs a LiquidHandler96 with a specified deck and backend.
     * @param deck the deck containing resources
     * @param backend the backend for executing commands
     */
    public LiquidHandler96(Deck deck, Backend backend) {
        super(deck, backend);
        this.head96 = new Head96();
        this.tipTracker = new TipTracker(96);
    }

    /**
     * Picks up all 96 tips from a tip rack simultaneously.
     * @param tipRack the tip rack containing 96 tips
     * @throws BackendException if the operation fails or tip rack doesn't have exactly 96 tips
     */
    public void pickUpTips96(TipRack tipRack) throws BackendException {
        log.info("Picking up 96 tips from {}", tipRack.getName());

        var allTips = tipRack.getAllItems();
        if (allTips.size() != 96) {
            throw new BackendException("TipRack must have exactly 96 tips");
        }

        Command cmd = new Command("pick_up_tips_96", Map.of(
            "tip_rack", tipRack.getAbsoluteLocation(),
            "pattern", "ALL_96"
        ));

        CommandResult result = backend.executeCommand(cmd);

        if (result.success()) {
            log.debug("96 tips picked up successfully");
        }
    }

    /**
     * Drops all 96 tips simultaneously.
     * @throws BackendException if the operation fails
     */
    public void dropTips96() throws BackendException {
        log.info("Dropping 96 tips");

        Command cmd = new Command("drop_tips_96", Map.of("pattern", "ALL_96"));
        backend.executeCommand(cmd);
    }

    /**
     * Aspirates the same volume from all 96 wells of a plate.
     * @param plate the plate to aspirate from
     * @param volume the volume to aspirate from each well
     * @throws BackendException if the operation fails or plate doesn't have exactly 96 wells
     */
    public void aspirate96(Plate plate, Volume volume) throws BackendException {
        log.info("Aspirating {}µL from all 96 wells of {}", volume.microliters(), plate.getName());

        List<Well> allWells = plate.getAllItems();
        if (allWells.size() != 96) {
            throw new BackendException("Plate must have exactly 96 wells");
        }

        List<Double> volumes = new ArrayList<>(96);
        for (int i = 0; i < 96; i++) {
            volumes.add(volume.microliters());
        }

        Command cmd = new Command("aspirate_96", Map.of(
            "plate", plate.getAbsoluteLocation(),
            "volumes", volumes,
            "pattern", "ALL_96"
        ));

        backend.executeCommand(cmd);
    }

    /**
     * Dispenses the same volume to all 96 wells of a plate.
     * @param plate the plate to dispense to
     * @param volume the volume to dispense to each well
     * @throws BackendException if the operation fails or plate doesn't have exactly 96 wells
     */
    public void dispense96(Plate plate, Volume volume) throws BackendException {
        log.info("Dispensing {}µL to all 96 wells of {}", volume.microliters(), plate.getName());

        List<Well> allWells = plate.getAllItems();
        if (allWells.size() != 96) {
            throw new BackendException("Plate must have exactly 96 wells");
        }

        List<Double> volumes = new ArrayList<>(96);
        for (int i = 0; i < 96; i++) {
            volumes.add(volume.microliters());
        }

        Command cmd = new Command("dispense_96", Map.of(
            "plate", plate.getAbsoluteLocation(),
            "volumes", volumes,
            "pattern", "ALL_96"
        ));

        backend.executeCommand(cmd);
    }

    /**
     * Aspirates the same volume from all wells in a specific column.
     * @param plate the plate to aspirate from
     * @param column the column number (1-12)
     * @param volume the volume to aspirate from each well
     * @throws BackendException if the operation fails
     */
    public void aspirateColumn(Plate plate, int column, Volume volume) throws BackendException {
        log.info("Aspirating column {} ({}µL per well)", column, volume.microliters());

        ChannelPattern pattern = ChannelPattern.column(column);
        List<Integer> channels = pattern.getChannels();

        List<Well> wells = new ArrayList<>();
        for (int channelIdx : channels) {
            wells.add(plate.getAllItems().get(channelIdx));
        }

        Command cmd = new Command("aspirate_column", Map.of(
            "plate", plate.getAbsoluteLocation(),
            "column", column,
            "volume", volume.microliters(),
            "channels", channels
        ));

        backend.executeCommand(cmd);
    }

    /**
     * Dispenses the same volume to all wells in a specific column.
     * @param plate the plate to dispense to
     * @param column the column number (1-12)
     * @param volume the volume to dispense to each well
     * @throws BackendException if the operation fails
     */
    public void dispenseColumn(Plate plate, int column, Volume volume) throws BackendException {
        log.info("Dispensing column {} ({}µL per well)", column, volume.microliters());

        ChannelPattern pattern = ChannelPattern.column(column);
        List<Integer> channels = pattern.getChannels();

        Command cmd = new Command("dispense_column", Map.of(
            "plate", plate.getAbsoluteLocation(),
            "column", column,
            "volume", volume.microliters(),
            "channels", channels
        ));

        backend.executeCommand(cmd);
    }

    /**
     * Aspirates the same volume from all wells in a specific row.
     * @param plate the plate to aspirate from
     * @param row the row letter (A-H)
     * @param volume the volume to aspirate from each well
     * @throws BackendException if the operation fails
     */
    public void aspirateRow(Plate plate, char row, Volume volume) throws BackendException {
        log.info("Aspirating row {} ({}µL per well)", row, volume.microliters());

        ChannelPattern pattern = ChannelPattern.row(row);
        List<Integer> channels = pattern.getChannels();

        Command cmd = new Command("aspirate_row", Map.of(
            "plate", plate.getAbsoluteLocation(),
            "row", String.valueOf(row),
            "volume", volume.microliters(),
            "channels", channels
        ));

        backend.executeCommand(cmd);
    }

    /**
     * Dispenses the same volume to all wells in a specific row.
     * @param plate the plate to dispense to
     * @param row the row letter (A-H)
     * @param volume the volume to dispense to each well
     * @throws BackendException if the operation fails
     */
    public void dispenseRow(Plate plate, char row, Volume volume) throws BackendException {
        log.info("Dispensing row {} ({}µL per well)", row, volume.microliters());

        ChannelPattern pattern = ChannelPattern.row(row);
        List<Integer> channels = pattern.getChannels();

        Command cmd = new Command("dispense_row", Map.of(
            "plate", plate.getAbsoluteLocation(),
            "row", String.valueOf(row),
            "volume", volume.microliters(),
            "channels", channels
        ));

        backend.executeCommand(cmd);
    }

    /**
     * Transfers liquid from all wells of a source plate to a destination plate.
     * @param source the source plate
     * @param dest the destination plate
     * @param volume the volume to transfer from/to each well
     * @throws BackendException if the operation fails
     */
    public void transfer96(Plate source, Plate dest, Volume volume) throws BackendException {
        log.info("Transferring {}µL from {} to {} (96-well)",
            volume.microliters(), source.getName(), dest.getName());

        aspirate96(source, volume);
        dispense96(dest, volume);
    }

    /**
     * Gets the 96-channel pipette head.
     * @return the head96
     */
    public Head96 getHead96() {
        return head96;
    }
}
