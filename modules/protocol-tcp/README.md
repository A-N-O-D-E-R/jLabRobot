# TCP Protocol Module

TCP/IP socket communication for network-connected lab equipment.

## Implementation

Uses Java 11 `HttpClient` (standard library - no dependencies).

## Usage

```java
TcpChannel channel = new TcpChannel();

// Connect to device
channel.connect("192.168.1.100:5000");

// Send command
String command = "C0RFid0001";
channel.send(command.getBytes());

// Receive response
byte[] response = channel.receive(5000); // 5 second timeout
String responseStr = new String(response);

channel.disconnect();
```

## Address Format

- **IP:Port**: `192.168.1.100:5000`
- **Hostname:Port**: `robot.local:8080`
- **Default ports**: Backend-specific (Hamilton: varies, Tecan: not applicable)

## Use Cases

- Hamilton STAR with TCP/IP communication module
- Network-connected plate readers
- Remote robot control

## Timeout

- Connection timeout: 30 seconds (hardcoded)
- Read timeout: Specified per `receive()` call

## Dependencies

- Java 11+ (HttpClient in stdlib)
