package io.jlabrobot.backend.opentrons;

import io.jlabrobot.backend.Backend;
import io.jlabrobot.backend.BackendException;
import io.jlabrobot.backend.Command;
import io.jlabrobot.backend.CommandResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Backend implementation for Opentrons OT-2 and Flex robots.
 *
 * Uses Python protocol script generation and execution to control Opentrons robots.
 * Accumulates abstract jLabRobot commands into an in-memory queue, translates them to
 * Opentrons Python API calls, writes to a temporary protocol script file, and executes
 * via the `opentrons_simulate` or `opentrons_execute` command-line tools.
 *
 * Implementation strategy: Python protocol script generation
 * - Generates Python protocol scripts using Opentrons API
 * - Executes via `opentrons_execute` command or uploads to robot
 *
 * Alternative approaches (not yet implemented):
 * - Use ProcessBuilder to call Python scripts that import ot_api
 * - Or: Embed Jython/GraalVM Python for direct ot_api calls
 *
 * See modules/backend-opentrons/README.md for setup instructions.
 */
public class OpentronsBackend implements Backend {
    private static final Logger log = LoggerFactory.getLogger(OpentronsBackend.class);

    private final String robotAddress;  // IP or "simulate" for simulation
    private final List<String> commandQueue;
    private Path protocolScript;
    private boolean initialized = false;

    /**
     * Constructs an OpentronsBackend instance.
     *
     * @param robotAddress the robot connection address: IP address for real robot or "simulate" for simulation mode
     */
    public OpentronsBackend(String robotAddress) {
        this.robotAddress = robotAddress;
        this.commandQueue = new ArrayList<>();
    }

    /**
     * Initializes the Opentrons backend by creating a temporary protocol script and writing the header.
     *
     * Sets up the Python environment and creates the Opentrons protocol metadata.
     *
     * @throws BackendException if script creation or header writing fails
     */
    @Override
    public void initialize() throws BackendException {
        log.info("Initializing Opentrons backend: {}", robotAddress);

        try {
            // Create temporary protocol script
            protocolScript = Files.createTempFile("jlabrobot_opentrons_", ".py");
            log.debug("Protocol script: {}", protocolScript);

            // Write protocol header
            writeProtocolHeader();

            initialized = true;
            log.info("Opentrons backend initialized (script generation mode)");

        } catch (IOException e) {
            throw new BackendException("Failed to initialize Opentrons", e);
        }
    }

    /**
     * Queues a command for batch execution on the Opentrons robot.
     *
     * Translates the abstract command to Opentrons Python API code and accumulates it.
     * The actual execution occurs when shutdown() is called, which writes all queued
     * commands to the protocol script and executes it.
     *
     * @param cmd the command to execute
     * @return a success result indicating the command was queued
     * @throws BackendException if the backend is not initialized
     */
    @Override
    public CommandResult executeCommand(Command cmd) throws BackendException {
        if (!initialized) {
            throw new BackendException("Backend not initialized");
        }

        log.debug("Queueing Opentrons command: {}", cmd.name());

        // Convert jLabRobot command to Opentrons Python API call
        String pythonCode = translateCommand(cmd);
        commandQueue.add(pythonCode);

        // For immediate execution, would need to flush and run
        // For batched execution, accumulate commands
        return CommandResult.success("Command queued: " + cmd.name());
    }

    /**
     * Shuts down the Opentrons backend, writing queued commands and executing the protocol.
     *
     * Writes all accumulated commands to the protocol script file and executes it via
     * the appropriate Opentrons command-line tool (simulate or execute).
     */
    @Override
    public void shutdown() {
        if (protocolScript != null && initialized) {
            try {
                // Write queued commands to script
                writeQueuedCommands();

                // Execute protocol
                if ("simulate".equals(robotAddress)) {
                    simulateProtocol();
                } else {
                    executeProtocol();
                }

                // Cleanup
                Files.deleteIfExists(protocolScript);
                log.info("Opentrons backend shutdown");

            } catch (IOException e) {
                log.error("Error during shutdown", e);
            }
        }
    }

