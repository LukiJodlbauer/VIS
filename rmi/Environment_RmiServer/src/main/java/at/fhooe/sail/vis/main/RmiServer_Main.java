package at.fhooe.sail.vis.main;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class RmiServer_Main extends UnicastRemoteObject implements IEnvService{
    Map<String, Integer> _sensors  = new HashMap<String, Integer>() {{
        put("ligth", 1);
        put("air", 3);
        put("noise",1);
    }};    public RmiServer_Main() throws RemoteException { super(); }

    public static void main(String[] args) {
        try {
            RmiServer_Main rmi = new RmiServer_Main();
            Registry reg =
                    LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
            reg.rebind("RmiServer", rmi);

            System.out.println("Server is waiting for queries ...");
            System.out.println("PID"+ProcessHandle.current().pid());
            Thread unbindHook = new Thread(() -> System.out.println("Test"));
            Runtime.getRuntime().addShutdownHook(unbindHook);
        } catch (Exception _e) { _e.printStackTrace(); }
    }

    /**
     * Requests the sensor types of our environment server.
     *
     * @return the sensor types as String[]
     */
    @Override
    public String[] requestEnvironmentDataTypes() {
        //var result = new String[0];

        //sendCommand("getSensortypes()");
        //result = getResponse().split(";");

        return _sensors.keySet().toArray(new String[0]);
    }

    /**
     * Requests the data of the given sensor type.
     *
     * @param _type the sensor type for which the data should
     *              be requested
     * @return the data of the given sensor as EnvData Object
     */
    @Override
    public EnvData requestEnvironmentData(String _type) {
        // System.out.println("requestEnvironmentData: " + _type);

        var result = new EnvData();
        result.setSensorName(_type);

        //sendCommand(String.format("getSensor(%s)", _type));
        //var parts = getResponse().split("\\|");

        long timestamp = System.currentTimeMillis() / 1000;
        result.setTimestamp(timestamp);

        result.setValues(generateRandomValues(_sensors.get(_type)));

        return result;
    }

    /**
     * Requests the data of all sensor types.
     *
     * @return the data of all sensor types as an EnvData[]
     */
    @Override
    public EnvData[] requestAll() {
        // System.out.println("requestAll");

        var results = new ArrayList<EnvData>();

        long timestamp = System.currentTimeMillis() / 1000;

        //var sensorParts = Arrays.copyOfRange(parts, 1, parts.length);
        for (Map.Entry<String, Integer> entry : _sensors.entrySet()) {
            //var partsList = new ArrayList<>(List.of(part.split(";")));

            var envData = new EnvData();
            envData.setTimestamp(timestamp);
            envData.setSensorName(entry.getKey());

            //int[] values = partsList.stream().mapToInt(Integer::parseInt).toArray();
            envData.setValues(generateRandomValues(entry.getValue()));

            results.add(envData);
        }

        return results.toArray(new EnvData[0]);
    }

   private int[] generateRandomValues(int amount){
       Random rand = new Random();
       List<Integer> values = new LinkedList<>();
       for (int i = 0; i < amount; i++) {
           values.add(rand.nextInt(250));
       }
      return values.stream().mapToInt(i->i).toArray();
   }
}