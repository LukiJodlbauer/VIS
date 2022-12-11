package at.fhooe.sail.vis.socket;

import at.fhooe.sail.vis.test.EnvData;
import at.fhooe.sail.vis.test.IEnvService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Environment_SocketClient implements IEnvService {
    String mIpAddress = "127.0.0.1";
    int mPort = 4949;
    Socket mSocket;
    InputStream mInputStream;
    OutputStream mOutputStream;

    public Environment_SocketClient() {
        connect();
    }

    private void connect() {
        System.out.println("starting client ...");

        try {
            System.out.println("connecting to " + mIpAddress + " on port " + mPort);
            mSocket = new Socket("127.0.0.1", 4949);
            System.out.println("connected to server");

            mOutputStream = mSocket.getOutputStream();
            mInputStream = mSocket.getInputStream();
        } catch (IOException _e) {
            _e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            mOutputStream.close();
            mInputStream.close();
            mSocket.close();
        } catch (IOException _e) {
            _e.printStackTrace();
        }
    }

    @Override
    public String[] requestEnvironmentDataTypes() {
        var result = new String[0];

        sendCommand("getSensortypes()");
        result = getResponse().split(";");

        return result;
    }

    @Override
    public EnvData requestEnvironmentData(String _type) {
        System.out.println("requestEnvironmentData: " + _type);

        var result = new EnvData();
        result.setmSensorName(_type);

        sendCommand(String.format("getSensor(%s)", _type));
        var parts = getResponse().split("\\|");

        long timestamp = Long.parseLong(parts[0]);
        result.setmTimestamp(timestamp);

        int[] values = Arrays.stream(parts[1].split(";"))
                .mapToInt(Integer::parseInt)
                .toArray();
        result.setmValues(values);

        return result;
    }

    @Override
    public EnvData[] requestAll() {
        System.out.println("requestAll");

        var results = new ArrayList<EnvData>();

        sendCommand("getAllSensors()");
        var parts = getResponse().split("\\|");

        long timestamp = Long.parseLong(parts[0]);

        var sensorParts = Arrays.copyOfRange(parts, 1, parts.length);
        for (String part : sensorParts) {
            var partsList = new ArrayList<>(List.of(part.split(";")));

            var envData = new EnvData();
            envData.setmTimestamp(timestamp);
            envData.setmSensorName(partsList.remove(0));

            int[] values = partsList.stream().mapToInt(Integer::parseInt).toArray();
            envData.setmValues(values);

            results.add(envData);
        }

        return results.toArray(new EnvData[0]);
    }

    private void sendCommand(String _command) {
        try {
            mOutputStream.write(_command.getBytes());
            mOutputStream.flush();
        } catch (IOException _e) {
            _e.printStackTrace();
        }
    }

    private String getResponse() {
        int data = -1;
        var line = new StringBuilder();

        try {
            while ((data = mInputStream.read()) != -1) {
                line.append((char) data);
                if (((char) data) == '\n') {
                    break;
                }
            }
            System.out.print("Server: " + line);
        } catch (IOException _e) {
            _e.printStackTrace();
        }

        return line.toString().trim();
    }
}
