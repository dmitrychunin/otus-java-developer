package ru.otus.javadeveloper.hw16.common.api.event.front;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.javadeveloper.hw16.common.api.Address;
import ru.otus.javadeveloper.hw16.common.api.event.Event;
import ru.otus.javadeveloper.hw16.common.model.User;

import java.util.List;

@Data
@NoArgsConstructor
public class ShowAllUsersEvent extends Event<List<User>> {
    public ShowAllUsersEvent(Address from, Address to, List<User> userList) {
        super(from, to, "show", userList);
    }
}
