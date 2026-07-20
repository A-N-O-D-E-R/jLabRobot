package io.jlabrobot.backend.hamilton;

import io.jlabrobot.backend.Backend;
import io.jlabrobot.backend.BackendException;
import io.jlabrobot.backend.Command;
import io.jlabrobot.backend.CommandResult;
import io.jlabrobot.protocol.ProtocolChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class HamiltonBackend implements Backend {
    private static final Logger log = LoggerFactory.getLogger(HamiltonBackend.class);

    private final ProtocolChannel channel;
    private final String address;
    private final AtomicInteger commandId = new AtomicInteger(1);

    public HamiltonBackend(ProtocolChannel channel, String address) {
        this.channel = channel;
        this.address = address;
    }

    @Override
    public void initialize() throws BackendException {
        try {
            log.info("Connecting to Hamilton at {}", address);
            channel.connect(address);

            String version = requestFirmwareVersion();
            log.info("Hamilton connected, firmware: {}", version);
        } catch (IOException e) {
            throw new BackendException("Failed to connect to Hamilton", e);
        }
    }

    @Override
    public CommandResult executeCommand(Command cmd) throws BackendException {
        log.debug("Executing command: {}", cmd.name());

        try {
            String fwCommand = buildFirmwareCommand(cmd);
            channel.send(fwCommand.getBytes());

            byte[] response = channel.receive(5000);
            return parseResponse(new String(response));
        } catch (IOException e) {
            throw new BackendException("Command execution failed", e);
        }
    }

    @Override
    public void shutdown() {
        if (channel.isConnected()) {
            channel.disconnect();
        }
    }

    private String requestFirmwareVersion() throws IOException {
        String cmd = "C0RFid" + String.format("%04d", commandId.getAndIncrement());
        channel.send(cmd.getBytes());
        byte[] resp = channel.receive(5000);
        return new String(resp); // ponytail: parse when real format known
    }

    private String buildFirmwareCommand(Command cmd) {
        StringBuilder sb = new StringBuilder();
        sb.append("C0");  // ponytail: hardcode master module, extract from cmd when needed
        sb.append(getFirmwareCode(cmd.name()));
        sb.append("id").append(String.format("%04d", commandId.getAndIncrement()));

        for (Map.Entry<String, Object> entry : cmd.parameters().entrySet()) {
            sb.append(entry.getKey());  // ponytail: assume 2-char keys already
            sb.append(formatValue(entry.getValue()));
        }

        return sb.toString();
    }

    private String getFirmwareCode(String cmdName) {
        // ponytail: stub mapping, fill from VENUS docs
        return switch (cmdName) {
            case "pick_up_tips" -> "PT";
            case "aspirate" -> "AP";
            case "dispense" -> "DP";
            case "drop_tips" -> "DT";
            default -> "XX";
        };
    }

    private String formatValue(Object value) {
        if (value instanceof Integer || value instanceof Long) {
            return String.format("%04d", ((Number) value).intValue());
        }
        if (value instanceof Boolean) {
            return (Boolean) value ? "1" : "0";
        }
        return value.toString();
    }

    private CommandResult parseResponse(String response) {
        // ponytail: stub parser, add format-string parser when protocol validated
        if (response.contains("er")) {
            return CommandResult.failure("Hamilton error: " + response);
        }
        return CommandResult.success("OK", response);
    }
}
