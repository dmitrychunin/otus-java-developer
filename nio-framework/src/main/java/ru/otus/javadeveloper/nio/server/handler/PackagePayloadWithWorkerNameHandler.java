package ru.otus.javadeveloper.nio.server.handler;

import ru.otus.javadeveloper.nio.framework.RequestContext;
import ru.otus.javadeveloper.nio.framework.pipeline.handler.ChannelHandler;

public class PackagePayloadWithWorkerNameHandler implements ChannelHandler {
    @Override
    public Object handle(RequestContext ctx, Object message) {
        return ctx.getWorkerName() + ": echo: " + message;
    }
}
