package io.jlabrobot.protocol.serial;

import com.fazecast.jSerialComm.SerialPort;
import io.jlabrobot.protocol.ProtocolChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Serial port protocol channel implementation for RS-232/RS-485 device communication.
 *
 * <p>Manages serial port connections with configurable baud rates, data bits, and parity settings.
 * Uses the jSerialComm library for cross-platform serial communication with standard configuration:
 * 8 data bits, 1 stop bit, no parity.
 *
 * <p>Usage example:
 * <pre>
 * SerialChannel channel = new SerialChannel(9600); // 9600 baud
 * channel.connect("/dev/ttyUSB0");
 * channel.send(commandData);
 * byte[] response = channel.receive(5000); // 5-second timeout
 * channel.disconnect();
 * </pre>
 */
public class SerialChannel implements ProtocolChannel {
    private static final Logger log = LoggerFactory.getLogger(SerialChannel.class);

    private final int baudRate;
    private SerialPort port;

    /**
     * Constructs a SerialChannel with the default baud rate of 9600.
     */
    public SerialChannel() {
        this(9600); // ponytail: common default, override via constructor when needed
    }

    /**
     * Constructs a SerialChannel with the specified baud rate.
     *
     * @param baudRate the communication speed in bits per second (e.g., 9600, 115200)
     */
    public SerialChannel(int baudRate) {
        this.baudRate = baudRate;
    }

    /**
     * Opens the serial port at the specified address with the configured baud rate.
     *
     * <p>The address is the system port name (e.g., "/dev/ttyUSB0" on Linux, "COM3" on Windows).
     * Configures standard settings: 8 data bits, 1 stop bit, no parity.
     *
     * @param address the serial port name or path
     * @throws IOException if the port cannot be opened or configured
     */
    @Override
    public void connect(String address) throws IOException {
        log.info("Opening serial port {} at {} baud", address, baudRate);
        port = SerialPort.getCommPort(address);

        port.setBaudRate(baudRate);
        port.setNumDataBits(8);
        port.setNumStopBits(1);
        port.setParity(SerialPort.NO_PARITY);

        if (!port.openPort()) {
            throw new IOException("Failed to open serial port: " + address);
        }
    }

    /**
     * Sends data to the serial device.
     *
     * <p>Ensures all bytes are written; throws an exception if the write is incomplete.
     *
     * @param data the byte array to transmit
     * @throws IOException if the port is not open or not all bytes can be written
     */
    @Override
    public void send(byte[] data) throws IOException {
        if (!isConnected()) {
            throw new IOException("Port not open");
        }

        int written = port.writeBytes(data, data.length);
        if (written != data.length) {
            throw new IOException("Failed to write all bytes");
        }
    }

    /**
     * Receives data from the serial device with a blocking timeout.
     *
     * <p>Sets the port read timeout and waits for data to arrive. Returns immediately
     * if data is available, or after the timeout expires.
     *
     * @param timeoutMillis the maximum wait time in milliseconds for data arrival
     * @return the received data as a byte array; may be empty if timeout expires with no data
     * @throws IOException if the port is not open
     */
    @Override
    public byte[] receive(int timeoutMillis) throws IOException {
        if (!isConnected()) {
            throw new IOException("Port not open");
        }

        port.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, timeoutMillis, 0);
        byte[] buffer = new byte[4096];
        int bytesRead = port.readBytes(buffer, buffer.length);

        byte[] result = new byte[bytesRead];
        System.arraycopy(buffer, 0, result, 0, bytesRead);
        return result;
    }

    /**
     * Checks if the serial port is currently open and operational.
     *
     * @return {@code true} if the port is open; {@code false} otherwise
     */
    @Override
    public boolean isConnected() {
        return port != null && port.isOpen();
    }

    /**
     * Closes the serial port and releases resources.
     *
     * <p>This method is idempotent and safe to call multiple times.
     */
    @Override
    public void disconnect() {
        if (port != null && port.isOpen()) {
            port.closePort();
            port = null;
        }
    }
}
