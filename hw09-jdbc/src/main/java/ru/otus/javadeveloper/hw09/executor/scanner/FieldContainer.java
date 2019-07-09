package ru.otus.javadeveloper.hw09.executor.scanner;

import lombok.Value;

import java.util.Optional;

@Value
public class FieldContainer {
    private final String name;
    private final Optional value;
    private final FieldType fieldType;

    public Object getValue() {
        return value.orElseGet(null);
    }
}