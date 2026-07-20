package io.jlabrobot.liquidhandling;

import io.jlabrobot.backend.Backend;
import io.jlabrobot.backend.BackendException;
import io.jlabrobot.backend.Command;
import io.jlabrobot.backend.CommandResult;
import io.jlabrobot.core.Volume;
import io.jlabrobot.resources.Deck;
import io.jlabrobot.resources.Tip;
import io.jlabrobot.resources.Well;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class LiquidHandler {
    private static final Logger log = LoggerFactory.getLogger(LiquidHandler.class);

    private final Deck deck;
    protected final Backend backend;
    private final Head head;
    private final VolumeTracker volumeTracker;

    public LiquidHandler(Deck deck, Backend backend) {
        this.deck = deck;
        this.backend = backend;
        this.head = new Head();
        this.volumeTracker = new VolumeTracker();
    }

    public void initialize() throws BackendException {
        log.info("Initializing liquid handler");
        backend.initialize();
    }

    public void shutdown() {
        log.info("Shutting down liquid handler");
        backend.shutdown();
    }

    public void pickUpTips(List<Tip> tips) throws BackendException {
        log.info("Picking up {} tips", tips.size());
        head.setTips(tips);

        Command cmd = new Command("pick_up_tips", Map.of(
                "tips", tips.stream().map(t -> t.getAbsoluteLocation()).toList()
        ));
        CommandResult result = backend.executeCommand(cmd);

        if (!result.success()) {
            throw new BackendException("Failed to pick up tips: " + result.message());
        }

        tips.forEach(Tip::markUsed);
    }

    public void aspirate(List<Well> wells, List<Double> volumesMicroliters) throws BackendException {
        if (wells.size() != volumesMicroliters.size()) {
            throw new IllegalArgumentException("Wells and volumes must have same size");
        }

        log.info("Aspirating from {} wells", wells.size());

        for (int i = 0; i < wells.size(); i++) {
            Well well = wells.get(i);
            Volume volume = new Volume(volumesMicroliters.get(i));
            volumeTracker.recordAspirate(well, volume);
        }

        Command cmd = new Command("aspirate", Map.of(
                "wells", wells.stream().map(w -> w.getAbsoluteLocation()).toList(),
                "volumes", volumesMicroliters
        ));
        CommandResult result = backend.executeCommand(cmd);

        if (!result.success()) {
            throw new BackendException("Failed to aspirate: " + result.message());
        }
    }

    public void dispense(List<Well> wells, List<Double> volumesMicroliters) throws BackendException {
        if (wells.size() != volumesMicroliters.size()) {
            throw new IllegalArgumentException("Wells and volumes must have same size");
        }

        log.info("Dispensing to {} wells", wells.size());

        for (int i = 0; i < wells.size(); i++) {
            Well well = wells.get(i);
            Volume volume = new Volume(volumesMicroliters.get(i));
            volumeTracker.recordDispense(well, volume);
        }

        Command cmd = new Command("dispense", Map.of(
                "wells", wells.stream().map(w -> w.getAbsoluteLocation()).toList(),
                "volumes", volumesMicroliters
        ));
        CommandResult result = backend.executeCommand(cmd);

        if (!result.success()) {
            throw new BackendException("Failed to dispense: " + result.message());
        }
    }

    public void dropTips(List<Tip> tips) throws BackendException {
        log.info("Dropping {} tips", tips.size());

        Command cmd = new Command("drop_tips", Map.of(
                "tips", tips.stream().map(t -> t.getAbsoluteLocation()).toList()
        ));
        CommandResult result = backend.executeCommand(cmd);

        if (!result.success()) {
            throw new BackendException("Failed to drop tips: " + result.message());
        }

        head.clearTips();
    }

    public Deck getDeck() {
        return deck;
    }

    public Head getHead() {
        return head;
    }

    public VolumeTracker getVolumeTracker() {
        return volumeTracker;
    }
}
