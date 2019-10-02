package kvstore;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface KVInterface extends Remote {

    public class KeyNotFound extends Exception {
        public KeyNotFound(String errorMessage) {
            super(errorMessage);
        }
    }

    public void insert(String key, String value) throws RemoteException;
    public String lookup(String key) throws RemoteException, KeyNotFound;
    public void shutdown() throws RemoteException;

}
