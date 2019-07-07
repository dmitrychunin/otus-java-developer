package ru.otus.javadeveloper.hw09.executor.builder;

import ru.otus.javadeveloper.hw09.executor.scanner.ClassScannerResult;
import ru.otus.javadeveloper.hw09.executor.scanner.ObjectScannerResult;

public interface QueryBuilder {
    String buildInsert(ObjectScannerResult objectScannerResult);

    String buildSelectById(ClassScannerResult classScannerResult);

    String buildUpdateById(ObjectScannerResult objectScannerResult);
}
