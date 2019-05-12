package ru.otus.javadeveloper.hw03;

import org.junit.jupiter.api.*;

public class PrivateTestsAreIgnoredPrivateAllAndEachAreNot {
    public PrivateTestsAreIgnoredPrivateAllAndEachAreNot() {
        System.out.println("Call of the constructor");
    }

    @BeforeAll
    private static void beforeAll1() {
        System.out.println("BeforeAll1");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("AfterAll");
    }

    @BeforeEach
    void beforeEach3() {
        System.out.println("BeforeEach3");
    }

    @BeforeEach
    private void beforeEach4() {
        System.out.println("BeforeEach4");
    }

    @BeforeEach
    void beforeEach() {
        System.out.println("BeforeEach");
    }

    @BeforeEach
    void beforeEach2() {
        System.out.println("BeforeEach2");
    }

    @Test
    void testOne() {
        System.out.println("testOne");
    }

    @Test
    private void testThree() {
        System.out.println("testThree");
    }

    @AfterEach
    void afterEach() {
        System.out.println("AfterEach");
    }
}
