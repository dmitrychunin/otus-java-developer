package ru.otus.javadeveloper.nio.framework;

import lombok.Data;

import java.nio.channels.SocketChannel;

@Data
public class Context {
    private final SocketChannel socket;
    private final String workerName;
    private boolean isSocketClosed = false;
    private Object payload;
}
