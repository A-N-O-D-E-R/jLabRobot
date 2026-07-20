package io.jlabrobot.protocol.serial;

import com.fazecast.jSerialComm.SerialPort;

import java.util.Arrays;
import java.util.List;

public class SerialPortUtil {
    public static List<String> listAvailablePorts() {
        return Arrays.stream(SerialPort.getCommPorts())
                .map(SerialPort::getSystemPortName)
                .toList();
    }

    public static void printAvailablePorts() {
        SerialPort[] ports = SerialPort.getCommPorts();
        if (ports.length == 0) {
            System.out.println("No serial ports found");
            return;
        }

        System.out.println("Available serial ports:");
        for (SerialPort port : ports) {
            System.out.printf("  %s - %s\n", port.getSystemPortName(), port.getDescriptivePortName());
        }
    }
}
