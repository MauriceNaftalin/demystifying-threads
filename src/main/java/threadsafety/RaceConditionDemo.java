package threadsafety;

public class RaceConditionDemo {

    private int counter = 0;

    public static void main(String[] args) {
        RaceConditionDemo demo = new RaceConditionDemo();
        demo.runTest();
    }

    private void runTest() {
        Thread thread1 = Thread.ofPlatform().start(this::incrementCounter);
        Thread thread2 = Thread.ofPlatform().start(this::incrementCounter);

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Threads interrupted");
        }

        System.out.println("Final counter value: " + counter);
    }

    private void incrementCounter() {
        for (int i = 0; i < 100000; i++) {
            counter++;
        }
    }
}
