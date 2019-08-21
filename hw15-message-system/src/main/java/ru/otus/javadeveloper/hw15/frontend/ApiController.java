package ru.otus.javadeveloper.hw15.frontend;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import ru.otus.javadeveloper.hw15.backend.model.User;
import ru.otus.javadeveloper.hw15.ms.FrontendClient;

@Controller
@RequiredArgsConstructor
public class ApiController {
    private final FrontendClient frontendClient;

    @MessageMapping({"/create"})
    public void createUser(User user) {
        frontendClient.createUser(user);
    }

    @MessageMapping({"/list"})
    public void returnUserList() {
        frontendClient.showAllUserList();
    }
}
