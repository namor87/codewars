package rrutkows.codewars.fundamentals;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SpinWords {

    public String spinWords(String sentence) {
        return Stream.of(sentence.split(" "))
                .map(word -> word.length() >= 5 ? reverse(word) : word)
                .collect(Collectors.joining(" "));
    }

    private String reverse(String word) {
        List<String> letters = Arrays.asList(word.split(""));
        Collections.reverse(letters);
        return String.join("", letters);
    }
}
