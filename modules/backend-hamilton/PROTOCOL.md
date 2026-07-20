# Hamilton STAR Protocol Format

Text-based firmware protocol using 2-character codes.

## Command Structure

```
{Module}{Command}id{SequenceID}{Param}{Value}...
```

- **Module**: 2 chars (e.g., `C0` = master, `X0` = X-drive)
- **Command**: 2 chars (e.g., `RF` = request firmware, `QM` = query status)
- **id**: Literal "id" followed by 4-digit sequence number (e.g., `id0001`)
- **Parameters**: pairs of 2-char key + value

## Example Commands

```
C0RFid0001                        # Request firmware version
X0XPid0002la01000lr3lw7          # Move X-axis: la=position, lr=accel, lw=current
```

## Parameter Encoding

- **Decimals** (`####`): 4+ digits (e.g., `1000`)
- **Hex** (`***`): hex string (e.g., `B0B`)
- **Chars** (`&&`): character sequences (e.g., `rw`)
- **Booleans**: `1` = true, `0` = false
- **Lists**: one-hot encoded based on tip_pattern

## Response Format

Similar structure, parsed via format strings:
- `aa####` = decimal field named "aa"
- `bb&&` = 2-char field named "bb"
- `cc***` = hex field named "cc"

## Aspirate Command (Single Channel)

Command: `aspirate_pip` (actual 2-char code TBD from firmware docs)

Parameters (subset):
- `xp`: X position (tenths of mm)
- `yp`: Y position
- `zl`: Z height
- `av`: Aspiration volume
- `as`: Aspiration speed
- `ta`: Transport air volume

## References

- PyLabRobot: `pylabrobot/liquid_handling/backends/hamilton/STAR_backend.py`
- Hamilton VENUS firmware manual (proprietary)
