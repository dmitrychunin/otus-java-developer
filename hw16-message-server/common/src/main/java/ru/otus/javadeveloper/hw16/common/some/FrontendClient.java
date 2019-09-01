package ru.otus.javadeveloper.hw16.common.some;

import ru.otus.javadeveloper.hw16.common.model.User;

import java.util.List;

/**
 * Created by tully.
 */
public interface FrontendClient extends Addressee {
    void returnUserList(List<User> userList);

    void createUser(User user);

    void showAllUserList();
}

