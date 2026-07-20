package io.jlabrobot.examples;

import io.jlabrobot.platereading.*;
import io.jlabrobot.platereading.bmg.CLARIOstarBackend;
import io.jlabrobot.resources.Plate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlateReaderExample {
    private static final Logger log = LoggerFactory.getLogger(PlateReaderExample.class);
    
    public static void main(String[] args) throws Exception {
        PlateReader reader = new PlateReader(new CLARIOstarBackend());
        reader.setup();
        
        Plate assayPlate = Plate.createPlate96("assay");
        reader.loadPlate(assayPlate);
        
        log.info("Reading luminescence...");
        ReadingResult result = reader.readLuminescence(5.0);
        
        result.wellReadings().forEach((well, value) -> 
            log.info("Well {}: {} RLU", well, value)
        );
        
        reader.stop();
    }
}
