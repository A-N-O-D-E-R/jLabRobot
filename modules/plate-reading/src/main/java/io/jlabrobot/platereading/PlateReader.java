package io.jlabrobot.platereading;

import io.jlabrobot.machines.Machine;
import io.jlabrobot.machines.MachineException;
import io.jlabrobot.resources.Plate;

/**
 * Controls plate reading operations including luminescence, absorbance, and fluorescence measurements.
 * Manages plate loading and delegates measurements to the backend.
 */
public class PlateReader extends Machine {
    private Plate currentPlate;

    /**
     * Constructs a PlateReader with a specified backend.
     * @param backend the plate reader backend
     */
    public PlateReader(PlateReaderBackend backend) {
        super(backend);
    }

    /**
     * Loads a plate into the reader for measurements.
     * @param plate the plate to load
     */
    public void loadPlate(Plate plate) {
        this.currentPlate = plate;
    }

    /**
     * Reads luminescence from all wells of the loaded plate.
     * @param focalHeight the focal height in millimeters
     * @return the reading result with well values
     * @throws MachineException if the machine is not set up or no plate is loaded
     */
    public ReadingResult readLuminescence(double focalHeight) throws MachineException {
        requireSetup();
        if (currentPlate == null) {
            throw new MachineException("No plate loaded");
        }

        PlateReaderBackend prBackend = (PlateReaderBackend) backend;
        return prBackend.readLuminescence(
            currentPlate, currentPlate.getAllItems(), focalHeight
        );
    }
}
