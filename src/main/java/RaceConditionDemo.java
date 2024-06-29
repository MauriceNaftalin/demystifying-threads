public class RaceConditionDemo {

    // Global counter variable
    private static int counter = 0;

    // Number of increments per thread
    private static final int NUM_INCREMENTS = 100000;

    // Synchronized method to increment the counter (commented out to show the race condition)
     private synchronized static void incrementCounter() {
         counter++;
     }

    // Method to increment the counter
//    private static void incrementCounter() {
//        counter++;
//    }

    // Runnable task for the threads
    private static class CounterIncrementer implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < NUM_INCREMENTS; i++) {
                incrementCounter();
            }
        }
    }

    public static void main(String[] args) {
        // Creating two threads that run the CounterIncrementer task
        Thread thread1 = new Thread(new CounterIncrementer());
        Thread thread2 = new Thread(new CounterIncrementer());

        // Starting the threads
        thread1.start();
        thread2.start();

        // Waiting for both threads to finish
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Expected counter value
        int expectedValue = NUM_INCREMENTS * 2;

        // Printing the result
        System.out.println("Final counter value: " + counter);
        System.out.println("Expected counter value: " + expectedValue);

        if (counter == expectedValue) {
            System.out.println("No race condition detected.");
        } else {
            System.out.println("Race condition detected.");
        }
    }
}