package taskexecution;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class FjArrayInitializer extends RecursiveAction {
    private static final int THRESHOLD = 1000; // Adjust as necessary
    public static final int ARRAY_LENGTH = 10000;
    private final int start;
    private final int end;
    private final int[] array;

    public FjArrayInitializer(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected void compute() {
        if (end - start < THRESHOLD) {
            for (int i = start; i < end; i++) {
                array[i] = i;
            }
        } else {
            int middle = start + (end - start) / 2;
            FjArrayInitializer left = new FjArrayInitializer(array, start, middle);
            FjArrayInitializer right = new FjArrayInitializer(array, middle, end);
            invokeAll(left, right);
        }
    }

    public static void main(String[] args) {
        int[] array = new int[ARRAY_LENGTH];
        try (ForkJoinPool pool = new ForkJoinPool()) {
            FjArrayInitializer task = new FjArrayInitializer(array, 0, array.length);
            pool.invoke(task);
        }
        for (int i = 0; i < ARRAY_LENGTH; i++) {
            System.out.println(array[i]);

        }
    }
}