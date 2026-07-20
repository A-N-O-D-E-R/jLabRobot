package io.jlabrobot.platereading;

import io.jlabrobot.machines.MachineBackend;
import io.jlabrobot.machines.MachineException;
import io.jlabrobot.resources.Plate;
import io.jlabrobot.resources.Well;
import java.util.List;

public abstract class PlateReaderBackend extends MachineBackend {
    public abstract ReadingResult readLuminescence(
        Plate plate, List<Well> wells, double focalHeight
    ) throws MachineException;
    
    public abstract ReadingResult readAbsorbance(
        Plate plate, List<Well> wells, int wavelength
    ) throws MachineException;
    
    public abstract ReadingResult readFluorescence(
        Plate plate, List<Well> wells, 
        int excitationWavelength, int emissionWavelength, double focalHeight
    ) throws MachineException;
}
