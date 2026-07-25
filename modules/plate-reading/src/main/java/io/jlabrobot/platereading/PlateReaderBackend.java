package io.jlabrobot.platereading;

import io.jlabrobot.machines.MachineBackend;
import io.jlabrobot.machines.MachineException;
import io.jlabrobot.resources.Plate;
import io.jlabrobot.resources.Well;
import java.util.List;

/**
 * Abstract backend for plate reading hardware supporting multiple measurement types.
 * Implementations handle communication with specific reader brands.
 */
public abstract class PlateReaderBackend extends MachineBackend {
    /**
     * Reads luminescence from specified wells.
     * @param plate the plate being read
     * @param wells the wells to read
     * @param focalHeight the focal height in millimeters
     * @return the reading result
     * @throws MachineException if the operation fails
     */
    public abstract ReadingResult readLuminescence(
        Plate plate, List<Well> wells, double focalHeight
    ) throws MachineException;

    /**
     * Reads absorbance from specified wells.
     * @param plate the plate being read
     * @param wells the wells to read
     * @param wavelength the wavelength in nanometers
     * @return the reading result
     * @throws MachineException if the operation fails
     */
    public abstract ReadingResult readAbsorbance(
        Plate plate, List<Well> wells, int wavelength
    ) throws MachineException;

    /**
     * Reads fluorescence from specified wells.
     * @param plate the plate being read
     * @param wells the wells to read
     * @param excitationWavelength the excitation wavelength in nanometers
     * @param emissionWavelength the emission wavelength in nanometers
     * @param focalHeight the focal height in millimeters
     * @return the reading result
     * @throws MachineException if the operation fails
     */
    public abstract ReadingResult readFluorescence(
        Plate plate, List<Well> wells,
        int excitationWavelength, int emissionWavelength, double focalHeight
    ) throws MachineException;
}
