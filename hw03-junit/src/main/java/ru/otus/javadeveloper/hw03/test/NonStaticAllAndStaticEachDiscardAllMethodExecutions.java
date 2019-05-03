package ru.otus.javadeveloper.hw03.test;

import ru.otus.javadeveloper.hw03.annotation.*;

public class NonStaticAllAndStaticEachDiscardAllMethodExecutions {
    public NonStaticAllAndStaticEachDiscardAllMethodExecutions() {
        System.out.println("Call of the constructor");
    }

    @BeforeEach
    static void staticBeforeEach() {
        System.out.println("static BeforeEach");
    }

    @AfterEach
    static void staticAfterEach() {
        System.out.println("static AfterEach");
    }

    @BeforeAll
    private void nonStaticBeforeAll() {
        System.out.println("non-static BeforeAll");
    }

    @AfterAll
    private void nonStaticAfterAll() {
        System.out.println("non-static AfterAll");
    }

    @Test
    void testOne() {
        System.out.println("testOne");
    }
}
