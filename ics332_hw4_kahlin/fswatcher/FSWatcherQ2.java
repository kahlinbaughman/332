package fswatcher;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.IOException;
import java.util.function.*;

public class FSWatcherQ2 {


    public void watch(String dirname, Consumer<String> method) {
        String[] command = ("inotifywait -e create -m " + dirname).split("[ \t\n]+");
        Process p = null;
        Watcher watcher;
        ProcessBuilder pb = new ProcessBuilder(command);

        try {
            p = pb.start();
        } catch (Exception e) {
        }

        watcher = new Watcher(p, method);
        new Thread(watcher).start();
    }
}

class Watcher implements Runnable{

    private Process p;
    private Consumer<String> method;

    public Watcher(Process p, Consumer<String> method) {
        this.p = p;
        this.method = method;
    }

    @Override
    public void run() {

        InputStream  processInput = p.getInputStream();
        BufferedReader input = new BufferedReader(new InputStreamReader(processInput));

        String line;
        while(true)
        {
            try {
                line = input.readLine();
                if (line == null) {
                    break;
                } else {
                    method.accept(line);
                }
            } catch (Exception e) {

            }
        }
    }
}
