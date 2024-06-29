import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Callable;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.Future;

public class ThreadThrashingDemo {
    public static void main(String[] args) {
        int[] threadCounts = {1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048}; // Varying thread counts
        int totalTasks = 2048; // Total tasks to keep the work constant

        for (int numThreads : threadCounts) {
            long duration = runTasks(numThreads, totalTasks);
            System.out.println("Number of threads: " + numThreads + ", Total execution time: " + duration + " milliseconds");
        }
    }

    private static long runTasks(int numThreads, int totalTasks) {
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        List<Future<Integer>> futures = new ArrayList<>();

        long startTime = System.nanoTime();

        int tasksPerThread = (int) Math.ceil((double) totalTasks / numThreads);
        for (int i = 0; i < numThreads; i++) {
            futures.add(executor.submit(new MatrixMultiplicationTask(100, tasksPerThread)));
        }

        for (Future<Integer> future : futures) {
            try {
                future.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long endTime = System.nanoTime();
        return TimeUnit.NANOSECONDS.toMillis(endTime - startTime);
    }
}

class MatrixMultiplicationTask implements Callable<Integer> {
    private int size;
    private int repetitions;

    public MatrixMultiplicationTask(int size, int repetitions) {
        this.size = size;
        this.repetitions = repetitions;
    }

    @Override
    public Integer call() {
        double[][] matrixA = new double[size][size];
        double[][] matrixB = new double[size][size];
        double[][] result = new double[size][size];

        // Initialize matrices with some values
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrixA[i][j] = Math.random();
                matrixB[i][j] = Math.random();
            }
        }

        // Perform matrix multiplication repeatedly
        for (int r = 0; r < repetitions; r++) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    result[i][j] = 0;
                    for (int k = 0; k < size; k++) {
                        result[i][j] += matrixA[i][k] * matrixB[k][j];
                    }
                }
            }
        }
        return 0;
    }
}