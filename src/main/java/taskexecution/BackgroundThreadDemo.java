package taskexecution;

import util.PrimeHelper;

import java.time.Duration;
import java.time.Instant;
import java.util.Scanner;
import java.util.concurrent.Executor;

public class BackgroundThreadDemo {
    static int n;
    public static void main(String[] args) {
        Runnable r = (() -> {
            int primeCount = n;
            long sum = PrimeHelper.sumOfFirstNPrimes(primeCount);
            System.out.printf("Sum of first %d primes: %d%n", primeCount, sum);
        });

        Scanner scanner = new Scanner(System.in);
        System.out.print("How many primes to sum? ");
        n = scanner.nextInt();

        Instant begin = Instant.now();
        new DirectExecutor().execute(r);
        System.out.print("How many primes to sum? ");
        n = scanner.nextInt();
        long sum = PrimeHelper.sumOfFirstNPrimes(n);
        System.out.printf("Sum of first %d primes: %d%n", n, sum);

        Instant end = Instant.now();
        System.out.println("Total time taken: " + Duration.between(begin, end).toMillis() + " ms");
    }

    static class DirectExecutor implements Executor {
        public void execute(Runnable r) {
            r.run();
        }
    }

    static class ThreadPerTaskExecutor implements Executor {
        public void execute(Runnable r) {
            new Thread(r).start();
        }
    }
}

