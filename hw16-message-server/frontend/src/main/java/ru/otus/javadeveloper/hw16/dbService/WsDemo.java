package ru.otus.javadeveloper.hw16.dbService;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import ru.otus.javadeveloper.hw16.common.api.event.front.ShowAllUsersEvent;
import ru.otus.javadeveloper.hw16.common.api.socket.MessageWorker;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication(scanBasePackages = "ru.otus.javadeveloper.hw16")
@RequiredArgsConstructor
public class WsDemo implements CommandLineRunner {
    private final MessageWorker messageWorker;
    private final SimpMessagingTemplate messagingTemplate;

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
                    ShowAllUsersEvent event = new ObjectMapper().readValue(json, ShowAllUsersEvent.class);
                    messagingTemplate.convertAndSend("/topic/response", event.getPayload());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        executorService.shutdown();
    }
}
