package ru.otus.javadeveloper.hw16.dbService;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import ru.otus.javadeveloper.hw16.common.api.Address;
import ru.otus.javadeveloper.hw16.common.api.event.Event;
import ru.otus.javadeveloper.hw16.common.api.socket.MessageWorker;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication(scanBasePackages = "ru.otus.javadeveloper.hw16")
@RequiredArgsConstructor
public class WsDemo implements CommandLineRunner {
    private final MessageWorker messageWorker;
    private final SimpMessagingTemplate messagingTemplate;
    private final Address address = new Address("Front");
    private final Address messageSystemAddress = new Address("MS");

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
                            Event getToEvent = new Event(address, messageSystemAddress, "getToResponse", null);
                            messageWorker.push(getToEvent);
                            break;
                        case "show":
                            messagingTemplate.convertAndSend("/topic/response", event.getPayload());
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
}
