package rrutkows.codewars.fundamentals;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Comparator.reverseOrder;


public class TopWords {
    public static List<String> top3(String s) {
        return Stream.of(s.split("[\\s./\\\\\\-;?+,!_:]+"))
                .filter(word -> word.matches("[\\w']*\\w+[\\w']*"))
                .collect(Collectors.groupingBy(String::toLowerCase, Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.comparingByValue(reverseOrder()))
                .limit(3)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
