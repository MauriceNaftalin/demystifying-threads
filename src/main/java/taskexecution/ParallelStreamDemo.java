package taskexecution;

import util.PrimeHelper;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.stream.LongStream;

public class ParallelStreamDemo {
    public static void main(String[] args) {
        long limit = 14_000;
        final long[] array = LongStream.iterate(0, n -> n <= limit, n -> n + 1).toArray();
        var start = Instant.now();
        Arrays.stream(array)
                .parallel()
                .map(PrimeHelper::sumOfFirstNPrimes)
                .toArray();
        var end = Instant.now();
        System.out.println("Total time taken: " + Duration.between(start, end).toMillis() + " ms");
    }
}

