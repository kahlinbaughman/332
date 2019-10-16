package fswatcher;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.IOException;
import java.util.function.*;

public class FSWatcherQ1 {


    public void watch(String dirname, Consumer<String> method) {
        String[] command = ("inotifywait -e create -m " + dirname).split("[ \t\n]+");
        Process p = null;
        ProcessBuilder pb = new ProcessBuilder(command);

        try {
            p = pb.start();
        } catch (Exception e) {
        }

        InputStream  processInput = p.getInputStream();
        BufferedReader input = new BufferedReader(new InputStreamReader(processInput));

        String line;
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

class Watcher {

    public Watcher() {

    }
}
