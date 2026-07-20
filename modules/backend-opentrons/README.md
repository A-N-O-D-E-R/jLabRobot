# Opentrons Backend

Java backend for Opentrons OT-2 and Flex liquid handling robots.

## Implementation Strategy

**Python Protocol Script Generation** - jLabRobot generates Python scripts using the Opentrons Protocol API, then executes them via:
1. `opentrons_simulate` - simulation mode (no hardware)
2. `opentrons_execute` - upload and run on physical robot
3. HTTP upload - direct API upload (future enhancement)

## Prerequisites

Install Opentrons Python package:

```bash
pip install opentrons
```

This provides:
- `opentrons_simulate` - protocol simulation
- `opentrons_execute` - protocol execution on robot
- Python API for protocol development

## Usage

### Simulation Mode (No Hardware)

```java
OpentronsBackend backend = new OpentronsBackend("simulate");
LiquidHandler lh = new LiquidHandler(deck, backend);

lh.initialize();
lh.pickUpTips(tips);
lh.aspirate(wells, volumes);
lh.dispense(wells, volumes);
lh.dropTips(tips);
lh.shutdown();  // Generates and simulates protocol
```

### Robot Execution

```java
OpentronsBackend backend = new OpentronsBackend("192.168.1.100");  // Robot IP
LiquidHandler lh = new LiquidHandler(deck, backend);

lh.initialize();
// ... commands ...
lh.shutdown();  // Uploads and executes on robot
```

## How It Works

1. **Command Queuing**: jLabRobot commands queue as Python API calls
2. **Script Generation**: On shutdown, generates Python protocol script
3. **Execution**: Runs via `opentrons_simulate` or `opentrons_execute`

### Generated Protocol Example

```python
from opentrons import protocol_api

metadata = {
    'protocolName': 'jLabRobot Protocol',
    'author': 'jLabRobot',
    'apiLevel': '2.13'
}

def run(protocol: protocol_api.ProtocolContext):
    tips = protocol.load_labware('opentrons_96_tiprack_300ul', '1')
    plate = protocol.load_labware('corning_96_wellplate_360ul_flat', '2')
    pipette = protocol.load_instrument('p300_single_gen2', 'right', tip_racks=[tips])

    # jLabRobot commands translated:
    pipette.pick_up_tip()
    pipette.aspirate(50.0, plate['A1'])
    pipette.dispense(50.0, plate['A2'])
    pipette.drop_tip()
```

## Command Translation

| jLabRobot | Opentrons API |
|-----------|---------------|
| `pick_up_tips()` | `pipette.pick_up_tip()` |
| `aspirate(well, volume)` | `pipette.aspirate(volume, plate['A1'])` |
| `dispense(well, volume)` | `pipette.dispense(volume, plate['A2'])` |
| `drop_tips()` | `pipette.drop_tip()` |

## Limitations

Current implementation:
- ✅ Script generation and simulation
- ✅ Single-channel pipette operations
- ⏳ 96-channel operations (requires protocol API changes)
- ⏳ Real-time command execution (uses batch mode)
- ⏳ Direct HTTP API upload (uses opentrons_execute)

## Alternative Approaches

### 1. Direct ot_api Integration (Python Required)

Use `ot_api` Python package via subprocess:

```java
// Call Python script that imports ot_api
ProcessBuilder pb = new ProcessBuilder("python3", "opentrons_wrapper.py", "aspirate", "50", "A1");
```

### 2. Jython/GraalVM (Embedded Python)

Embed Python interpreter in Java:

```java
// GraalVM Python
Context context = Context.newBuilder("python").build();
context.eval("python", "import ot_api; ot_api.runs.create()");
```

Requires GraalVM Python runtime.

### 3. HTTP Robot Server API (Undocumented)

Opentrons robot-server has HTTP endpoints, but they're not publicly documented:
- `/runs` - create/manage protocol runs
- `/commands` - queue commands
- `/protocols` - upload protocols

Would require reverse engineering or Opentrons API docs.

## Deck Setup

Opentrons uses numeric slots (1-11 for OT-2, 1-12 for Flex):

```java
// ponytail: map jLabRobot deck positions to Opentrons slots
// Example: tipRack at (0,0) → slot 1
//          plate at (150,0) → slot 2
```

## Labware Definitions

Opentrons has extensive labware library:
- `opentrons_96_tiprack_300ul`
- `corning_96_wellplate_360ul_flat`
- `nest_12_reservoir_15ml`

See: https://labware.opentrons.com/

## Testing

```bash
# Simulate protocol
opentrons_simulate protocol.py

# Execute on robot (requires Opentrons on network)
opentrons_execute -n 192.168.1.100 protocol.py
```

## Future Enhancements

- [ ] Real-time command execution via HTTP API
- [ ] 96-channel protocol generation
- [ ] Direct HTTP protocol upload
- [ ] Labware auto-detection from jLabRobot resources
- [ ] Deck position to slot mapping
- [ ] Error handling and protocol validation

## References

- **Opentrons Protocol API**: https://docs.opentrons.com/v2/
- **Python API Library**: https://pypi.org/project/opentrons/
- **Labware Library**: https://labware.opentrons.com/
- **Robot Server**: https://github.com/Opentrons/opentrons/tree/edge/robot-server
