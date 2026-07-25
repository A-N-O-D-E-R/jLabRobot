package io.jlabrobot.protocol;

import java.io.IOException;

/**
 * Core abstraction for device communication across different protocols.
 *
 * <p>Defines the contract for establishing connections, sending commands, and receiving responses
 * from laboratory devices. Implementations support TCP, serial, USB, and other transport mechanisms
 * while maintaining a unified interface for the protocol layer and above.
 *
 * <p>Implementing classes should handle protocol-specific details such as connection setup,
 * error handling, and timeout behavior while conforming to this common interface.
 */
public interface ProtocolChannel {
    /**
     * Establishes a connection to a device using the provided address.
     *
     * <p>The address format varies by protocol implementation:
     * <ul>
     *   <li>TCP: "host:port" (e.g., "192.168.1.100:5000")</li>
     *   <li>Serial: port name (e.g., "/dev/ttyUSB0" or "COM3")</li>
     *   <li>USB: "VID:PID" (e.g., "0x04b4:0x0001")</li>
     * </ul>
     *
     * @param address the device address string in protocol-specific format
     * @throws IOException if connection fails or the device is unreachable
     */
    void connect(String address) throws IOException;

    /**
     * Sends data to the connected device.
     *
     * @param data the byte array to transmit
     * @throws IOException if transmission fails or the channel is not connected
     */
    void send(byte[] data) throws IOException;

    /**
     * Receives data from the connected device with a specified timeout.
     *
     * @param timeout the maximum wait time in milliseconds for data to arrive
     * @return the received byte array; may be empty if no data is received within timeout
     * @throws IOException if reception fails or the channel is not connected
     */
    byte[] receive(int timeout) throws IOException;

    /**
     * Checks whether the channel is currently connected to a device.
     *
     * @return {@code true} if connected and operational; {@code false} otherwise
     */
    boolean isConnected();

    /**
     * Closes the connection to the device and releases associated resources.
     *
     * <p>This method should be idempotent—calling it multiple times should not throw exceptions.
     * After disconnection, the channel should not be reused without a new {@link #connect(String)} call.
     */
    void disconnect();
}
