package ru.otus.javadeveloper.hw08.pojo;

import lombok.Data;

@Data
public class RootObject {
    private final int int1 = 5;
    private String str1;
    private Integer[] arrayOfInt;
    private ChildObject[] childObjects;
    private ChildObject childObject;
}
