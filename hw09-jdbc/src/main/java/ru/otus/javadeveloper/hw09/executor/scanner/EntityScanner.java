package ru.otus.javadeveloper.hw09.executor.scanner;

public interface EntityScanner {
    ObjectScannerResult scanObject(Object origin);

    ClassScannerResult scanClass(Class clazz);
}
