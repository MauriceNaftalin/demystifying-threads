package collections;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CmeDemo {

    public static void main(String[] args) {
        final List<Integer> ints = Stream.iterate(0, i -> i + 1).limit(10).collect(Collectors.toList());
        System.out.println("ints = " + ints);
        for (Integer i : ints) {
            ints.remove(i);
        }
    }
}
