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
 * Backend implementation for Tecan Freedom EVO liquid handlers.
 *
 * Manages communication with Tecan EVO liquid handling arms via binary USB protocol.
 * Translates abstract jLabRobot commands into Tecan firmware commands with STX/NUL framing,
 * handles parameter encoding, and parses device responses including error codes.
 *
 * Protocol: Binary USB with STX/NUL framing.
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

    /**
     * Constructs a TecanBackend instance.
     *
     * @param channel the communication channel for sending commands and receiving responses
     * @param address the device connection address (USB port, IP, or device identifier)
     */
    public TecanBackend(ProtocolChannel channel, String address) {
        this.channel = channel;
        this.address = address;
    }

    /**
     * Initializes the Tecan backend by establishing connection and configuring the liquid handling arm.
     *
     * Connects to the Tecan EVO device, initializes the LiHa arm (PIA command),
     * and queries the number of available channels (RNT command).
     *
     * @throws BackendException if connection or initialization fails
     */
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

    /**
     * Executes a command on the Tecan EVO device.
     *
     * Translates the abstract command to Tecan firmware code, extracts parameters,
     * and sends the command to the liquid handling arm module.
     *
     * @param cmd the command to execute
     * @return the result of command execution
     * @throws BackendException if communication or command execution fails
     */
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

    /**
     * Shuts down the Tecan backend and closes the connection.
     */
    @Override
    public void shutdown() {
        if (channel.isConnected()) {
            channel.disconnect();
        }
        log.info("Tecan backend shutdown");
    }

    /**
     * Sends a Tecan command to the device and returns the parsed response.
     *
     * @param module the module identifier (e.g., MODULE_LIHA)
     * @param command the 3-character Tecan firmware command code
     * @param params the command parameters as a list of integers
     * @return the result of command execution
     * @throws IOException if communication fails
     */
    private CommandResult sendTecanCommand(String module, String command, List<Integer> params)
            throws IOException {

        String cmdStr = buildCommand(module, command, params);
        byte[] cmdBytes = cmdStr.getBytes(StandardCharsets.UTF_8);

        channel.send(cmdBytes);
        byte[] response = channel.receive(5000);

        return parseResponse(response);
    }

    /**
     * Builds a Tecan command string with STX/NUL framing.
     *
     * Constructs the binary command format: STX + module + command + parameters + NUL.
     *
     * @param module the module identifier
     * @param command the command code
     * @param params the parameter values
     * @return the formatted command string
     */
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

    /**
     * Parses a Tecan device response.
     *
     * Extracts module echo, return code (XOR 0x80), and data payload.
     * A return code of 0 indicates success; non-zero values indicate errors.
     *
     * @param response the raw response bytes from the device
     * @return a CommandResult with success/failure status and parsed data
     */
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

    /**
     * Maps abstract command names to Tecan firmware codes.
     *
     * @param cmdName the abstract command name
     * @return the corresponding 3-character Tecan firmware code
     */
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

    /**
     * Extracts and converts command parameters to Tecan parameter list format.
     *
     * Translates parameter key-value pairs into the integer list format expected by Tecan commands.
     *
     * @param parameters the command parameters as a map
     * @return the formatted parameter list for Tecan firmware
     */
    private List<Integer> extractParams(Map<String, Object> parameters) {
        // ponytail: convert command params to Tecan param list
        // Format depends on command - needs per-command logic
        return List.of();
    }
}

