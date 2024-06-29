import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.LongStream;

public class Parallelization {
    public static void main(String[] args) {
        long limit = 14_000;
        final long[] array = LongStream.iterate(0, n -> n <= limit, n -> n + 1).toArray();

        new Scanner(System.in).nextLine();
        long startTime = System.currentTimeMillis();
        final long[] array1 = Arrays.stream(array).map(i -> new PrimeHelper().sumOfFirstNPrimes(i)).toArray();
        long endTime = System.currentTimeMillis();

        for (long l : array1) System.out.println(l);
        System.out.println("Total time taken: " + (endTime - startTime) + " ms");
    }
}

