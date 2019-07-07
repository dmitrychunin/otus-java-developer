package ru.otus.javadeveloper.hw09.executor.utils;

import com.google.common.primitives.Primitives;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ReflectionUtils<T> {
    public T createObject(Object[] fieldValues, Class<T> clazz) {
        Constructor<?>[] constructors = clazz.getConstructors();
        for (Constructor<?> constructor : constructors) {
            try {
                if (isRowColumnsMatchConstructorArgs(constructor, fieldValues)) {
                    return (T) constructor.newInstance(fieldValues);
                }
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        throw new RuntimeException("Matching constructor is not found");
    }

    private boolean isRowColumnsMatchConstructorArgs(Constructor<?> constructor, Object[] fieldValues) {
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        if (constructor.getParameterCount() != fieldValues.length) {
            return false;
        }
        for (int i = 0; i < constructor.getParameterCount(); i++) {
            Class<?> constructorParameterWrappedType = Primitives.wrap(parameterTypes[i]);
            Class<?> columnWrappedType = Primitives.wrap(fieldValues[i].getClass());

            if (!constructorParameterWrappedType.equals(columnWrappedType)) {
                return false;
            }
        }
        return true;
    }
}
