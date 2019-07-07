package ru.otus.javadeveloper.hw09.scanner;

import lombok.Value;

import java.util.Map;

@Value
public class ObjectScannerResult {
    private final String className;
    private final String idName;
    private final Long idValue;
    private final Map<String, Object> fieldMap;
}

