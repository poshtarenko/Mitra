package com.mitra.controller.impl.impl;

import com.mitra.controller.impl.get.FastSearchController;
import com.mitra.service.ServiceFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FastSearchControllerTest {

    static FastSearchController fastSearchController;
    static List<Integer> profileIds;

    @BeforeAll
    static void setUp() {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        fastSearchController =
                new FastSearchController(serviceFactory.getProfileService());

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
        String actualString = fastSearchController.profileIdsToString(profileIds);
        assertEquals(expectedString, actualString);
    }

    @Test
    void getProfileIdFromString() {
        int expected;
        int actual;

        expected = 12;
        String idString = fastSearchController.profileIdsToString(profileIds);
        actual = fastSearchController.extractFirstNumberFromString(idString);

        assertEquals(expected, actual);
    }

    @Test
    void cutProfileIds() {
        String expectedString = "39x87x831x5x99x";

        String idString = fastSearchController.profileIdsToString(profileIds);

        String actualString = fastSearchController.cutProfileIds(idString, 12);
        assertEquals(expectedString, actualString);

        expectedString = "39x87x5x99x";
        actualString = fastSearchController.cutProfileIds(actualString, 831);
        assertEquals(expectedString, actualString);

        expectedString = "39x87x99x";
        actualString = fastSearchController.cutProfileIds(actualString, 5);
        assertEquals(expectedString, actualString);

        expectedString = "39x87x";
        actualString = fastSearchController.cutProfileIds(actualString, 99);
        assertEquals(expectedString, actualString);
    }
}