package ru.otus.javadeveloper.hw16.ms.server;

import ru.otus.javadeveloper.hw16.common.api.Address;
import ru.otus.javadeveloper.hw16.common.api.event.Event;
import ru.otus.javadeveloper.hw16.common.api.socket.MessageWorker;
import ru.otus.javadeveloper.hw16.common.api.socket.SocketMessageWorker;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

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
        excecutorService.submit(this::pushAllInputMessagesIntoCorrespondingWorkers);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {

            while (!excecutorService.isShutdown()) {
                Socket socket = serverSocket.accept();  //blocks
                SocketMessageWorker worker = new SocketMessageWorker(socket, "message-system");
                worker.init();
                worker.push(new Event(new Address("ms"), null, "getToRequest", null));
                Event pool;
                while ((pool = worker.pool()) == null) ;
                if (!"getToResponse".equals(pool.getEventId())) {
                    throw new RuntimeException("unable bind worker to destination: unexpected first message type");
                }
                Address payload = pool.getFrom();
                if (payload == null) {
                    throw new RuntimeException("unable bind worker to destination: empty address");
                }
                worker.addDestination(payload);
                workers.add(worker);
            }
        }
    }

    private void pushAllInputMessagesIntoCorrespondingWorkers() {
        try {
            while (true) {
                Map<Address, List<Event>> collect = workers.stream()
                        .flatMap(worker -> worker.pollAll().stream())
                        .collect(Collectors.groupingBy(Event::getTo, toList()));

                for (MessageWorker worker : workers) {
                    Address destination = worker.getDestination();
                    worker.pushAll(Optional.ofNullable(collect.get(destination)).orElseGet(ArrayList::new));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}


