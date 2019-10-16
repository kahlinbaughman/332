package fswatcher;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FSWatcherUseCaseQ3 {

    private ArrayList<String> watchers;
    private FSWatcherQ3 fswatcher;

    FSWatcherUseCaseQ3() {
        this.watchers = new ArrayList<>();
        this.fswatcher = new FSWatcherQ3();
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
                    String dir = getPath();
                    if (!Files.isDirectory(Paths.get(dir))) {
                        System.err.println(dir + ": invalid directory");
                        break;
                    }
                    removeDirWatcher(dir);
                    break;
                }
                case 3: {
                    for (String dir : this.watchers) {
                        System.out.println("---> " + dir);
                    }
                    break;
                }
                case 4: {
                    for (String dir : this.watchers) {
                        this.fswatcher.stopWatch(dir);
                    }
                    System.exit(0);
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


    private void removeDirWatcher(String dir) {
        this.watchers.remove(this.watchers.indexOf(dir));
        try {
            this.fswatcher.stopWatch(dir);
        } catch (ArrayIndexOutOfBoundsException e) {
            // Do nothing
            return;
        }
    }

    private static void printMenuChoices() {
        System.out.println();
        System.out.println("1. Watch a directory");
        System.out.println("2. Stop watching a directory");
        System.out.println("3. List of watched directories");
        System.out.println("4. Quit");
        System.out.println();

    }

    private int getMenuChoice() {

        while (true) {
            printMenuChoices();
            InputStreamReader reader = new InputStreamReader(System.in);

            try {
                int input = reader.read() - '0';
                if ((input >= 1) && (input <= 4)) {
                    return input;
                }
            } catch (IOException e) {
                System.err.println("I/O Error while reading from keyboard");
                System.exit(1);
            }
        }
    }

    public static void main(String[] args) {
        new FSWatcherUseCaseQ3().go();
    }
}
