package rrutkows.codewars.fundamentals;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class FindOdd {
    public static int findIt(int[] values) {
        Map<Integer, AtomicInteger> counts = histogram(values);
        counts.values().removeIf(count -> count.get() % 2 ==0);
        return counts.keySet().stream().findFirst().orElse(Integer.MIN_VALUE);
    }

    private static Map<Integer, AtomicInteger> histogram(int[] values) {
        Map <Integer, AtomicInteger> counts = new HashMap<>();
        IntStream.of(values)
                .forEach(element -> {
                    counts.putIfAbsent(element, new AtomicInteger(0));
                    counts.get(element).incrementAndGet();
                });
        return counts;
    }
}