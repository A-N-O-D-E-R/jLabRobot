package io.jlabrobot.protocol.serial;

import com.fazecast.jSerialComm.SerialPort;

import java.util.Arrays;
import java.util.List;

/**
 * Utility class for enumerating and managing available serial ports.
 *
 * <p>Provides convenience methods to discover connected serial devices and their descriptive names.
 * Useful for configuration dialogs, device discovery, and troubleshooting connection issues.
 */
public class SerialPortUtil {
    /**
     * Returns a list of all available serial port names on the system.
     *
     * <p>Common port names include "/dev/ttyUSB0" on Linux, "/dev/tty.usbserial*" on macOS,
     * and "COM1", "COM3", etc. on Windows.
     *
     * @return an unmodifiable list of system port names; may be empty if no ports are available
     */
    public static List<String> listAvailablePorts() {
        return Arrays.stream(SerialPort.getCommPorts())
                .map(SerialPort::getSystemPortName)
                .toList();
    }

    /**
     * Prints all available serial ports to standard output with their descriptive names.
     *
     * <p>Useful for interactive device discovery and debugging. Output example:
     * <pre>
     * Available serial ports:
     *   /dev/ttyUSB0 - USB-to-Serial Adapter
     *   /dev/ttyUSB1 - Arduino Uno
     * </pre>
     * If no ports are found, prints a message indicating this.
     */
    public static void printAvailablePorts() {
        SerialPort[] ports = SerialPort.getCommPorts();
        if (ports.length == 0) {
            System.out.println("No serial ports found");
            return;
        }

        System.out.println("Available serial ports:");
        for (SerialPort port : ports) {
            System.out.printf("  %s - %s\n", port.getSystemPortName(), port.getDescriptivePortName());
        }
    }
}
