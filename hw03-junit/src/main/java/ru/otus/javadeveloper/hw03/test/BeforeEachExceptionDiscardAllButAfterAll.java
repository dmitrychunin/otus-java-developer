package ru.otus.javadeveloper.hw03.test;

import ru.otus.javadeveloper.hw03.annotation.*;

public class BeforeEachExceptionDiscardAllButAfterAll {
    public BeforeEachExceptionDiscardAllButAfterAll() {
        System.out.println("Call of the constructor");
    }

    @BeforeAll
    static void beforeAll() {
        System.out.println("BeforeAll");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("AfterAll");
    }

    @BeforeEach
    void beforeEach() {
        System.out.println("BeforeEach");
    }

    @BeforeEach
    void beforeEach2() {
        throw new RuntimeException("BeforeEach2");
    }

    @BeforeEach
    void beforeEach3() {
        System.out.println("BeforeEach3");
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