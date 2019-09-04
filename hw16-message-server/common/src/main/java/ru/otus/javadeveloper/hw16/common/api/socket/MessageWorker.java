package ru.otus.javadeveloper.hw16.common.api.socket;

import ru.otus.javadeveloper.hw16.common.api.Address;
import ru.otus.javadeveloper.hw16.common.api.event.Event;

import java.io.IOException;
import java.util.List;

public interface MessageWorker {
    Event pool();

    List<Event> pollAll();

    void push(Event event);

    void pushAll(List<Event> eventList);

    Event take() throws InterruptedException;

    void close() throws IOException;

    void addDestination(Address address);

    Address getDestination();
}
