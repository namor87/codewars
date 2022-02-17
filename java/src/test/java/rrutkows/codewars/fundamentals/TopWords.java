package rrutkows.codewars.fundamentals;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.function.Predicate.not;

public class TopWords {

    public static final int SIZE = 3;

    public static List<String> top3(String s) {

        Map<String, Long> histogram = Stream.of(s.split("[\\s,\\.\\/\\\\]+"))
                .filter(word -> word.matches("\\w+'?\\w*|\\w*'?\\w+"))
                .collect(Collectors.groupingBy(String::toLowerCase, Collectors.counting()));

        return histogram
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(SIZE)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
