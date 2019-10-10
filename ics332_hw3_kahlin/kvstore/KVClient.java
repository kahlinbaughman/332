package kvstore;

import java.rmi.Naming;

public class KVClient {

    private static KVInterface server_name;
    private static int boo = 1;

    public static void main(String[] args)
    {
        String value;

        try
        {
            server_name = (KVInterface) Naming.lookup("rmi://localhost/Server");

            if (args.length == 0 || args.length > 3)
            {
                System.err.println("Usage: java kvstore.KVClient [ shutdown | insert <key> <value> | lookup <key> ]");
            } else
            {
                switch(args[0])
                {

                    case "shutdown":
                        if (args.length == 1)
                        {
                            server_name.shutdown();
                            boo = 0;
                        }
                        break;

                    case "lookup":
                        if (args.length == 2)
                        {
                            System.out.println(server_name.lookup(args[1]));
                            boo = 0;
                        }
                        break;

                    case "insert":
                        if (args.length == 3)
                        {
                            server_name.insert(args[1], args[2]);
                            boo = 0;
                        }
                        break;
                }

                if (boo == 1)
                {
                    System.err.println("Usage: java kvstore.KVClient [ shutdown | insert <key> <value> | lookup <key> ]");
                }
            }
        }
        catch (Exception e)
        {

        }
    }
}
