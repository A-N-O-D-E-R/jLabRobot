# Tecan EVO Protocol Format

Binary USB protocol for Tecan Freedom EVO liquid handlers.

## Command Structure

```
\02{Module}{Command}{Param1},{Param2},...{ParamN}\00
```

- **Start**: `\02` (STX - Start of Text, 0x02)
- **Module**: 2 characters (e.g., `C5` = LiHa arm, `C1` = RoMa arm)
- **Command**: 3 characters (e.g., `PID` = initialize plunger, `PAA` = position absolute)
- **Parameters**: Comma-separated integers (empty string for None/null)
- **End**: `\00` (NUL, 0x00)

## Example Commands

```
\02C5PID255\00                    # Initialize plunger, all tips (binary 11111111)
\02C5PAA450,1031,90\00           # Position absolute: X, Y, Y-spacing
\02C5RZP5\00                     # Report Z parameter 5 (machine range)
```

## Response Format

```
{Module}{ReturnCode},{Data1},{Data2},...
```

- **Module**: 2 chars echoing module
- **ReturnCode**: Integer XOR'd with 0x80
  - `0` = Success
  - Non-zero = Error code (see error_code_to_exception)
- **Data**: Comma-separated integers or strings

## Module IDs

- **C5**: LiHa (Liquid Handling Arm) - pipetting
- **C1**: RoMa (Robotic Manipulator Arm) - gripper/transport
- **C9**: MCA (Multi-Channel Arm) - 96-channel head

## Key LiHa Commands

### Initialization
- `PIA` - Position initialization all axes
- `PIB` - Position initialization B-axis
- `PID` - Initialize plunger (requires tip bitmask)

### Movement
- `PAA` - Position absolute all axes: X, Y, Y-spacing, [Z per channel]
- `MAZ` - Move absolute Z: [Z per channel]
- `SSZ` - Set slow speed Z: [speed per channel]
- `STH` - Set Z travel height: [height per channel]

### Liquid Handling
- `MTR` - Move tracking relative: [plunger per channel]
- `MPR` - Move plunger relative: [distance per channel]
- `PVL` - Position valve logical: [state per channel] (0=air, 1=liquid)
- `SEP` - Set end speed plunger: [speed per channel]

### Tip Handling
- `GDP` - Get disposable tip: tip_bitmask, Z_start, Z_max
- `DDP` - Drop disposable tip: tip_bitmask, discard_height

### Detection
- `MDL` - Move detect liquid: tip_bitmask, [Z_add per channel]
- `SDM` - Set detection mode: detection_process, conductivity

### Reporting
- `RXP` - Report X parameter: param_id (0=position, 5=range, etc.)
- `RYP` - Report Y parameter: param_id
- `RZP` - Report Z parameter: param_id
- `RNT` - Report number of tips

## Tip Bitmask Encoding

8 channels → 8-bit binary encoded as decimal:
```
Channel: 87654321
Binary:  11111111 = 255 (all tips)
Binary:  10000001 = 129 (tips 1 and 8)
Binary:  00001111 = 15  (tips 1-4)
```

## Connection

**USB**: Vendor ID `0x0C47`, Product ID `0x4000`

Uses bulk transfer on USB endpoints (not serial/TCP).

## Coordinate System

- **X**: Left-right (rail position)
- **Y**: Front-back
- **Z**: Up-down (per channel)
- **Units**: Tenths of millimeter (e.g., 450 = 45.0 mm)

## Error Handling

Response return code != 0 indicates error:
- Parse module + return_code
- Map to TecanError exception
- Common errors: 5 (axis not initialized), collision, timeout

## References

- PyLabRobot: `pylabrobot/liquid_handling/backends/tecan/EVO_backend.py`
- Tecan EVOware software documentation (proprietary)
- USB protocol via pylabrobot.io.usb module
