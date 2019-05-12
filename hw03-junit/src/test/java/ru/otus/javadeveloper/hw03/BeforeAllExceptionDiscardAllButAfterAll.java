package ru.otus.javadeveloper.hw03;

import org.junit.jupiter.api.*;

class BeforeAllExceptionDiscardAllButAfterAll {
    public BeforeAllExceptionDiscardAllButAfterAll() {
        System.out.println("Call of the constructor");
    }

    @BeforeAll
    static void beforeAll() {
        throw new RuntimeException("BeforeAll");
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
    void testTwo() {
        System.out.println("testTwo");
    }

    @AfterEach
    void afterEach() {
        System.out.println("AfterEach");
    }
}