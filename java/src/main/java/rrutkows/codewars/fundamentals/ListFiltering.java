package rrutkows.codewars.fundamentals;

import java.util.List;
import java.util.stream.Collectors;

public class ListFiltering {
    public static List filterList(final List<?> list) {
        return list.stream()
                .filter(o -> o instanceof Integer)
                .collect(Collectors.toList());
    }
}
