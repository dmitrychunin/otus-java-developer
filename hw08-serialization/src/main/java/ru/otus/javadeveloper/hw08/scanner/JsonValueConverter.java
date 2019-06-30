package ru.otus.javadeveloper.hw08.scanner;

import javax.json.*;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Objects;

public class JsonValueConverter {
    
    public JsonValue toJsonValue(Object originObject) throws IllegalAccessException {
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

    public JsonValue toJsonValue(byte b) {
        return Json.createValue(b);
    }
    public JsonValue toJsonValue(short i) {
        return Json.createValue(i);
    }

    public JsonValue toJsonValue(int i) {
        return Json.createValue(i);
    }

    public JsonValue toJsonValue(long l) {
        return Json.createValue(l);
    }

    public JsonValue toJsonValue(float f) {
        return Json.createValue(f);
    }

    public JsonValue toJsonValue(double d) {
        return Json.createValue(d);
    }

    public JsonValue toJsonValue(String s) {
        if (Objects.isNull(s)) {
            return JsonValue.NULL;
        }
        return Json.createValue(s);
    }

    public JsonValue toJsonValue(char c) {
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
            return Json.createValue(anShort);
        } else if (Byte.class.equals(object.getClass())) {
            short anByte = (Byte) object;
            return Json.createValue(anByte);
        } else if (Long.class.equals(object.getClass())) {
            long anLong = (Long) object;
            return Json.createValue(anLong);
        } else if (Float.class.equals(object.getClass())) {
            float anFloat = (Float) object;
            return Json.createValue(anFloat);
        } else if (Double.class.equals(object.getClass())) {
            double anDouble = (Double) object;
            return Json.createValue(anDouble);
        } else if (String.class.equals(object.getClass())) {
            String str = (String) object;
            return Json.createValue(str);
        } else if (Character.class.equals(object.getClass())) {
            Character character = (Character) object;
            return Json.createValue(character.toString());
        } else if (Collection.class.isAssignableFrom(object.getClass())) {
            Collection collection = (Collection) object;
            return buildJsonArray(collection.toArray());
        }
        return toJsonValue(object);
    }
}