    /**
     * Writes the Opentrons protocol header to the script file.
     *
     * Includes metadata, labware definitions, and instrument setup.
     *
     * @throws IOException if writing to the script file fails
     */
    private void writeProtocolHeader() throws IOException {
        String header = """
            from opentrons import protocol_api

            # Auto-generated by jLabRobot
            metadata = {
                'protocolName': 'jLabRobot Protocol',
                'author': 'jLabRobot',
                'apiLevel': '2.13'
            }

            def run(protocol: protocol_api.ProtocolContext):
                # Load labware
                tips = protocol.load_labware('opentrons_96_tiprack_300ul', '1')
                plate = protocol.load_labware('corning_96_wellplate_360ul_flat', '2')

                # Load instrument
                pipette = protocol.load_instrument('p300_single_gen2', 'right', tip_racks=[tips])

                # Commands
            """;

        Files.writeString(protocolScript, header, StandardOpenOption.TRUNCATE_EXISTING);
    }

    /**
     * Writes all queued commands to the protocol script file.
     *
     * Appends Python code for each queued command to the protocol script.
     *
     * @throws IOException if writing to the script file fails
     */
    private void writeQueuedCommands() throws IOException {
        if (commandQueue.isEmpty()) {
            return;
        }

        StringBuilder commands = new StringBuilder();
        for (String cmd : commandQueue) {
            commands.append("    ").append(cmd).append("\n");
        }

        Files.writeString(protocolScript, commands.toString(), StandardOpenOption.APPEND);
    }

    /**
     * Translates an abstract command to Opentrons Python API code.
     *
     * Maps command names and parameters to the corresponding Opentrons API method calls.
     *
     * @param cmd the command to translate
     * @return a Python code line suitable for inclusion in the Opentrons protocol script
     */
    private String translateCommand(Command cmd) {
        // ponytail: stub translation, expand per Opentrons API
        return switch (cmd.name()) {
            case "pick_up_tips" -> "pipette.pick_up_tip()";
            case "aspirate" -> {
                double volume = getVolume(cmd.parameters());
                yield String.format("pipette.aspirate(%f, plate['A1'])", volume);
            }
            case "dispense" -> {
                double volume = getVolume(cmd.parameters());
                yield String.format("pipette.dispense(%f, plate['A2'])", volume);
            }
            case "drop_tips" -> "pipette.drop_tip()";
            default -> "# Unknown command: " + cmd.name();
        };
    }

    /**
     * Extracts the volume parameter from a command's parameter map.
     *
     * @param params the command parameters
     * @return the volume value, or a default value if not found
     */
    private double getVolume(Map<String, Object> params) {
        Object vol = params.get("volume");
        if (vol instanceof Number num) {
            return num.doubleValue();
        }
        return 50.0; // ponytail: default
    }

    /**
     * Simulates the protocol locally using the `opentrons_simulate` command.
     *
     * @throws IOException if the simulation command fails
     */
    private void simulateProtocol() throws IOException {
        log.info("Simulating Opentrons protocol...");
        executeCommand(List.of("opentrons_simulate", protocolScript.toString()));
    }

    /**
     * Executes the protocol on the connected Opentrons robot using the `opentrons_execute` command.
     *
     * @throws IOException if the execution command fails or robot is unreachable
     */
    private void executeProtocol() throws IOException {
        log.info("Executing protocol on robot: {}", robotAddress);
        // ponytail: upload via HTTP API or SSH, then trigger run
        // Example: opentrons_execute -n <robot_ip> <protocol.py>
        executeCommand(List.of("opentrons_execute", "-n", robotAddress, protocolScript.toString()));
    }

    /**
     * Executes a system command via ProcessBuilder and logs output.
     *
     * Captures and logs stdout/stderr and checks the exit code.
     *
     * @param command the command and arguments as a list of strings
     * @throws IOException if the process execution fails
     */
    private void executeCommand(List<String> command) throws IOException {
        ProcessBuilder pb = new ProcessBuilder(command);
        pb.redirectErrorStream(true);

        Process process = pb.start();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                log.info("Opentrons: {}", line);
            }
        }

        try {
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new IOException("Opentrons command failed with exit code: " + exitCode);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("Interrupted waiting for Opentrons command", e);
        }
    }
}
