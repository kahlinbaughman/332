package kvstore;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.InputStream;

public class KVServer2 extends UnicastRemoteObject implements KVInterface {

    private static final String serverName = "rmi://localhost/Server";

    private HashMap<String, String> kv = new HashMap<String, String>();

    private static String[] checkReg = ("ps -o pid= -C rmiregistry").split("[ \t\n]+");
    private static String rmiReg = "rmiregistry";

    // Custom constructor that throws the required exception
    protected KVServer2() throws RemoteException {
        super();
    }

    private static Boolean checkRmi() {

        Boolean running = false;

        Process p = null;
        ProcessBuilder pb = new ProcessBuilder(checkReg);

        try
        {
            p = pb.start();
        } catch (Exception e) {
        }

        InputStream processOut = p.getInputStream();
        BufferedReader input = new BufferedReader(new InputStreamReader(processOut));

        try {
            if (input.readLine() == null)
            {
                running = false;
            }
            else
            {
                running = true;
            }
        } catch (Exception e) {

        }

        return running;
    }

    private static void runRmi() {

        Process p = null;
        ProcessBuilder pb = new ProcessBuilder(rmiReg);

        try
        {
            p = pb.start();
            Thread.sleep(1000);
        } catch (Exception e) {
        }

    }

    @Override
    public void insert(String key, String value) throws RemoteException {
        kv.put(key, value);
    }

    @Override
    public String lookup(String key) throws RemoteException, KeyNotFound {

        String value;

        if (kv.containsKey(key))
        {
            value = kv.get(key);
        }
        else
        {
            value = "Unknown key";
        }

        return value;
    }

    public void shutdown() throws RemoteException  {
        try{
            // Unregister ourself
            Naming.unbind(serverName);
            // Unexport; this will also remove us from the RMI runtime
            UnicastRemoteObject.unexportObject(this, true);
            System.out.println("KVServer: exiting!");
        } catch(Exception e) {
            // too bad
        }
    }

    public static void main(String[] args) {

        if (!checkRmi()) {
            System.out.println("KVServer: Starting RMI Registry…");
            runRmi();
            System.out.println("KVServer: RMI Registry started");
        } else {
            System.out.println("KVServer: RMI Registry already running!");
        }

        try {
            // Register myself to the RMI Registry
            System.err.println("KVServer: Registering to RMI Registry...");
            Naming.rebind(serverName, new KVServer());
            System.err.println("KVServer: Registered to RMI Registry...");
        } catch (Exception e) {
            System.err.println("KVServer: exception: " + e.toString());
            e.printStackTrace();
            System.exit(0);
        }
    }
}
