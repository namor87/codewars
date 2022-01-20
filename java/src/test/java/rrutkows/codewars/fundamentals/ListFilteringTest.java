package rrutkows.codewars.fundamentals;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;
import static rrutkows.codewars.fundamentals.ListFiltering.filterList;

public class ListFilteringTest {
    @Test
    public void examples() {
        // assertEquals("expected", "actual");
        Assert.assertEquals(Arrays.asList(1,2), filterList(Arrays.asList(1,2,"a","b")));
        assertEquals(Arrays.asList(1,0,15), filterList(Arrays.asList(1,"a","b",0,15)));
        assertEquals(Arrays.asList(1,2,123), filterList(Arrays.asList(1,2,"aasf","1","123",123)));
    }
}