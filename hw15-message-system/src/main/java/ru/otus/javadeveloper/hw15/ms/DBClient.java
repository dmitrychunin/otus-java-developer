package ru.otus.javadeveloper.hw15.ms;

import ru.otus.javadeveloper.hw15.backend.model.User;

/**
 * Created by tully.
 */
public interface DBClient extends Addressee {
    void createUser(User user);
}
