package ru.otus.javadeveloper.hw09.executor.scanner;

import ru.otus.javadeveloper.hw09.executor.Id;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ru.otus.javadeveloper.hw09.executor.scanner.FieldType.FIELD;
import static ru.otus.javadeveloper.hw09.executor.scanner.FieldType.ID;

public class EntityScannerImpl implements EntityScanner {
    @Override
    public ScannerResult scanObject(Object origin) {
        Class<?> originClass = origin.getClass();
        Field[] declaredFields = originClass.getDeclaredFields();
        List<FieldContainer> fieldContainers = new ArrayList<>();

        for (Field declaredField : declaredFields) {
            try {
                declaredField.setAccessible(true);
                Object value = declaredField.get(origin);

                FieldContainer fieldContainer = build(declaredField, Optional.of(value));
                fieldContainers.add(fieldContainer);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } finally {
                declaredField.setAccessible(false);
            }
        }
        checkForNotPresentedId(fieldContainers);

        return new ScannerResult(originClass.getSimpleName(), fieldContainers);
    }

    @Override
    public ScannerResult scanClass(Class originClass) {
        Field[] declaredFields = originClass.getDeclaredFields();
        List<FieldContainer> fieldContainers = new ArrayList<>();

        for (Field declaredField : declaredFields) {
            FieldContainer fieldContainer = build(declaredField, Optional.empty());
            fieldContainers.add(fieldContainer);
        }

        checkForNotPresentedId(fieldContainers);

        return new ScannerResult(originClass.getSimpleName(), fieldContainers);
    }

    private void checkForNotPresentedId(List<FieldContainer> fieldContainers) {
        if (isIdNotPresented(fieldContainers)) {
            throw new RuntimeException("Id is not present");
        }
    }

    private FieldContainer build(Field declaredField, Optional value) {
        boolean annotationPresent = declaredField.isAnnotationPresent(Id.class);
        FieldType fieldType = annotationPresent ? ID : FIELD;
        return new FieldContainer(declaredField.getName(), value, fieldType);
    }

    private boolean isIdNotPresented(List<FieldContainer> fieldContainers) {
        return fieldContainers.stream().noneMatch(fieldContainer -> fieldContainer.getFieldType() == ID);
    }
}
