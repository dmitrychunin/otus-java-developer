package ru.otus.javadeveloper.hw15.ms;

import ru.otus.javadeveloper.hw15.backend.model.User;

import java.util.List;

/**
 * Created by tully.
 */
public interface FrontendClient extends Addressee {
    void returnUserList(List<User> userList);
}

