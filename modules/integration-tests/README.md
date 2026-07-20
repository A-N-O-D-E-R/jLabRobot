# Hardware Integration Tests

Tests that require physical lab equipment. Tagged with `@Tag("hardware")` and excluded from default `mvn test`.

## Running Hardware Tests

```bash
# Run all hardware tests
mvn verify -Phardware

# Run specific test class
mvn test -Dtest=HardwareHamiltonTest

# Skip hardware tests (default)
mvn test
```

## Environment Setup

### Hamilton STAR
Set environment variable before running:
```bash
export HAMILTON_PORT=/dev/ttyUSB0  # Linux
export HAMILTON_PORT=COM3           # Windows
mvn test -Dtest=HardwareHamiltonTest
```

Baud rate: 38400 (typical for Hamilton STAR)

### BMG CLARIOstar Plate Reader
Connection details in test configuration.

## Test Structure

All hardware tests:
1. Marked with `@Tag("hardware")`
2. Optional: `@EnabledIfEnvironmentVariable` for connection details
3. Use `@BeforeEach` to initialize hardware
4. Use `@AfterEach` to clean up (shutdown, disconnect)

## Creating New Hardware Tests

Template:
```java
@Tag("hardware")
@EnabledIfEnvironmentVariable(named = "DEVICE_PORT", matches = ".+")
class HardwareDeviceTest {
    private DeviceBackend backend;

    @BeforeEach
    void setup() throws Exception {
        String port = System.getenv("DEVICE_PORT");
        backend = new DeviceBackend(port);
        backend.initialize();
    }

    @AfterEach
    void teardown() {
        if (backend != null) {
            backend.shutdown();
        }
    }

    @Test
    void testBasicOperation() throws Exception {
        // Test implementation
    }
}
```

## Safety Checklist

Before running hardware tests:
- [ ] Deck layout matches test assumptions
- [ ] Tip racks have tips loaded
- [ ] Source wells contain test liquid
- [ ] No collision hazards on deck
- [ ] Emergency stop accessible
- [ ] Test runs in manual/setup mode first

## Available Tests

- `HardwareHamiltonTest` - Hamilton STAR liquid handler
- `HardwarePlateReaderTest` - BMG CLARIOstar plate reader

## Troubleshooting

**Connection failures**:
- Check port name: `ls /dev/tty*` (Linux) or Device Manager (Windows)
- Verify baud rate matches device settings
- Check cable and power

**Command failures**:
- Verify deck positions in test match physical setup
- Check firmware version compatibility
- Enable debug logging: `-Dorg.slf4j.simpleLogger.defaultLogLevel=debug`

**Unexpected behavior**:
- Review vendor documentation for command format
- Compare PyLabRobot implementation for reference
- Test with vendor software first to isolate jLabRobot vs hardware issue
