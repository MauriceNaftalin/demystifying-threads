package collections;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HashMapCounter {
    private static Map<String, Integer> map = new ConcurrentHashMap<>();

    public static void main(String[] args) throws InterruptedException {
        // Thread to increment the count for "key1"
        Thread t1 = Thread.ofPlatform().start(() -> {
            for (int i = 0; i < 10000; i++) {
                map.put("key1", map.getOrDefault("key1", 0) + 1);
            }
        });

        // Another thread to increment the count for "key1"
        Thread t2 = Thread.ofPlatform().start(() -> {
            for (int i = 0; i < 10000; i++) {
                map.put("key1", map.getOrDefault("key1", 0) + 1);
            }
        });

        t1.join();
        t2.join();

        System.out.println("Count of key1: " + map.get("key1"));
    }
}