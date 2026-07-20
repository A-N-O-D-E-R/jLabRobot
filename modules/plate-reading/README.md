# Plate Reading Module

Microplate reader integration for absorbance, fluorescence, and luminescence.

## Components

**Main API**
- `PlateReader` - High-level plate reader interface
- `ReadingResult` - Measurement results with well data

**Backend Interface**
- `PlateReaderBackend` - Hardware abstraction

**Implementations**
- `CLARIOstarBackend` - BMG Labtech CLARIOstar (stub)

## Usage

```java
PlateReaderBackend backend = new CLARIOstarBackend();
PlateReader reader = new PlateReader(backend);

reader.setup();

// Load plate
Plate plate = Plate.createPlate96("assay");
reader.loadPlate(plate);

// Read absorbance
ReadingResult absorbance = reader.readAbsorbance(450.0); // 450nm

// Read fluorescence
ReadingResult fluorescence = reader.readFluorescence(
    485.0,  // excitation
    528.0   // emission
);

// Read luminescence
ReadingResult luminescence = reader.readLuminescence(1.0); // 1 second integration

// Access results
Map<String, Double> wellReadings = absorbance.wellReadings();
double valueA1 = wellReadings.get("A1");

reader.stop();
```

## Reading Result

```java
public record ReadingResult(
    String readingType,              // "absorbance", "fluorescence", "luminescence"
    Map<String, Double> wellReadings // "A1" → 0.523
) {}
```

## Backend Interface

```java
public interface PlateReaderBackend {
    void initialize() throws Exception;
    ReadingResult readAbsorbance(double wavelength) throws Exception;
    ReadingResult readFluorescence(double excitation, double emission) throws Exception;
    ReadingResult readLuminescence(double integrationTime) throws Exception;
    void shutdown();
}
```

## Implementing Custom Backend

```java
public class MyReaderBackend implements PlateReaderBackend {
    @Override
    public ReadingResult readAbsorbance(double wavelength) throws Exception {
        // Send command to reader
        // Parse response
        Map<String, Double> readings = parseReadings();
        return new ReadingResult("absorbance", readings);
    }
}
```

## Status

- ✅ Interface defined
- ✅ CLARIOstar stub (needs hardware)
- ⏳ Other plate readers (add as needed)

## Dependencies

- `jlabrobot-core` - Resource
- `jlabrobot-resources` - Plate
