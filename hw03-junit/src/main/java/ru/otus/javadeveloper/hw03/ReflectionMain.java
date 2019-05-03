package ru.otus.javadeveloper.hw03;

import ru.otus.javadeveloper.hw03.test.*;

public class ReflectionMain {
    public static void main(String[] args) throws Exception {
        CustomJunit5AnnotationRunner.runTest(Junit5AnnotationsTest.class);
        CustomJunit5AnnotationRunner.runTest(NonStaticAllAndStaticEachDiscardAllMethodExecutions.class);
        CustomJunit5AnnotationRunner.runTest(PrivateTestsAreIgnoredPrivateAllAndEachAreNot.class);
        CustomJunit5AnnotationRunner.runTest(BeforeAllExceptionDiscardAllButAfterAll.class);
        CustomJunit5AnnotationRunner.runTest(BeforeEachExceptionDiscardAllButAfterAll.class);
    }
}
