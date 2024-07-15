public class DeadlockDemo {

    public static void main(String[] args) {
        final Object resource1 = new Object();
        final Object resource2 = new Object();

        // Thread 1 acquires resource1 then resource2
        Thread.ofPlatform().start(() -> {
            synchronized (resource1) {
                System.out.println("Thread 1: Locked resource 1");
                try { Thread.sleep(100);} catch (Exception e) {}
                synchronized (resource2) {
                    System.out.println("Thread 1: Locked resource 2");
                }
            }
        });

        // Thread 2 acquires resource2 then resource1
        Thread.ofPlatform().start(() -> {
            synchronized (resource2) {
                System.out.println("Thread 2: Locked resource 2");
                try { Thread.sleep(100);} catch (Exception e) {}
                synchronized (resource1) {
                    System.out.println("Thread 2: Locked resource 1");
                }
            }
        });
    }
}