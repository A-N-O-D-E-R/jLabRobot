package io.jlabrobot.protocol.usb;

/**
 * Immutable record containing USB device identification information.
 *
 * <p>Used to identify USB devices by their vendor and product IDs for enumeration and connection.
 * The device path is system-specific and populated when device discovery is fully implemented.
 *
 * @param vendorId the USB vendor ID (VID) as a decimal integer (parsed from hex)
 * @param productId the USB product ID (PID) as a decimal integer (parsed from hex)
 * @param devicePath optional system-specific device path (e.g., "/dev/bus/usb/001/002" on Linux)
 */
public record UsbDeviceInfo(int vendorId, int productId, String devicePath) {

    /**
     * Parses a USB device address string in "VID:PID" format (hexadecimal) into a UsbDeviceInfo record.
     *
     * <p>The address format is "vendorId:productId" where both values are hexadecimal strings
     * (e.g., "04b4:0001"). Currently, the device path is set to {@code null}.
     *
     * @param address the device address in "VID:PID" format
     * @return a UsbDeviceInfo record with the parsed vendor and product IDs
     * @throws IllegalArgumentException if the address format is invalid or cannot be parsed
     * @throws NumberFormatException if vendor or product ID cannot be parsed as hexadecimal
     */
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

    /**
     * Returns a string representation of the USB device identifier in "VID:PID" format.
     *
     * <p>Both values are displayed as 4-digit uppercase hexadecimal (e.g., "04B4:0001").
     *
     * @return the device identifier as a hexadecimal string
     */
    @Override
    public String toString() {
        return String.format("%04X:%04X", vendorId, productId);
    }
}
