package ru.otus.javadeveloper.hw09.scanner;

import ru.otus.javadeveloper.hw09.executor.Id;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class EntityScannerImpl implements EntityScanner {
    @Override
    public ObjectScannerResult scanObject(Object origin) {
        Class<?> originClass = origin.getClass();
        Field[] declaredFields = originClass.getDeclaredFields();
        String idName = null;
        Long idValue = null;
        Map<String, Object> fieldMap = new HashMap<>();
        boolean isIdEverPresented = false;

        for (Field declaredField : declaredFields) {
            try {
                declaredField.setAccessible(true);
                boolean annotationPresent = declaredField.isAnnotationPresent(Id.class);
                if (annotationPresent) {
                    idName = declaredField.getName();
                    idValue = declaredField.getLong(origin);
                    isIdEverPresented = true;
                } else {
                    fieldMap.put(declaredField.getName(), declaredField.get(origin));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } finally {
                declaredField.setAccessible(false);
            }
        }
        if (!isIdEverPresented) {
            throw new RuntimeException("Id is not present");
        }
        return new ObjectScannerResult(originClass.getSimpleName(), idName, idValue, fieldMap);
    }

    @Override
    public ClassScannerResult scanClass(Class originClass) {
        Field[] declaredFields = originClass.getDeclaredFields();
        String idName = null;
        boolean isIdEverPresented = false;

        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            boolean annotationPresent = declaredField.isAnnotationPresent(Id.class);
            if (annotationPresent) {
                idName = declaredField.getName();
                isIdEverPresented = true;
            }
        }
        if (!isIdEverPresented) {
            throw new RuntimeException("Id is not present");
        }
        return new ClassScannerResult(originClass.getSimpleName(), idName);
    }
}
