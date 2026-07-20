package io.jlabrobot.protocol.tcp;

import io.jlabrobot.protocol.ProtocolChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class TcpChannel implements ProtocolChannel {
    private static final Logger log = LoggerFactory.getLogger(TcpChannel.class);

    private Socket socket;
    private InputStream input;
    private OutputStream output;

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

    @Override
    public void send(byte[] data) throws IOException {
        if (!isConnected()) {
            throw new IOException("Not connected");
        }
        output.write(data);
        output.flush();
    }

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

    @Override
    public boolean isConnected() {
        return socket != null && socket.isConnected() && !socket.isClosed();
    }

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
