package ru.otus.javadeveloper.nio.server.handler;

import lombok.extern.slf4j.Slf4j;
import ru.otus.javadeveloper.nio.framework.Context;
import ru.otus.javadeveloper.nio.framework.pipeline.handler.ChannelHandler;

import java.io.IOException;

@Slf4j
//todo add new handler class
public class StopConnectionHandler implements ChannelHandler {
    @Override
    public Object handle(Context ctx, Object message) {
        try {
            if ("stop\n".equals(((String) message).toLowerCase())) {
                log.info("{} channel closed by client request", ctx.getWorkerName());
                ctx.getSocket().close();
                ctx.setSocketClosed(true);
                return ctx;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
