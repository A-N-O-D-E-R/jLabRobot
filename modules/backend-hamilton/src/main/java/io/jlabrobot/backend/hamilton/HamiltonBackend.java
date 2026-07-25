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

/**
 * Backend implementation for Hamilton liquid handling robots (VENUS protocol).
 *
 * Translates abstract jLabRobot commands into Hamilton-specific firmware commands,
 * manages serial/USB communication via a ProtocolChannel, and handles response parsing.
 * Supports Hamilton Star, Vantage, and Microlab series through VENUS automation software.
 *
 * Protocol: Serial/USB text-based with command/response pattern.
 * Format: C0{FirmwareCode}id{CommandId}{Param1}{Param2}...
 */
public class HamiltonBackend implements Backend {
    private static final Logger log = LoggerFactory.getLogger(HamiltonBackend.class);

    private final ProtocolChannel channel;
    private final String address;
    private final AtomicInteger commandId = new AtomicInteger(1);

    /**
     * Constructs a HamiltonBackend instance.
     *
     * @param channel the communication channel for sending commands and receiving responses
     * @param address the connection address (serial port, IP, or device identifier)
     */
    public HamiltonBackend(ProtocolChannel channel, String address) {
        this.channel = channel;
        this.address = address;
    }

    /**
     * Initializes the Hamilton backend by establishing connection and verifying firmware.
     *
     * Connects to the Hamilton device, queries firmware version, and verifies communication.
     *
     * @throws BackendException if connection fails or firmware query fails
     */
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

    /**
     * Executes a command on the Hamilton device.
     *
     * Translates the abstract command to Hamilton firmware format, sends it via the channel,
     * receives the response, and parses the result.
     *
     * @param cmd the command to execute
     * @return the result of command execution
     * @throws BackendException if communication or command execution fails
     */
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

    /**
     * Shuts down the Hamilton backend and closes the connection.
     */
    @Override
    public void shutdown() {
        if (channel.isConnected()) {
            channel.disconnect();
        }
    }

    /**
     * Queries the Hamilton device firmware version.
     *
     * @return the firmware version string returned by the device
     * @throws IOException if communication fails
     */
    private String requestFirmwareVersion() throws IOException {
        String cmd = "C0RFid" + String.format("%04d", commandId.getAndIncrement());
        channel.send(cmd.getBytes());
        byte[] resp = channel.receive(5000);
        return new String(resp); // ponytail: parse when real format known
    }

    /**
     * Builds a Hamilton firmware command string from an abstract Command.
     *
     * Encodes the command name as Hamilton firmware code, increments command ID,
     * and formats parameters according to Hamilton protocol specifications.
     *
     * @param cmd the abstract command to translate
     * @return the formatted Hamilton firmware command string
     */
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

    /**
     * Maps abstract command names to Hamilton firmware codes.
     *
     * @param cmdName the abstract command name
     * @return the corresponding Hamilton firmware code (2 characters)
     */
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

    /**
     * Formats a parameter value for Hamilton firmware protocol.
     *
     * Converts integers to 4-digit zero-padded strings, booleans to 0/1,
     * and other types to their string representation.
     *
     * @param value the value to format
     * @return the formatted value string
     */
    private String formatValue(Object value) {
        if (value instanceof Integer || value instanceof Long) {
            return String.format("%04d", ((Number) value).intValue());
        }
        if (value instanceof Boolean) {
            return (Boolean) value ? "1" : "0";
        }
        return value.toString();
    }

    /**
     * Parses a response string from the Hamilton device.
     *
     * Checks for error indicators in the response and constructs appropriate CommandResult.
     *
     * @param response the raw response string from the device
     * @return a CommandResult with success/failure status and parsed data
     */
    private CommandResult parseResponse(String response) {
        // ponytail: stub parser, add format-string parser when protocol validated
        if (response.contains("er")) {
            return CommandResult.failure("Hamilton error: " + response);
        }
        return CommandResult.success("OK", response);
    }
}
