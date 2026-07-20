package io.jlabrobot.platereading;

import io.jlabrobot.machines.Machine;
import io.jlabrobot.machines.MachineException;
import io.jlabrobot.resources.Plate;

public class PlateReader extends Machine {
    private Plate currentPlate;
    
    public PlateReader(PlateReaderBackend backend) {
        super(backend);
    }
    
    public void loadPlate(Plate plate) {
        this.currentPlate = plate;
    }
    
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
