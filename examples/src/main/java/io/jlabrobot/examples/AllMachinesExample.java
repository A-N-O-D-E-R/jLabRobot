package io.jlabrobot.examples;

import io.jlabrobot.heatingshaking.HeaterShaker;
import io.jlabrobot.heatingshaking.inheco.InhecoThermoShakeBackend;
import io.jlabrobot.centrifuge.Centrifuge;
import io.jlabrobot.centrifuge.vspin.VSpinBackend;
import io.jlabrobot.scale.Scale;
import io.jlabrobot.scale.mettler.MettlerToledoBackend;
import io.jlabrobot.pump.Pump;
import io.jlabrobot.pump.masterflex.MasterflexBackend;
import io.jlabrobot.platereading.PlateReader;
import io.jlabrobot.platereading.bmg.CLARIOstarBackend;
import io.jlabrobot.resources.Plate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AllMachinesExample {
    private static final Logger log = LoggerFactory.getLogger(AllMachinesExample.class);
    
    public static void main(String[] args) throws Exception {
        log.info("=== jLabRobot Machine Types Demo ===");
        
        // Heater/Shaker
        log.info("\n--- Heater/Shaker ---");
        HeaterShaker heaterShaker = new HeaterShaker(new InhecoThermoShakeBackend());
        heaterShaker.setup();
        heaterShaker.setTemperature(37.0);
        heaterShaker.startShaking(300);
        Thread.sleep(100);
        heaterShaker.stopShaking();
        heaterShaker.stop();
        
        // Centrifuge
        log.info("\n--- Centrifuge ---");
        Centrifuge centrifuge = new Centrifuge(new VSpinBackend());
        centrifuge.setup();
        centrifuge.openDoor();
        centrifuge.closeDoor();
        centrifuge.lockDoor();
        centrifuge.spin(1000, 60000, 9.0);
        centrifuge.stop();
        
        // Scale
        log.info("\n--- Scale ---");
        Scale scale = new Scale(new MettlerToledoBackend());
        scale.setup();
        scale.zero();
        double weight = scale.readWeight();
        scale.tare();
        scale.stop();
        
        // Pump
        log.info("\n--- Pump ---");
        Pump pump = new Pump(new MasterflexBackend());
        pump.setup();
        pump.setFlowRate(10.0);
        pump.dispense(5.0);
        pump.stop();
        
        // Plate Reader
        log.info("\n--- Plate Reader ---");
        PlateReader reader = new PlateReader(new CLARIOstarBackend());
        reader.setup();
        Plate plate = Plate.createPlate96("assay");
        reader.loadPlate(plate);
        reader.readLuminescence(5.0);
        reader.stop();
        
        log.info("\n=== Demo Complete ===");
    }
}
