package com.mitra.controller.request_processor.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SearchBySwipeProcessorTest {

    static SearchBySwipeProcessor searchBySwipeProcessor;
    static List<Integer> profileIds;

    @BeforeAll
    static void setUp() {
        searchBySwipeProcessor = new SearchBySwipeProcessor();

        profileIds = new ArrayList<>();
        profileIds.add(12);
        profileIds.add(39);
        profileIds.add(87);
        profileIds.add(831);
        profileIds.add(5);
        profileIds.add(99);
    }

    @Test
    void profileIdsToString() {
        String expectedString = "12x39x87x831x5x99x";
        String actualString = searchBySwipeProcessor.profileIdsToString(profileIds);
        assertEquals(expectedString, actualString);
    }

    @Test
    void getProfileIdFromString() {
        int expected;
        int actual;

        expected = 12;
        String idString = searchBySwipeProcessor.profileIdsToString(profileIds);
        actual = searchBySwipeProcessor.getProfileIdFromString(idString);

        assertEquals(expected, actual);
    }

    @Test
    void cutProfileIds() {
        String expectedString = "39x87x831x5x99x";

        String idString = searchBySwipeProcessor.profileIdsToString(profileIds);

        String actualString = searchBySwipeProcessor.cutProfileIds(idString, 12);
        assertEquals(expectedString, actualString);

        expectedString = "39x87x5x99x";
        actualString = searchBySwipeProcessor.cutProfileIds(actualString, 831);
        assertEquals(expectedString, actualString);

        expectedString = "39x87x99x";
        actualString = searchBySwipeProcessor.cutProfileIds(actualString, 5);
        assertEquals(expectedString, actualString);

        expectedString = "39x87x";
        actualString = searchBySwipeProcessor.cutProfileIds(actualString, 99);
        assertEquals(expectedString, actualString);
    }
}