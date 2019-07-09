package ru.otus.javadeveloper.hw09.executor.scanner;

import lombok.Value;

import java.util.List;

import static ru.otus.javadeveloper.hw09.executor.scanner.FieldType.ID;

@Value
public class ScannerResult {
    private final String className;
    private final List<FieldContainer> fieldContainers;

    public FieldContainer getFirstIdFieldContainer() {
        return fieldContainers
                .stream()
                .filter(fieldContainer -> fieldContainer.getFieldType() == ID)
                .findFirst().orElseThrow(RuntimeException::new);
    }
}

