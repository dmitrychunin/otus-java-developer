package ru.otus.javadeveloper.hw16.common.api.socket;

import java.io.IOException;
import java.util.List;

public interface MessageWorker {
    String pool();

    List<String> pollAll();

    void push(String event);

    void pushAll(List<String> eventList);

    String take() throws InterruptedException;

    void close() throws IOException;
}
