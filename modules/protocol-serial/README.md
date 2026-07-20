# Serial Protocol Module

RS-232 serial communication for lab equipment.

## Implementation

Uses **jSerialComm** library - cross-platform, pure Java serial port access.

## Usage

```java
// Default baud rate (9600)
SerialChannel channel = new SerialChannel();

// Custom baud rate
SerialChannel channel = new SerialChannel(38400); // Hamilton typical

// Connect to port
channel.connect("/dev/ttyUSB0");  // Linux
channel.connect("COM3");          // Windows

// Send/receive
channel.send("C0RFid0001".getBytes());
byte[] response = channel.receive(5000); // 5s timeout

channel.disconnect();
```

## Port Discovery

```java
// List available ports
List<String> ports = SerialPortUtil.listAvailablePorts();
// → ["/dev/ttyUSB0", "/dev/ttyUSB1"]

// Print with descriptions
SerialPortUtil.printAvailablePorts();
// Available serial ports:
//   /dev/ttyUSB0 - USB-Serial Controller
//   COM3 - Communications Port (COM3)
```

## Configuration

- **Baud rate**: Configurable (default 9600)
- **Data bits**: 8
- **Stop bits**: 1
- **Parity**: None
- **Timeout**: Blocking read with timeout

## Common Baud Rates

- Hamilton STAR: 38400
- Tecan EVO: 9600 (if serial adapter used)
- Generic devices: 9600, 19200, 115200

## Platform Notes

**Linux**: Ports named `/dev/ttyUSB*` or `/dev/ttyACM*`
- May need permissions: `sudo usermod -a -G dialout $USER`

**Windows**: Ports named `COM1`, `COM3`, etc.
- Check Device Manager for port number

**macOS**: Ports named `/dev/cu.usbserial-*`

## Dependencies

- jSerialComm 2.10.4
