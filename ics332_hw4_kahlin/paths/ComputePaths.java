package paths;

public class ComputePaths {


    public void compute(int graph_size, int num_threads) {

        for (long i = 0; i < 2520; i++) {
            new FloydWarshall(graph_size, i).floydWarshall();
        }

    }

    public static void main(String[] args) {

        if (args.length != 2) {
            System.err.println("Usage: java ComputePaths <graph size> <# threads>");
            System.exit(1);
        }

        int graph_size = 0;
        int num_threads = 0;
        try {
            graph_size = Integer.parseInt(args[0]);
            num_threads = Integer.parseInt(args[1]);
            if ((graph_size <= 0) || (num_threads < 1)) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            System.err.println("Invalid command-line arguments");
            System.exit(1);
        }

        double now = System.currentTimeMillis();

        new ComputePaths().compute(graph_size, num_threads);

        System.out.println("All graphs computed in " + (System.currentTimeMillis() - now) / 1000 + " seconds");

    }

}
