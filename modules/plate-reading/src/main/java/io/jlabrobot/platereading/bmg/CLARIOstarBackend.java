package io.jlabrobot.platereading.bmg;

import io.jlabrobot.platereading.*;
import io.jlabrobot.machines.MachineException;
import io.jlabrobot.resources.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

public class CLARIOstarBackend extends PlateReaderBackend {
    private static final Logger log = LoggerFactory.getLogger(CLARIOstarBackend.class);
    
    @Override
    public void setup() throws MachineException {
        log.info("Setting up BMG CLARIOstar (stub)");
        this.setupFinished = true;
    }
    
    @Override
    public ReadingResult readLuminescence(
        Plate plate, List<Well> wells, double focalHeight
    ) throws MachineException {
        log.info("Reading luminescence from {} wells at focal height {}mm", 
            wells.size(), focalHeight);
        
        Map<String, Double> readings = new HashMap<>();
        for (Well well : wells) {
            readings.put(well.getName(), Math.random() * 10000);
        }
        
        return new ReadingResult(System.currentTimeMillis(), readings);
    }
    
    @Override
    public ReadingResult readAbsorbance(
        Plate plate, List<Well> wells, int wavelength
    ) throws MachineException {
        log.info("Reading absorbance at {}nm from {} wells", wavelength, wells.size());
        throw new MachineException("Absorbance reading not implemented");
    }
    
    @Override
    public ReadingResult readFluorescence(
        Plate plate, List<Well> wells,
        int excitationWavelength, int emissionWavelength, double focalHeight
    ) throws MachineException {
        log.info("Reading fluorescence Ex{}nm/Em{}nm", 
            excitationWavelength, emissionWavelength);
        throw new MachineException("Fluorescence reading not implemented");
    }
    
    @Override
    public void stop() throws MachineException {
        log.info("Stopping BMG CLARIOstar");
        this.setupFinished = false;
    }
}
