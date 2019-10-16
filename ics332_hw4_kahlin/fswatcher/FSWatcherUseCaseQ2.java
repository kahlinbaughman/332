package fswatcher;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FSWatcherUseCaseQ2 {

    private ArrayList<String> watchers;
    private FSWatcherQ2 fswatcher;

    FSWatcherUseCaseQ2() {
        this.watchers = new ArrayList<>();
        this.fswatcher = new FSWatcherQ2();
    }

    private void go() {

        while (true) {
            switch (getMenuChoice()) {
                case 1: {
                    String dir = getPath();
                    if (!Files.isDirectory(Paths.get(dir))) {
                        System.err.println(dir + ": invalid directory");
                        break;
                    }
                    addDirWatcher(dir);
                    break;
                }
                case 2: {
                    for (String dir : this.watchers) {
                        System.out.println("---> " + dir);
                    }
                    break;
                }
            }
        }
    }

    private String getPath() {
        System.out.print("Enter a directory path: ");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line = "";
        try {
            line = reader.readLine();
        } catch (IOException e) {
            System.exit(1);
        }
        return line;
    }

    private void addDirWatcher(String dir) {
        this.watchers.add(dir);
        this.fswatcher.watch(dir, (s) -> {
            System.out.println("New file in directory " + dir + ": " + s.split(" ")[2]);
        });
//        printMenuChoices();
    }

    private static void printMenuChoices() {
        System.out.println();
        System.out.println("1. Watch a directory");
        System.out.println("2. List of watched directories");
        System.out.println();

    }

    private int getMenuChoice() {

        while (true) {
            printMenuChoices();
            InputStreamReader reader = new InputStreamReader(System.in);

            try {
                int input = reader.read() - '0';
                if ((input >= 1) && (input <= 2)) {
                    return input;
                }
            } catch (IOException e) {
                System.err.println("I/O Error while reading from keyboard");
                System.exit(1);
            }
        }
    }

    public static void main(String[] args) {
        new FSWatcherUseCaseQ2().go();
    }
}
