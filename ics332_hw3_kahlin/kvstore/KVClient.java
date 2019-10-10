package kvstore;

import java.rmi.*;

public class KVClient {

    public static void main(String[] args) {

        Lookup access = Naming.lookup("rmi://localhost/Server");

        if (args.length == 0 || args.length > 3)
        {
            System.err.println("Usage: java kvstore.KVClient [ shutdown | insert <key> <value> | lookup <key> ]");
        } else
        {
            switch(args[0])
            {

                case "shutdown":
                    System.out.println("shutdown");
                    break;

                case "insert":
                    System.out.println("insert");
                    access.insert("as", "as");
                    break;

                case "lookup":
                    System.out.println("lookup");
                    break;

                default:
                    System.err.println("Usage: java kvstore.KVClient [ shutdown | insert <key> <value> | lookup <key> ]");
            }
        }
    }
}
