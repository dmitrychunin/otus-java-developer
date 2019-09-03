package ru.otus.javadeveloper.hw16.ms;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.otus.javadeveloper.hw16.ms.runner.ProcessRunnerImpl;
import ru.otus.javadeveloper.hw16.ms.server.EchoSocketMessageServer;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SpringBootApplication(scanBasePackages = "ru.otus.javadeveloper.hw16.ms")
public class WsDemo implements CommandLineRunner {

    //    todo remove absolute path
    private static final String CLIENT_START_COMMAND1 = "java -jar " + "hw16-message-server" + File.separator + "frontend" + File.separator + "target" + File.separator + "frontend.jar --server.port=5051";
    private static final String CLIENT_START_COMMAND2 = "java -jar " + "hw16-message-server" + File.separator + "frontend" + File.separator + "target" + File.separator + "frontend.jar --server.port=5052";
    private static final String CLIENT_START_COMMAND3 = "java -jar " + "hw16-message-server" + File.separator + "db-service" + File.separator + "target" + File.separator + "db-service.jar --server.port=5053";
    private static final String CLIENT_START_COMMAND4 = "java -jar " + "hw16-message-server" + File.separator + "db-service" + File.separator + "target" + File.separator + "db-service.jar --server.port=5054";
    private static final int CLIENT_START_DELAY_SEC = 5;

    public static void main(String[] args) {
        SpringApplication.run(WsDemo.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        start();
    }

    private void start() throws Exception {

        EchoSocketMessageServer server = new EchoSocketMessageServer();

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(5);

        startClient(executorService, CLIENT_START_COMMAND1);
        startClient(executorService, CLIENT_START_COMMAND2);
        startClient(executorService, CLIENT_START_COMMAND3);
        startClient(executorService, CLIENT_START_COMMAND4);

        server.start();
        executorService.shutdown();
    }

    private void startClient(ScheduledExecutorService executorService, String command) {
        executorService.schedule(() -> {
            try {
                System.out.println("start process");
                new ProcessRunnerImpl().start(command);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }, CLIENT_START_DELAY_SEC, TimeUnit.SECONDS);

    }
}
