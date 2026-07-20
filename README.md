# jLabRobot

Java port of [PyLabRobot](https://github.com/PyLabRobot/pylabrobot) for lab automation.

## Quick Start

```bash
# Build
mvn clean install

# Run example
mvn exec:java -pl examples -Dexec.mainClass="io.jlabrobot.examples.BasicLiquidTransfer"
```

## Example

```java
Backend backend = new HamiltonBackend(new SerialChannel(), "/dev/ttyUSB0");
LiquidHandler lh = new LiquidHandler(deck, backend);

lh.initialize();
lh.pickUpTips(tips);
lh.aspirate(sourceWells, volumes);
lh.dispense(destWells, volumes);
lh.dropTips(tips);
lh.shutdown();
```

## Architecture

**24 Maven modules** organized by function:

| Layer | Modules |
|-------|---------|
| **Core** | `core`, `resources` |
| **Liquid Handling** | `liquid-handling` |
| **Backends** | `backend-{api,hamilton,tecan,opentrons,chatterbox}` |
| **Protocols** | `protocol-{api,tcp,serial,usb}` |
| **Equipment** | `plate-reading`, `heating-shaking`, `centrifuge`, `scale`, `pump`, `thermocycler`, `incubator`, `washer`, `machines` |
| **Utilities** | `visualization`, `integration-tests` |
| **Examples** | `examples` (14 working programs) |

Each module has a README - see `modules/*/README.md`

## Documentation

| File | Description |
|------|-------------|
| **[COORDINATES.md](COORDINATES.md)** | Coordinate system, calibration, deck layout |
| **[IMPLEMENTATION.md](IMPLEMENTATION.md)** | Complete implementation details, all 10 tasks |
| `modules/backend-hamilton/PROTOCOL.md` | Hamilton STAR firmware protocol |
| `modules/backend-tecan/PROTOCOL.md` | Tecan EVO USB protocol |
| `modules/backend-opentrons/README.md` | Opentrons Python script generation |
| `modules/integration-tests/README.md` | Hardware testing guide |

## Key Features

✅ **Pluggable backends** - Hamilton STAR, Tecan EVO, Opentrons OT-2  
✅ **Serial + TCP protocols** - jSerialComm, Java HttpClient  
✅ **Single + 96-channel** - LiquidHandler, LiquidHandler96  
✅ **Visualization** - ASCII deck renderer  
✅ **14 examples** - Basic transfer to complex workflows  
✅ **Hardware tests** - Integration test templates with `@Tag("hardware")`  
✅ **101 Java files** - 9 test suites, all passing

## Build & Test

```bash
mvn clean install          # Full build (8-9 seconds)
mvn test                   # Unit tests only
mvn verify -Phardware      # Hardware integration tests
```

**Requirements**: Java 17+, Maven 3.9+

## Design Patterns

- **Composite**: Resource hierarchy (Deck → Carriers → Plates → Wells)
- **Strategy**: Pluggable backends via dependency injection
- **Simplicity**: Stdlib over frameworks (ponytail mode - see code comments)

## License

Mirrors [PyLabRobot](https://github.com/PyLabRobot/pylabrobot) licensing.
