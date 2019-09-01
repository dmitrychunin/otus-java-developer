package ru.otus.javadeveloper.hw16.common.api.event.db;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.javadeveloper.hw16.common.api.Address;
import ru.otus.javadeveloper.hw16.common.api.event.Event;
import ru.otus.javadeveloper.hw16.common.model.User;

@Data
@NoArgsConstructor
public class CreateUserEvent extends Event<User> {

    public CreateUserEvent(Address from, Address to, User user) {
        super(from, to, "create", user);
    }
}
