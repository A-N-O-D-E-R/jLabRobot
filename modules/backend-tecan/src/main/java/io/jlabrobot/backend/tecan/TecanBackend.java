package io.jlabrobot.backend.tecan;

import io.jlabrobot.backend.Backend;
import io.jlabrobot.backend.BackendException;
import io.jlabrobot.backend.Command;
import io.jlabrobot.backend.CommandResult;
import io.jlabrobot.protocol.ProtocolChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * Backend for Tecan Freedom EVO liquid handlers.
 *
 * Protocol: Binary USB with STX/NUL framing
 * Format: \02{Module}{Command}{Param1},{Param2},...\00
 *
 * See modules/backend-tecan/PROTOCOL.md for details.
 */
public class TecanBackend implements Backend {
    private static final Logger log = LoggerFactory.getLogger(TecanBackend.class);

    private static final byte STX = 0x02;
    private static final byte NUL = 0x00;
    private static final String MODULE_LIHA = "C5";  // Liquid handling arm

    private final ProtocolChannel channel;
    private String address;
    private int numChannels = 8;  // ponytail: detect from RNT command

    public TecanBackend(ProtocolChannel channel, String address) {
        this.channel = channel;
        this.address = address;
    }

    @Override
    public void initialize() throws BackendException {
        try {
            log.info("Connecting to Tecan EVO at {}", address);
            channel.connect(address);

            // Initialize LiHa arm
            sendTecanCommand(MODULE_LIHA, "PIA", List.of());

            // Query number of channels
            CommandResult result = sendTecanCommand(MODULE_LIHA, "RNT", List.of());
            // ponytail: parse response when format validated
            log.info("Tecan EVO initialized");

        } catch (IOException e) {
            throw new BackendException("Failed to initialize Tecan", e);
        }
    }

    @Override
    public CommandResult executeCommand(Command cmd) throws BackendException {
        log.debug("Executing Tecan command: {}", cmd.name());

        try {
            String tecanCmd = getFirmwareCode(cmd.name());
            List<Integer> params = extractParams(cmd.parameters());

            return sendTecanCommand(MODULE_LIHA, tecanCmd, params);

        } catch (IOException e) {
            throw new BackendException("Tecan command failed", e);
        }
    }

    @Override
    public void shutdown() {
        if (channel.isConnected()) {
            channel.disconnect();
        }
        log.info("Tecan backend shutdown");
    }

    private CommandResult sendTecanCommand(String module, String command, List<Integer> params)
            throws IOException {

        String cmdStr = buildCommand(module, command, params);
        byte[] cmdBytes = cmdStr.getBytes(StandardCharsets.UTF_8);

        channel.send(cmdBytes);
        byte[] response = channel.receive(5000);

        return parseResponse(response);
    }

    private String buildCommand(String module, String command, List<Integer> params) {
        StringBuilder sb = new StringBuilder();
        sb.append((char) STX);
        sb.append(module);
        sb.append(command);

        for (int i = 0; i < params.size(); i++) {
            Integer param = params.get(i);
            if (param != null) {
                sb.append(param);
            }
            // Empty string for null params
            if (i < params.size() - 1) {
                sb.append(',');
            }
        }

        sb.append((char) NUL);
        return sb.toString();
    }

    private CommandResult parseResponse(byte[] response) {
        String resp = new String(response, StandardCharsets.UTF_8);

        if (resp.length() < 4) {
            return CommandResult.failure("Invalid response length");
        }

        // Module echo (chars 0-1)
        String module = resp.substring(0, 2);

        // Return code (char 2) XOR 0x80
        int returnCode = (int) resp.charAt(2) ^ 0x80;

        if (returnCode != 0) {
            return CommandResult.failure("Tecan error code: " + returnCode + " (module: " + module + ")");
        }

        // Data (chars 3+ until NUL)
        String data = resp.substring(3).replace(String.valueOf((char) NUL), "");

        return CommandResult.success("OK", data);
    }

    private String getFirmwareCode(String cmdName) {
        // ponytail: stub mapping, fill from EVO firmware docs
        return switch (cmdName) {
            case "pick_up_tips" -> "GDP";  // Get disposable tip
            case "aspirate" -> "MTR";      // Move tracking relative (aspirate)
            case "dispense" -> "MTR";      // Move tracking relative (dispense)
            case "drop_tips" -> "DDP";     // Drop disposable tip
            default -> "XXX";
        };
    }

    private List<Integer> extractParams(Map<String, Object> parameters) {
        // ponytail: convert command params to Tecan param list
        // Format depends on command - needs per-command logic
        return List.of();
    }
}

