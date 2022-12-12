package at.fhooe.sail.vis.socket;

import at.fhooe.sail.vis.main.EnvData;
import at.fhooe.sail.vis.main.IEnvService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple socket client for testing the C++ Environment-Server.
 */
public class Environment_SocketClient implements IEnvService {
    /**
     * ip address of the Socket to which the client should connect.
     */
    String       mIpAddress;
    /**
     * port of the Socket to which the client should connect.
     */
    int          mPort;
    /**
     * Socket instance of the connection.
     */
    Socket       mSocket;
    /**
     * InputStream of the connection
     */
    InputStream  mInputStream;
    /**
     * OutputStream of the connection
     */
    OutputStream mOutputStream;

    /**
     * Constructor for initializing ip address and port. Once called it automatically starts the connection.
     */
    public Environment_SocketClient() {
        mIpAddress = "10.29.19.91";
        mPort = 4949;
        connect();
    }

    /**
     * Called from Constructor. It tries to connect to the ip address
     * and port specified in the constructor and sets Input- and OutputStream.
     */
    private void connect() {
        System.out.println("starting client ...");

        try {
            System.out.println("connecting to " + mIpAddress + " on port " + mPort);
            mSocket = new Socket(mIpAddress, mPort);
            System.out.println("connected to server");

            mOutputStream = mSocket.getOutputStream();
            mInputStream = mSocket.getInputStream();
        } catch (IOException _e) {
            _e.printStackTrace();
        }
    }

    /**
     * Can be used to manually close the Input- and OutputStream as well as the
     * Socket connection.
     */
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
        result.setSensorName(_type);

        sendCommand(String.format("getSensor(%s)", _type));
        var parts = getResponse().split("\\|");

        long timestamp = Long.parseLong(parts[0]);
        result.setTimestamp(timestamp);

        int[] values = Arrays.stream(parts[1].split(";"))
                .mapToInt(Integer::parseInt)
                .toArray();
        result.setValues(values);

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
            envData.setTimestamp(timestamp);
            envData.setSensorName(partsList.remove(0));

            int[] values = partsList.stream().mapToInt(Integer::parseInt).toArray();
            envData.setValues(values);

            results.add(envData);
        }

        return results.toArray(new EnvData[0]);
    }

    /**
     * Writes the command given as parameter to the OutputStream and
     * flushes it.
     *
     * @param _command the command to be written to the stream
     */
    private void sendCommand(String _command) {
        try {
            mOutputStream.write(_command.getBytes());
            mOutputStream.flush();
        } catch (IOException _e) {
            _e.printStackTrace();
        }
    }

    /**
     * Stores the values of the InputStream and returns it after
     * a new-line character was read.
     *
     * @return the value of the InputStream until new-line
     * character as String
     */
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
