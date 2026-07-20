package io.jlabrobot.examples;

import io.jlabrobot.resources.Plate;
import io.jlabrobot.resources.vendor.axygen.Axygen;
import io.jlabrobot.resources.vendor.corning.Corning;
import io.jlabrobot.resources.vendor.eppendorf.Eppendorf;
import io.jlabrobot.resources.vendor.falcon.Falcon;
import io.jlabrobot.resources.vendor.greiner.Greiner;
import io.jlabrobot.resources.vendor.sarstedt.Sarstedt;
import io.jlabrobot.resources.vendor.thermofisher.ThermoFisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VendorCatalogExample {
    private static final Logger log = LoggerFactory.getLogger(VendorCatalogExample.class);

    public static void main(String[] args) {
        log.info("=== Vendor Catalog Demo ===\n");

        log.info("--- Corning ---");
        Plate corning3596 = Corning.plate3596("corning-96");
        log.info("Corning 3596: {} wells, {}mL per well",
            corning3596.getAllItems().size(),
            corning3596.getAllItems().get(0).getMaxVolume().microliters() / 1000.0);

        log.info("\n--- Thermo Fisher / Nunc ---");
        Plate nunc96 = ThermoFisher.nunc96MicroWell("nunc-96");
        Plate nunc384 = ThermoFisher.nunc384MicroWell("nunc-384");
        log.info("Nunc 96-well: {} wells, {}µL", nunc96.getAllItems().size(),
            nunc96.getAllItems().get(0).getMaxVolume().microliters());
        log.info("Nunc 384-well: {} wells, {}µL", nunc384.getAllItems().size(),
            nunc384.getAllItems().get(0).getMaxVolume().microliters());

        log.info("\n--- Falcon ---");
        Plate falcon96 = Falcon.plate353072("falcon-flat");
        Plate falcon24 = Falcon.plate353263("falcon-24");
        log.info("Falcon 353072: 96-well flat bottom");
        log.info("Falcon 353263: 24-well TC-treated ({} wells)", falcon24.getAllItems().size());

        log.info("\n--- Axygen ---");
        Plate axygenPCR = Axygen.pcr96("axygen-pcr");
        Plate axygenDeep = Axygen.deepWell2mL("axygen-deep");
        log.info("Axygen PCR-96-C: {}µL PCR plate",
            axygenPCR.getAllItems().get(0).getMaxVolume().microliters());
        log.info("Axygen P-DW-2ML: {}mL deep well",
            axygenDeep.getAllItems().get(0).getMaxVolume().microliters() / 1000.0);

        log.info("\n--- Sarstedt ---");
        Plate sarstedt = Sarstedt.plate821582001("sarstedt-flat");
        log.info("Sarstedt 82.1582.001: 96-well flat bottom");

        log.info("\n--- Eppendorf ---");
        Plate eppendorfPCR = Eppendorf.twinTecPCR96("eppendorf-pcr");
        log.info("Eppendorf twin.tec PCR: {}µL",
            eppendorfPCR.getAllItems().get(0).getMaxVolume().microliters());

        log.info("\n--- Greiner ---");
        Plate greiner = Greiner.cellstar96("greiner-cellstar");
        log.info("Greiner CELLSTAR: {} wells, TC-treated for cell culture",
            greiner.getAllItems().size());

        log.info("\n=== Catalog Summary ===");
        log.info("Total vendors: 7 (Corning, Thermo Fisher, Falcon, Axygen, Sarstedt,");
        log.info("               Eppendorf, Greiner)");
        log.info("Total plates cataloged: 25+ different plate types");
        log.info("Formats: 24-well, 96-well, 384-well, DeepWell, PCR, TC-treated");
    }
}
