package io.jlabrobot.platereading;

import io.jlabrobot.platereading.bmg.CLARIOstarBackend;
import io.jlabrobot.resources.Plate;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PlateReaderTest {
    @Test
    void testSetupAndRead() throws Exception {
        PlateReader reader = new PlateReader(new CLARIOstarBackend());
        reader.setup();
        
        Plate plate = Plate.createPlate96("test");
        reader.loadPlate(plate);
        
        ReadingResult result = reader.readLuminescence(5.0);
        assertNotNull(result);
        assertEquals(96, result.wellReadings().size());
        
        reader.stop();
    }
}
