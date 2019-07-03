package ru.otus.javadeveloper.hw08.scanner;

import javax.json.*;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Objects;

public class JsonValueConverter {
    
    private JsonValue toJsonValue(Object originObject) throws IllegalAccessException {
        if (Objects.isNull(originObject)) {
            return JsonValue.NULL;
        }
        if (originObject.getClass().isArray()) {
            return buildJsonArray(originObject);
        }
        if (Collection.class.isAssignableFrom(originObject.getClass())) {
            Collection collection = (Collection) originObject;
            return buildJsonArray(collection.toArray());
        }
        Class<?> clazz = originObject.getClass();
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();

        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            String name = declaredField.getName();
            declaredField.setAccessible(true);
            Object fieldObject = declaredField.get(originObject);
            if (declaredField.getType().isArray()) {
                JsonValue array = buildJsonArray(fieldObject);
                objectBuilder.add(name, array);
            } else {
                JsonValue jsonValue = buildJsonValue(fieldObject);
                objectBuilder.add(name, jsonValue);
            }
            declaredField.setAccessible(false);
        }
        return objectBuilder.build();
    }

    public String toJsonString(Object originObject) throws IllegalAccessException {
        return toJsonValue(originObject).toString();
    }

    public String toJsonString(byte b) {
        return toJsonValue(b).toString();
    }
    public String toJsonString(short i) {
        return toJsonValue(i).toString();
    }

    public String toJsonString(int i) {
        return toJsonValue(i).toString();
    }

    public String toJsonString(long l) {
        return toJsonValue(l).toString();
    }

    public String toJsonString(float f) {
        return toJsonValue(f).toString();
    }

    public String toJsonString(double d) {
        return toJsonValue(d).toString();
    }

    public String toJsonString(String s) {
        if (Objects.isNull(s)) {
            return JsonValue.NULL.toString();
        }
        return Json.createValue(s).toString();
    }

    public String toJsonString(char c) {
        return Json.createValue(String.valueOf(c)).toString();
    }

    private JsonValue toJsonValue(byte b) {
        return Json.createValue(b);
    }
    private JsonValue toJsonValue(short i) {
        return Json.createValue(i);
    }

    private JsonValue toJsonValue(int i) {
        return Json.createValue(i);
    }

    private JsonValue toJsonValue(long l) {
        return Json.createValue(l);
    }

    private JsonValue toJsonValue(float f) {
        return Json.createValue(f);
    }

    private JsonValue toJsonValue(double d) {
        return Json.createValue(d);
    }

    private JsonValue toJsonValue(String s) {
        if (Objects.isNull(s)) {
            return JsonValue.NULL;
        }
        return Json.createValue(s);
    }

    private JsonValue toJsonValue(char c) {
        return Json.createValue(String.valueOf(c));
    }

    private JsonArray buildJsonArray(Object array) throws IllegalAccessException {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        int length = Array.getLength(array);
        for (int i = 0; i < length; i++) {
            Object o = Array.get(array, i);
            arrayBuilder.add(buildJsonValue(o));
        }
        return arrayBuilder.build();
    }

    private JsonValue buildJsonValue(Object object) throws IllegalAccessException {
        if (Objects.isNull(object)) {
            return JsonValue.NULL;
        } else if (Boolean.class.equals(object.getClass())) {
            boolean aBoolean = (Boolean) object;
            return aBoolean ? JsonValue.TRUE : JsonValue.FALSE;
        } else if (Integer.class.equals(object.getClass())) {
            int anInt = (Integer) object;
            return toJsonValue(anInt);
        } else if (Short.class.equals(object.getClass())) {
            short anShort = (Short) object;
            return toJsonValue(anShort);
        } else if (Byte.class.equals(object.getClass())) {
            short anByte = (Byte) object;
            return toJsonValue(anByte);
        } else if (Long.class.equals(object.getClass())) {
            long anLong = (Long) object;
            return toJsonValue(anLong);
        } else if (Float.class.equals(object.getClass())) {
            float anFloat = (Float) object;
            return toJsonValue(anFloat);
        } else if (Double.class.equals(object.getClass())) {
            double anDouble = (Double) object;
            return toJsonValue(anDouble);
        } else if (String.class.equals(object.getClass())) {
            String str = (String) object;
            return toJsonValue(str);
        } else if (Character.class.equals(object.getClass())) {
            Character character = (Character) object;
            return toJsonValue(character.toString());
        } else if (Collection.class.isAssignableFrom(object.getClass())) {
            Collection collection = (Collection) object;
            return buildJsonArray(collection.toArray());
        }
        return toJsonValue(object);
    }
}
