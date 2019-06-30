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

        JsonValue jsonValue = new JsonValueConverter().toJsonValue(childObject);
        String serializedChildObject = jsonValue.toString();

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

        JsonValue rootJsonValue = new JsonValueConverter().toJsonValue(originalRootObject);
        String serializedRootObject = rootJsonValue.toString();

        Gson gson = new Gson();
        RootObject deserializedRootObject = gson.fromJson(serializedRootObject, RootObject.class);
        assertEquals(originalRootObject, deserializedRootObject);
    }

    @Test
    public void customTest() throws IllegalAccessException {
        Gson gson = new Gson();
        JsonValueConverter jsonValueConverter = new JsonValueConverter();
        assertEquals(jsonValueConverter.toJsonValue(null).toString(), gson.toJson(null));
        assertEquals(jsonValueConverter.toJsonValue((byte) 1).toString(), gson.toJson((byte) 1));
        assertEquals(jsonValueConverter.toJsonValue((short) 1f).toString(), gson.toJson((short) 1f));
        assertEquals(jsonValueConverter.toJsonValue(1).toString(), gson.toJson(1));
        assertEquals(jsonValueConverter.toJsonValue(1L).toString(), gson.toJson(1L));
        assertEquals(jsonValueConverter.toJsonValue(1f).toString(), gson.toJson(1f));
        assertEquals(jsonValueConverter.toJsonValue(1d).toString(), gson.toJson(1d));
        assertEquals(jsonValueConverter.toJsonValue("aaa").toString(), gson.toJson("aaa"));
        assertEquals(jsonValueConverter.toJsonValue('a').toString(), gson.toJson('a'));
        assertEquals(jsonValueConverter.toJsonValue(new int[]{1, 2, 3}).toString(), gson.toJson(new int[]{1, 2, 3}));
        assertEquals(jsonValueConverter.toJsonValue(List.of(1, 2, 3)).toString(), gson.toJson(List.of(1, 2, 3)));
        assertEquals(jsonValueConverter.toJsonValue(Collections.singletonList(1)).toString(), gson.toJson(Collections.singletonList(1)));
    }
}
