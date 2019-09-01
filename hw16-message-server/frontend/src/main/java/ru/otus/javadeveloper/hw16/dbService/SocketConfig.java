package ru.otus.javadeveloper.hw16.dbService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.javadeveloper.hw16.common.api.socket.MessageWorker;
import ru.otus.javadeveloper.hw16.common.api.socket.SocketMessageWorker;

import java.io.IOException;

@Configuration
public class SocketConfig {
    private static final String HOST = "localhost";
    private static final int PORT = 5050;

    @Bean
    public MessageWorker messageWorker() throws IOException {
        SocketMessageWorker client = new SocketMessageWorker(HOST, PORT, "frontend");
        client.init();
        System.out.println("Start client ");
        return client;
    }
}
