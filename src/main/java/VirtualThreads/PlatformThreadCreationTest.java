package VirtualThreads;

public class PlatformThreadCreationTest {
    public static void main(String[] args) {
        int count = 0;

        try {
            while (true) {  // Continuously create new threads
                new Thread(() -> {
                    try {
                        Thread.sleep(1_000_000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }).start();
                count++;
            }
        } catch (OutOfMemoryError e) {
            System.out.println("OutOfMemoryError after creating " + count + " threads");
        }
    }
}