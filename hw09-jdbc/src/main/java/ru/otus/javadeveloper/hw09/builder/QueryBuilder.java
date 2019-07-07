package ru.otus.javadeveloper.hw09.builder;

import ru.otus.javadeveloper.hw09.scanner.ClassScannerResult;
import ru.otus.javadeveloper.hw09.scanner.ObjectScannerResult;

public interface QueryBuilder {
    String buildInsert(ObjectScannerResult objectScannerResult);

    String buildSelectById(ClassScannerResult classScannerResult);

    String buildUpdateById(ObjectScannerResult objectScannerResult);
}
