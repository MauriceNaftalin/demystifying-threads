package VirtualThreads;

public class VirtualThreadCreationTest {
    public static void main(String[] args) {
        int count = 0;
        try {
            while (true) {  // Continuously create new threads
                Thread.ofVirtual().start(() -> {
                    try {
                        Thread.sleep(1_000_000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                });
                count += 1;
                if (count % 1_000_000 == 0) {
                    System.out.println(count);
                    System.out.flush();
                }
            }
        } catch (OutOfMemoryError e) {
            System.out.println("OutOfMemoryError after creating " + count + " threads");
        }
    }
}
