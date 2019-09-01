package ru.otus.javadeveloper.hw16.dbService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.otus.javadeveloper.hw16.common.api.Address;
import ru.otus.javadeveloper.hw16.common.api.event.Event;
import ru.otus.javadeveloper.hw16.common.api.event.front.ShowAllUsersEvent;
import ru.otus.javadeveloper.hw16.common.api.socket.MessageWorker;
import ru.otus.javadeveloper.hw16.common.model.User;
import ru.otus.javadeveloper.hw16.dbService.service.DBService;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication(scanBasePackages = "ru.otus.javadeveloper.hw16")
@RequiredArgsConstructor
public class WsDemo implements CommandLineRunner {
    private final MessageWorker messageWorker;
    private final DBService<User> dbService;
    private final Address address = new Address("DB");
    private final Address frontAddress = new Address("Front");

    public static void main(String[] args) {
        SpringApplication.run(WsDemo.class, args);
    }

    @Override
    public void run(String... args) {

        ExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
//        todo abstract event listener?
        executorService.submit(() -> {
            while (true) {
                try {
                    String json = messageWorker.take();
                    Event event = new ObjectMapper().readValue(json, Event.class);
                    switch (event.getEventId()) {
                        case "create":
                            User user = new ObjectMapper().convertValue(event.getPayload(), User.class);
                            dbService.save(user);
                        case "get":
                            getAllUserList();
                            break;
                        default:
                            System.out.println("not my message");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        executorService.shutdown();
    }

    private void returnResult(List<User> all) throws JsonProcessingException {
        ShowAllUsersEvent showAllUsersEvent = new ShowAllUsersEvent(address, frontAddress, all);
        String json = new ObjectMapper().writeValueAsString(showAllUsersEvent);
        messageWorker.push(json);
    }

    private void getAllUserList() throws JsonProcessingException {
        List<User> all = dbService.getAll(User.class);
        returnResult(all);
    }
}
