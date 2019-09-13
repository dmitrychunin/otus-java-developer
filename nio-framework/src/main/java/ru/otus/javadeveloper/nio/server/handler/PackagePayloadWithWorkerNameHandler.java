package ru.otus.javadeveloper.nio.server.handler;

import ru.otus.javadeveloper.nio.framework.Context;
import ru.otus.javadeveloper.nio.framework.pipeline.handler.ChannelHandler;

public class PackagePayloadWithWorkerNameHandler implements ChannelHandler {
    @Override
    public Object handle(Context ctx, Object message) {
        return ctx.getWorkerName() + ": echo: " + message;
    }
}
