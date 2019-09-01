package ru.otus.javadeveloper.hw16.common.some;

import ru.otus.javadeveloper.hw16.common.model.User;

/**
 * Created by tully.
 */
public interface DBClient extends Addressee {
    void createUser(User user);

    void getAllUserList();
}
