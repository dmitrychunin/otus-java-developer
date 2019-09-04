package ru.otus.javadeveloper.hw16.common.api.socket;

import com.google.gson.Gson;
import ru.otus.javadeveloper.hw16.common.api.Address;
import ru.otus.javadeveloper.hw16.common.api.event.Event;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class SocketMessageWorker implements MessageWorker {
    private static final int WORKER_COUNT = 2;

    private final ExecutorService executorService;
    private final Socket socket;
    private final String name;
    private Address to;

    private final BlockingQueue<Event> output = new LinkedBlockingQueue<>();
    private final BlockingQueue<Event> input = new LinkedBlockingQueue<>();

    public SocketMessageWorker(Socket socket, String name) {
        this.socket = socket;
        executorService = Executors.newFixedThreadPool(WORKER_COUNT);
        this.name = name;
    }

    public SocketMessageWorker(String host, int port, String name) throws IOException {
        this(new Socket(host, port), name);
    }

    public void init() {
        executorService.execute(this::sendAbstractEvent);
        executorService.execute(this::receiveAbstractEvent);
    }

    @Override
    public Event pool() {
        return input.poll();
    }

    @Override
    public List<Event> pollAll() {
        List<Event> inputMessages = new ArrayList<>();
        while (!input.isEmpty()) {
            Event poll = input.poll();
            inputMessages.add(poll);
        }
        return inputMessages;
    }

    @Override
    public void push(Event event) {
        output.add(event);
    }

    @Override
    public void pushAll(List<Event> eventList) {
        for (Event event : eventList) {
            output.add(event);
        }
    }

    @Override
    public Event take() throws InterruptedException {
        return input.take();
    }

    @Override
    public void close() throws IOException {
        socket.close();
        executorService.shutdown();
    }

    @Override
    public void addDestination(Address address) {
        this.to = address;
    }

    @Override
    public Address getDestination() {
        return to;
    }

    private void sendAbstractEvent() {
        try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            while (socket.isConnected()) {
                Event event = output.take();
                String json = new Gson().toJson(event);
                System.out.println(name + " take from out " + json);
                out.println(json);
                out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void receiveAbstractEvent() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String inputLine;
            StringBuilder stringBuilder = new StringBuilder();
            while ((inputLine = reader.readLine()) != null) {
                stringBuilder.append(inputLine);
                if (inputLine.isEmpty()) {
                    String json = stringBuilder.toString();
                    Event event = new Gson().fromJson(json, Event.class);
                    System.out.println(name + " add in " + event);
                    input.add(event);
                    stringBuilder = new StringBuilder();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
