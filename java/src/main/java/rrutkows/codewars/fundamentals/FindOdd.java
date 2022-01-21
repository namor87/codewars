package rrutkows.codewars.fundamentals;

import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class FindOdd {
    public static int findIt(int[] values) {
//        IntSummaryStatistics collect = IntStream.of(values).boxed()
//                .collect(Collectors.summarizingInt(Integer::intValue));
//        System.out.println(collect);

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