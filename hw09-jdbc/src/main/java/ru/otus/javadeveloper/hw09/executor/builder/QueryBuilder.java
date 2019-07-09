package ru.otus.javadeveloper.hw09.executor.builder;

import ru.otus.javadeveloper.hw09.executor.scanner.ScannerResult;

public interface QueryBuilder {
    String buildInsert(ScannerResult scannerResult);

    String buildSelectById(ScannerResult scannerResult);

    String buildUpdateById(ScannerResult scannerResult);
}
