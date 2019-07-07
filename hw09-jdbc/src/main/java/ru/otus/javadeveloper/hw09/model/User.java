package ru.otus.javadeveloper.hw09.model;

import lombok.*;
import ru.otus.javadeveloper.hw09.executor.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "id")
public class User {
    @Id
    private long id;
    private String name;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    private int age;
}
