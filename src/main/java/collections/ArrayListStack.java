package collections;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

public class ArrayListStack<T> {

    private final List<T> underlying = new ArrayList<>();

    public void push(T elt) {
        if (underlying.isEmpty()) {
            underlying.add(elt);
        }
    }
    public T pop() {
        if (! underlying.isEmpty()) {
            return underlying.removeLast();
        } else return null;
    }
    public static void main(String[] args) {
        final ArrayListStack<Integer> stack = new ArrayListStack<>();
        try (ExecutorService es = Executors.newFixedThreadPool(20)) {
            for (int i = 0; i < 20; i++) {
                es.submit(() -> {
                    while (true) {
                        if (ThreadLocalRandom.current().nextBoolean()) {
                            stack.push(0);
                        } else {
                            try {
                                stack.pop();
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                        }
                    }
                });
            }
        }
    }
}
