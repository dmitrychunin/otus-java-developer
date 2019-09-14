package ru.otus.javadeveloper.nio.framework.pipeline.handler;

import ru.otus.javadeveloper.nio.framework.RequestContext;

public interface ChannelHandler {
//    todo remove Object from return
    Object handle(RequestContext ctx, Object message);
}
