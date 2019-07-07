package ru.otus.javadeveloper.hw09.scanner;

public interface EntityScanner {
    ObjectScannerResult scanObject(Object origin);

    ClassScannerResult scanClass(Class clazz);
}
