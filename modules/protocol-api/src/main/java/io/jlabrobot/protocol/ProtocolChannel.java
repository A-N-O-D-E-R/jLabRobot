package io.jlabrobot.protocol;

import java.io.IOException;

public interface ProtocolChannel {
    void connect(String address) throws IOException;
    void send(byte[] data) throws IOException;
    byte[] receive(int timeout) throws IOException;
    boolean isConnected();
    void disconnect();
}
