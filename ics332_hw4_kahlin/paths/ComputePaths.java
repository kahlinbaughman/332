package paths;

public class ComputePaths {

    private static int graphs[] = new int[]{2520, 1260, 840, 630, 504, 420, 360, 315, 280, 252};

    public void compute(int graph_size, int num_threads) {

        int graphDiv = graphs[(num_threads - 1)];
        int graphDivStart = 0;

        for (int tr = 0; tr < num_threads; tr++)
        {
            // Not real function, change later
            // worker(graph_size, graphDivStart, graphDiv);
            graphDivStart =  graphDiv;
            graphDiv += graphDiv;

        }

        for (long i = 0; i < 252; i++) {
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

class Worker implements Runnable{
    private int graphSize, graphStart, graphStop;


    public Worker(int graphSize, int graphStart, int graphStop) {
        this.graphSize = graphSize;
        this.graphStart = graphStart;
        this.graphStop = graphStop;
    }

    @Override
    public void run() {
    }

}
