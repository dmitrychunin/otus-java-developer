package ru.otus.javadeveloper.hw03;

public class ReflectionMain {
    public static void main(String[] args) throws Exception {
        CustomJunit5AnnotationRunner.runTest(CustomAnnotationsTest.class);
    }
}
