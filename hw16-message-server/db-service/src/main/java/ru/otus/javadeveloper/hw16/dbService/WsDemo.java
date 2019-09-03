package ru.otus.javadeveloper.hw16.dbService;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.otus.javadeveloper.hw16.common.api.Address;
import ru.otus.javadeveloper.hw16.common.api.event.Event;
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
                    Event event = messageWorker.take();
                    switch (event.getEventId()) {
                        case "getToRequest":
                            Event getToEvent = new Event(address, frontAddress, "getToResponse", null);
                            messageWorker.push(getToEvent);
                            break;
                        case "create":
                            User user = new ObjectMapper().convertValue(event.getPayload(), User.class);
                            dbService.save(user);
                        case "get":
                            getAllUserList();
                            break;
                        default:
                            throw new RuntimeException("undefined event");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        executorService.shutdown();
    }

    private void returnResult(List<User> all) {
        Event<List<User>> showAllUsersEvent = new Event<>(address, frontAddress, "show", all);
        messageWorker.push(showAllUsersEvent);
    }

    private void getAllUserList() {
        List<User> all = dbService.getAll(User.class);
        returnResult(all);
    }
}
