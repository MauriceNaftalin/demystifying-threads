package taskexecution;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class FjArraySumCalculator extends RecursiveTask<Long> {
    private static final int THRESHOLD = 1000; // Adjust as necessary
    private final int start;
    private final int end;
    private final int[] array;

    public FjArraySumCalculator(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        long sum = 0;
        if (end - start <= THRESHOLD) {
            for (int i = start; i < end; i++) {
                sum += array[i];
            }
            return sum;
        } else {
            int middle = start + (end - start) / 2;
            FjArraySumCalculator leftTask = new FjArraySumCalculator(array, start, middle);
            FjArraySumCalculator rightTask = new FjArraySumCalculator(array, middle, end);
            leftTask.fork(); // Asynchronously execute the left task
            long rightResult = rightTask.compute(); // Compute the right part
            long leftResult = leftTask.join(); // Wait for and retrieve the result of the left task
            return leftResult + rightResult;
        }
    }

    public static void main(String[] args) {
        int[] array = new int[10000];
        for (int i = 0; i < array.length; i++) {
            array[i] = i; // initialize array with sample values for demonstration
        }

        long result;
        try (ForkJoinPool pool = new ForkJoinPool()) {
            FjArraySumCalculator task = new FjArraySumCalculator(array, 0, array.length);
            result = pool.invoke(task);
        }

        System.out.println("Sum: " + result);
    }
}