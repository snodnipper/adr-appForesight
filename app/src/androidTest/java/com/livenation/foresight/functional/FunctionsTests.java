package com.livenation.foresight.functional;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.livenation.foresight.functional.Functions.filterList;
import static com.livenation.foresight.functional.Functions.mapList;

public class FunctionsTests extends TestCase {
    private static final List<Integer> TEST_DATA = Arrays.asList(10, 20, 30, 40, 50);

    private static List<Integer> testDataCopy() {
        List<Integer> copy = new ArrayList<>();
        copy.addAll(TEST_DATA);
        return copy;
    }

    public void testMapList() {
        List<Integer> original = testDataCopy();
        List<Integer> mapped = mapList(original, i -> i * 10);
        assertEquals(Arrays.asList(100, 200, 300, 400, 500), mapped);
        assertEquals(TEST_DATA, original);
        assertEquals(new ArrayList<>(), mapList(null, o -> o));
    }

    public void testFilterList() {
        List<Integer> original = testDataCopy();
        List<Integer> filtered = filterList(original, i -> (i / 2) % 2 == 0);
        assertEquals(Arrays.asList(20, 40), filtered);
        assertEquals(TEST_DATA, original);
        assertEquals(new ArrayList<>(), filterList(null, o -> true));
    }
}
