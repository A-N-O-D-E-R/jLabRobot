package io.jlabrobot.integration;

import io.jlabrobot.platereading.*;
import io.jlabrobot.platereading.bmg.CLARIOstarBackend;
import io.jlabrobot.resources.Plate;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@Tag("hardware")
class HardwarePlateReaderTest {
    @Test
    void testRealCLARIOstar() throws Exception {
        PlateReader reader = new PlateReader(new CLARIOstarBackend());
        reader.setup();
        
        Plate plate = Plate.createPlate96("test_plate");
        reader.loadPlate(plate);
        
        ReadingResult result = reader.readLuminescence(5.0);
        assertNotNull(result);
        assertFalse(result.wellReadings().isEmpty());
        
        reader.stop();
    }
}
