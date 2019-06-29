package ru.otus.javadeveloper.hw08.scanner;

import javax.json.*;
import java.lang.reflect.Array;
import java.lang.reflect.Field;

public class JsonObjectConverter {
    
    public JsonObjectBuilder toJsonBuilder(Object originObject) throws IllegalAccessException {
        Class<?> clazz = originObject.getClass();
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();

        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            String name = declaredField.getName();
            declaredField.setAccessible(true);
            if (declaredField.getType().isArray()) {
                JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
                Object array = declaredField.get(originObject);
                int length = Array.getLength(array);
                for (int i = 0; i < length; i++) {
                    Object o = Array.get(array, i);
                    arrayBuilder.add(buildFromObjectValue(o));
                }
                objectBuilder.add(name, arrayBuilder);
            } else {
                JsonValue jsonValue = buildFromObjectField(declaredField, originObject);
                objectBuilder.add(name, jsonValue);
            }
            declaredField.setAccessible(false);
        }
        return objectBuilder;
    }

    private JsonValue buildFromObjectField(Field declaredField, Object originObject) throws IllegalAccessException {
        switch(declaredField.getType().getName()) {
            case "boolean":
                boolean aBoolean = declaredField.getBoolean(originObject);
                return aBoolean ? JsonValue.TRUE : JsonValue.FALSE;
            case "int":
            case "java.lang.Integer":
                int anInt = declaredField.getInt(originObject);
                return Json.createValue(anInt);
            case "java.lang.String":
                String str = (String) declaredField.get(originObject);
                return Json.createValue(str);
            default:
                JsonObjectBuilder innerObjectBuilder = toJsonBuilder(declaredField.get(originObject));
                return innerObjectBuilder.build();
        }
    }

    private JsonValue buildFromObjectValue(Object object) throws IllegalAccessException {
        switch(object.getClass().getName()) {
            case "boolean":
                boolean aBoolean =(Boolean) object;
                return aBoolean ? JsonValue.TRUE : JsonValue.FALSE;
            case "int":
            case "java.lang.Integer":
                int anInt = (Integer) object;
                return Json.createValue(anInt);
            case "java.lang.String":
                String str = (String) object;
                return Json.createValue(str);
            default:
                JsonObjectBuilder innerObjectBuilder = toJsonBuilder(object);
                return innerObjectBuilder.build();
        }
    }
}
