package ru.otus.javadeveloper.hw09.executor.scanner;

public interface EntityScanner {
    ScannerResult scanObject(Object origin);

    ScannerResult scanClass(Class clazz);
}
