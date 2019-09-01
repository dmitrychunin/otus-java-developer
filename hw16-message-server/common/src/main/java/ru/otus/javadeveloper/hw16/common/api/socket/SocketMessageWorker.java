package ru.otus.javadeveloper.hw16.common.api.socket;

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

    private final BlockingQueue<String> output = new LinkedBlockingQueue<>();
    private final BlockingQueue<String> input = new LinkedBlockingQueue<>();

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
    public String pool() {
        return input.poll();
    }

    @Override
    public List<String> pollAll() {
        List<String> inputMessages = new ArrayList<>();
        while (!input.isEmpty()) {
            String poll = input.poll();
            inputMessages.add(poll);
        }
        return inputMessages;
    }

    @Override
    public void push(String event) {
        output.add(event);
    }

    @Override
    public void pushAll(List<String> eventList) {
        for (String event : eventList) {
            output.add(event);
        }
    }

    @Override
    public String take() throws InterruptedException {
        return input.take();
    }

    @Override
    public void close() throws IOException {
        socket.close();
        executorService.shutdown();
    }

    private void sendAbstractEvent() {
        try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            while (socket.isConnected()) {
                String json = output.take();
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
                    System.out.println(name + " add in " + json);
                    input.add(json);
                    stringBuilder = new StringBuilder();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
