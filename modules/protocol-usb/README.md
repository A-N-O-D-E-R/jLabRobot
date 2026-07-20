# USB Protocol Channel

Stub implementation for USB device communication. Requires hardware-specific integration.

## Status

**Not implemented** - stub throws `IOException`. Implement when hardware requires USB.

## Implementation Options

### Option 1: usb4java (Recommended)
Pure Java wrapper around libusb. Cross-platform.

```xml
<dependency>
    <groupId>org.usb4java</groupId>
    <artifactId>usb4java</artifactId>
    <version>1.3.0</version>
</dependency>
```

**Usage:**
```java
Context context = new Context();
LibUsb.init(context);

DeviceList list = new DeviceList();
LibUsb.getDeviceList(context, list);

// Find device by vendor/product ID
Device device = findDevice(list, vendorId, productId);

DeviceHandle handle = new DeviceHandle();
LibUsb.open(device, handle);

// Bulk transfer
ByteBuffer buffer = ByteBuffer.allocateDirect(64);
buffer.put(data);
IntBuffer transferred = IntBuffer.allocate(1);
LibUsb.bulkTransfer(handle, endpoint, buffer, transferred, timeout);
```

### Option 2: javax.usb (JSR-80)
Older Java USB API. Requires native implementation.

```xml
<dependency>
    <groupId>javax.usb</groupId>
    <artifactId>usb-api</artifactId>
    <version>1.0.2</version>
</dependency>
```

Less active, harder to set up cross-platform.

## Typical USB Device Communication

1. **Enumerate devices** by vendor/product ID
2. **Claim interface** (exclusive access)
3. **Bulk/Interrupt transfers** on endpoints
   - OUT endpoint: send data
   - IN endpoint: receive data
4. **Release interface** and close

## Integration Steps

1. Add usb4java dependency to pom.xml
2. Implement device enumeration in `UsbDeviceUtil`
3. Complete `UsbChannel.connect()` - parse address as `vendorId:productId`
4. Implement `send()` - bulk transfer to OUT endpoint
5. Implement `receive()` - bulk transfer from IN endpoint with timeout
6. Add `UsbChannel.isConnected()` check
7. Implement `disconnect()` - release interface, close handle

## Address Format

Proposal: `{vendorId}:{productId}` hex format

```java
// Example: Hamilton with VID 0x1234, PID 0x5678
channel.connect("1234:5678");
```

Or specific device path: `/dev/bus/usb/001/002` (Linux)

## Testing

Requires physical USB device. Test with:
```bash
# Linux: list USB devices
lsusb

# Check permissions
ls -l /dev/bus/usb/001/002
# May need udev rule for non-root access
```

## See Also
- usb4java: http://usb4java.org/
- libusb: https://libusb.info/
- USB specifications: https://www.usb.org/documents
