package ru.otus.javadeveloper.hw16.dbService;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import ru.otus.javadeveloper.hw16.common.api.Address;
import ru.otus.javadeveloper.hw16.common.api.event.Event;
import ru.otus.javadeveloper.hw16.common.api.socket.MessageWorker;
import ru.otus.javadeveloper.hw16.common.model.User;

@Controller
@RequiredArgsConstructor
public class ApiController {
    private final MessageWorker client;
    private final Address address = new Address("Front");
    private final Address dbAddress = new Address("DB");

    @MessageMapping({"/create"})
    public void createUser(User user) {
        Event<User> createUserEvent = new Event<>(address, dbAddress, "create", user);
        client.push(createUserEvent);
    }

    @MessageMapping({"/list"})
    public void returnUserList() {
        Event<User> getUserEvent = new Event<>(address, dbAddress, "get", null);
        client.push(getUserEvent);
    }
}
