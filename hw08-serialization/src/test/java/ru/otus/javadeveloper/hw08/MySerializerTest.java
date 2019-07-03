package ru.otus.javadeveloper.hw08;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import ru.otus.javadeveloper.hw08.pojo.ChildObject;
import ru.otus.javadeveloper.hw08.pojo.RootObject;
import ru.otus.javadeveloper.hw08.scanner.JsonValueConverter;

import javax.json.JsonObject;
import javax.json.JsonValue;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MySerializerTest {

    @Test
    public void originalAndDeserializedChildObjectShouldBeEquals() throws IllegalAccessException {
        ChildObject childObject = new ChildObject(true, false, 123, "str1");

        String serializedChildObject = new JsonValueConverter().toJsonString(childObject);

        Gson gson = new Gson();
        ChildObject deserializedChildObject = gson.fromJson(serializedChildObject, ChildObject.class);
        assertEquals(childObject, deserializedChildObject);
    }

    @Test
    public void originalAndDeserializedRootObjectShouldBeEquals() throws IllegalAccessException {
        RootObject originalRootObject = new RootObject();
        originalRootObject.setChildObject(new ChildObject(true, false, 123, "str0"));
        originalRootObject.setStr1("string1");
        Integer[] arrayOfInt = {1, 2, 3};
        originalRootObject.setArrayOfInt(arrayOfInt);
        ChildObject[] childObjects = {new ChildObject(false, false, 456, "str3"),
                new ChildObject(true, true, 789, "str4")};
        originalRootObject.setChildObjects(childObjects);

        String serializedRootObject = new JsonValueConverter().toJsonString(originalRootObject);

        Gson gson = new Gson();
        RootObject deserializedRootObject = gson.fromJson(serializedRootObject, RootObject.class);
        assertEquals(originalRootObject, deserializedRootObject);
    }

    @Test
    public void customTest() throws IllegalAccessException {
        Gson gson = new Gson();
        JsonValueConverter jsonValueConverter = new JsonValueConverter();
        assertEquals(jsonValueConverter.toJsonString(null), gson.toJson(null));
        assertEquals(jsonValueConverter.toJsonString((byte) 1), gson.toJson((byte) 1));
        assertEquals(jsonValueConverter.toJsonString((short) 1f), gson.toJson((short) 1f));
        assertEquals(jsonValueConverter.toJsonString(1), gson.toJson(1));
        assertEquals(jsonValueConverter.toJsonString(1L), gson.toJson(1L));
        assertEquals(jsonValueConverter.toJsonString(1f), gson.toJson(1f));
        assertEquals(jsonValueConverter.toJsonString(1d), gson.toJson(1d));
        assertEquals(jsonValueConverter.toJsonString("aaa"), gson.toJson("aaa"));
        assertEquals(jsonValueConverter.toJsonString('a'), gson.toJson('a'));
        assertEquals(jsonValueConverter.toJsonString(new int[]{1, 2, 3}), gson.toJson(new int[]{1, 2, 3}));
        assertEquals(jsonValueConverter.toJsonString(List.of(1, 2, 3)), gson.toJson(List.of(1, 2, 3)));
        assertEquals(jsonValueConverter.toJsonString(Collections.singletonList(1)), gson.toJson(Collections.singletonList(1)));
    }
}
