package ru.otus.javadeveloper.hw08;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import ru.otus.javadeveloper.hw08.pojo.ChildObject;
import ru.otus.javadeveloper.hw08.pojo.RootObject;
import ru.otus.javadeveloper.hw08.scanner.JsonObjectConverter;

import javax.json.JsonObject;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MySerializerTest {

    @Test
    public void originalAndDeserializedChildObjectShouldBeEquals() throws IllegalAccessException {
        ChildObject childObject = new ChildObject(true, false, 123, "str1");

        JsonObject jsonObject = new JsonObjectConverter().toJsonBuilder(childObject).build();
        String serializedChildObject = jsonObject.toString();

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

        JsonObject rootJsonObject = new JsonObjectConverter().toJsonBuilder(originalRootObject).build();
        String serializedRootObject = rootJsonObject.toString();

        Gson gson = new Gson();
        RootObject deserializedRootObject = gson.fromJson(serializedRootObject, RootObject.class);
        assertEquals(originalRootObject, deserializedRootObject);
    }
}
