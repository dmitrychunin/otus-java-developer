package ru.otus.javadeveloper.hw16.dbService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import ru.otus.javadeveloper.hw16.common.api.Address;
import ru.otus.javadeveloper.hw16.common.api.event.db.CreateUserEvent;
import ru.otus.javadeveloper.hw16.common.api.event.db.GetUsersEvent;
import ru.otus.javadeveloper.hw16.common.api.socket.MessageWorker;
import ru.otus.javadeveloper.hw16.common.model.User;

@Controller
@RequiredArgsConstructor
public class ApiController {
    private final MessageWorker client;
    private final Address address = new Address("Front");
    private final Address dbAddress = new Address("DB");

    @MessageMapping({"/create"})
    public void createUser(User user) throws JsonProcessingException {
        CreateUserEvent createUserEvent = new CreateUserEvent(address, dbAddress, user);
        String json = new ObjectMapper().writeValueAsString(createUserEvent);
        client.push(json);
    }

    @MessageMapping({"/list"})
    public void returnUserList() throws JsonProcessingException {
        GetUsersEvent getUsersEvent = new GetUsersEvent(address, dbAddress);
        String json = new ObjectMapper().writeValueAsString(getUsersEvent);
        client.push(json);
    }
}
