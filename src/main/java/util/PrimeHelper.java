package util;

public class PrimeHelper {
    public static boolean isPrime(int n) {
        if (n <= 1) return false;
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) return false;
        }
        return true;
    }

    public static long sumOfFirstNPrimes(long n) {
        int count = 0;
        long sum = 0;
        int candidatePrime = 2;
        while (count < n) {
            if (isPrime(candidatePrime)) {
                sum += candidatePrime;
                count++;
            }
            candidatePrime++;
        }
        return sum;
    }
}