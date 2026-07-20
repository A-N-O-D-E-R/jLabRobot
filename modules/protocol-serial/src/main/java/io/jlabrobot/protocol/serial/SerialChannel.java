package io.jlabrobot.protocol.serial;

import com.fazecast.jSerialComm.SerialPort;
import io.jlabrobot.protocol.ProtocolChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class SerialChannel implements ProtocolChannel {
    private static final Logger log = LoggerFactory.getLogger(SerialChannel.class);

    private final int baudRate;
    private SerialPort port;

    public SerialChannel() {
        this(9600); // ponytail: common default, override via constructor when needed
    }

    public SerialChannel(int baudRate) {
        this.baudRate = baudRate;
    }

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

    @Override
    public boolean isConnected() {
        return port != null && port.isOpen();
    }

    @Override
    public void disconnect() {
        if (port != null && port.isOpen()) {
            port.closePort();
            port = null;
        }
    }
}
