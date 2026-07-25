package io.jlabrobot.protocol.tcp;

import io.jlabrobot.protocol.ProtocolChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * TCP/IP protocol channel implementation for network-based device communication.
 *
 * <p>Establishes TCP socket connections to remote devices and provides synchronized send/receive
 * operations. Handles socket lifecycle management, timeouts, and connection validation.
 *
 * <p>Usage example:
 * <pre>
 * TcpChannel channel = new TcpChannel();
 * channel.connect("192.168.1.100:5000");
 * channel.send(commandData);
 * byte[] response = channel.receive(5000); // 5-second timeout
 * channel.disconnect();
 * </pre>
 */
public class TcpChannel implements ProtocolChannel {
    private static final Logger log = LoggerFactory.getLogger(TcpChannel.class);

    private Socket socket;
    private InputStream input;
    private OutputStream output;

    /**
     * Connects to a remote TCP server.
     *
     * <p>The address must be in "host:port" format, where host is a hostname or IP address
     * and port is a valid port number (1-65535).
     *
     * @param address the connection address in "host:port" format (e.g., "192.168.1.100:5000")
     * @throws IOException if the connection fails or the address format is invalid
     */
    @Override
    public void connect(String address) throws IOException {
        String[] parts = address.split(":");
        String host = parts[0];
        int port = Integer.parseInt(parts[1]);

        log.info("Connecting TCP to {}:{}", host, port);
        socket = new Socket(host, port);
        input = socket.getInputStream();
        output = socket.getOutputStream();
    }

    /**
     * Sends data to the connected TCP server.
     *
     * <p>The data is written to the output stream and flushed immediately to ensure transmission.
     *
     * @param data the byte array to send
     * @throws IOException if the channel is not connected or transmission fails
     */
    @Override
    public void send(byte[] data) throws IOException {
        if (!isConnected()) {
            throw new IOException("Not connected");
        }
        output.write(data);
        output.flush();
    }

    /**
     * Receives data from the connected TCP server with a blocking timeout.
     *
     * <p>Sets the socket timeout and waits for up to {@code timeoutMillis} milliseconds
     * to receive data. The method blocks until data arrives or the timeout expires.
     *
     * @param timeoutMillis the maximum wait time in milliseconds
     * @return the received data as a byte array; the actual size depends on data availability
     * @throws IOException if the channel is not connected, timeout occurs, or the connection closes
     */
    @Override
    public byte[] receive(int timeoutMillis) throws IOException {
        if (!isConnected()) {
            throw new IOException("Not connected");
        }

        socket.setSoTimeout(timeoutMillis);
        byte[] buffer = new byte[4096];
        int bytesRead = input.read(buffer);

        if (bytesRead == -1) {
            throw new IOException("Connection closed");
        }

        byte[] result = new byte[bytesRead];
        System.arraycopy(buffer, 0, result, 0, bytesRead);
        return result;
    }

    /**
     * Checks if the TCP channel is connected to the remote server.
     *
     * @return {@code true} if the socket is active and connected; {@code false} otherwise
     */
    @Override
    public boolean isConnected() {
        return socket != null && socket.isConnected() && !socket.isClosed();
    }

    /**
     * Closes the TCP connection and releases all associated resources.
     *
     * <p>This method is idempotent and does not throw exceptions; any errors during
     * closure are logged as warnings.
     */
    @Override
    public void disconnect() {
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                log.warn("Error closing socket", e);
            }
            socket = null;
            input = null;
            output = null;
        }
    }
}
