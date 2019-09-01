package ru.otus.javadeveloper.hw16.dbService.backend;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.javadeveloper.hw16.common.api.Address;
import ru.otus.javadeveloper.hw16.common.model.User;
import ru.otus.javadeveloper.hw16.common.some.DBClient;
import ru.otus.javadeveloper.hw16.dbService.service.DBService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DBClientImpl implements DBClient {
    private final Address address = new Address("DB");
    private final DBService<User> dbService;
    private final Address frontAddress = new Address("Front");
//    todo remove hardcode pull from ms

//    @PostConstruct
//    public void initialize() {
//        messagingTemplate.convertAndSend("/topic/ms/add-addressee", this);
//        messagingTemplate.convertAndSend("/topic/ms/add-front-address", address);
//    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public void createUser(User entity) {
        dbService.save(entity);
        List<User> all = dbService.getAll(User.class);
        returnResult(all);
    }

    private void returnResult(List<User> all) {
//        Message message = new Message<FrontendClient>(address, frontAddress, user, command) {
//            @Override
//            public void exec(FrontendClient frontendClient) {
//                frontendClient.returnUserList(all);
//            }
//        };
//        messagingTemplate.convertAndSend("/topic/ms/send-message", message);
    }

    @Override
    public void getAllUserList() {
        List<User> all = dbService.getAll(User.class);
        returnResult(all);
    }
}
