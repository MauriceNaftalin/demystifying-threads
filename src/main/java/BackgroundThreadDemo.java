import java.util.Scanner;
import java.util.concurrent.Executor;

public class BackgroundThreadDemo {
    static int n;
    public static void main(String[] args) {
        Runnable r = (() -> {
            int primeCount = n;
            long sum = new PrimeHelper().sumOfFirstNPrimes(primeCount);
            System.out.printf("Sum of first %d primes: %d%n", primeCount, sum);
        });

        Scanner scanner = new Scanner(System.in);
        System.out.print("How many primes to sum? ");
        n = scanner.nextInt();

        long startTime = System.currentTimeMillis();
        new ThreadPerTaskExecutor().execute(r);
        System.out.print("How many primes to sum? ");
        n = scanner.nextInt();
        long sum2 = new PrimeHelper().sumOfFirstNPrimes(n);
        System.out.printf("Sum of first %d primes: %d%n", n, sum2);

        long endTime = System.currentTimeMillis();
        System.out.println("Total time taken: " + (endTime - startTime) + " ms");
    }
}

//https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/concurrent/Executor.html // 2500000, then 2000000
class DirectExecutor implements Executor {
    public void execute(Runnable r) {
        r.run();
    }
}

class ThreadPerTaskExecutor implements Executor {
    public void execute(Runnable r) {
        new Thread(r).start();
    }
}

