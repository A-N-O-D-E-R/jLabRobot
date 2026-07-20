package io.jlabrobot.protocol.usb;

/**
 * USB device identifier for enumeration.
 *
 * @param vendorId USB vendor ID (VID) in hex
 * @param productId USB product ID (PID) in hex
 * @param devicePath System-specific path (e.g., "/dev/bus/usb/001/002")
 */
public record UsbDeviceInfo(int vendorId, int productId, String devicePath) {

    public static UsbDeviceInfo fromAddress(String address) {
        // ponytail: parse "VID:PID" format, add path parsing when needed
        String[] parts = address.split(":");
        if (parts.length != 2) {
            throw new IllegalArgumentException("USB address format: vendorId:productId (hex)");
        }

        int vid = Integer.parseInt(parts[0], 16);
        int pid = Integer.parseInt(parts[1], 16);
        return new UsbDeviceInfo(vid, pid, null);
    }

    @Override
    public String toString() {
        return String.format("%04X:%04X", vendorId, productId);
    }
}
