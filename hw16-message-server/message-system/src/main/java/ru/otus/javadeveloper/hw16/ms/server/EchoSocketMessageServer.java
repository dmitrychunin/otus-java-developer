package ru.otus.javadeveloper.hw16.ms.server;

import ru.otus.javadeveloper.hw16.common.api.socket.MessageWorker;
import ru.otus.javadeveloper.hw16.common.api.socket.SocketMessageWorker;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EchoSocketMessageServer {
    private static final int THREADS_COUNT = 1;
    private static final int PORT = 5050;

    private final ExecutorService excecutorService;
    private final List<MessageWorker> workers;

    public EchoSocketMessageServer() {
        System.out.println("Start server");
        excecutorService = Executors.newFixedThreadPool(THREADS_COUNT);
        workers = new CopyOnWriteArrayList<>();
    }

    public void start() throws Exception {
        excecutorService.submit(this::broadcastMessagesToCorrespondingAddressWorkers);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {

            while (!excecutorService.isShutdown()) {
                Socket socket = serverSocket.accept();  //blocks
                SocketMessageWorker worker = new SocketMessageWorker(socket, "message-system");
                worker.init();
                workers.add(worker);
            }
        }
    }

    private void broadcastMessagesToCorrespondingAddressWorkers() {
        while (true) {
            Map<MessageWorker, List<String>> map = new HashMap<>();
            for (MessageWorker worker : workers) {
                List<String> message = worker.pollAll();
                map.put(worker, message);
            }

            for (MessageWorker worker : workers) {
                for (Map.Entry<MessageWorker, List<String>> messageWorkerListEntry : map.entrySet()) {
                    if (messageWorkerListEntry.getKey() != worker) {
                        worker.pushAll(messageWorkerListEntry.getValue());
                    }
                }
            }
        }

    }
}
