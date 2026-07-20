# Protocol API Module

Communication channel abstraction for hardware protocols.

## Interface

```java
public interface ProtocolChannel {
    void connect(String address) throws IOException;
    void send(byte[] data) throws IOException;
    byte[] receive(int timeoutMillis) throws IOException;
    boolean isConnected();
    void disconnect();
}
```

## Implementations

| Channel | Protocol | Use Case |
|---------|----------|----------|
| `TcpChannel` | TCP/IP | Network-connected devices |
| `SerialChannel` | RS-232 | Serial port (COM/ttyUSB) |
| `UsbChannel` | USB bulk | Direct USB devices |

## Usage

```java
// TCP connection
ProtocolChannel channel = new TcpChannel();
channel.connect("192.168.1.100:5000");

// Serial connection
ProtocolChannel channel = new SerialChannel(38400); // baud rate
channel.connect("/dev/ttyUSB0");

// USB connection
ProtocolChannel channel = new UsbChannel();
channel.connect("0C47:4000"); // vendor:product ID

// Send/receive
channel.send("C0RFid0001".getBytes());
byte[] response = channel.receive(5000); // 5 second timeout

channel.disconnect();
```

## Backend Integration

Backends use ProtocolChannel for hardware communication:

```java
public class HamiltonBackend implements Backend {
    private final ProtocolChannel channel;

    public HamiltonBackend(ProtocolChannel channel, String address) {
        this.channel = channel;
        this.address = address;
    }

    @Override
    public void initialize() throws BackendException {
        channel.connect(address);
        // Send initialization commands
    }
}
```

## Design Pattern

**Abstraction**: Separates protocol details from backend logic.
- Backend translates commands to protocol format
- ProtocolChannel handles transport layer

## Dependencies

None - pure Java 17
