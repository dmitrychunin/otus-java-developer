package ru.otus.javadeveloper.nio.framework.pipeline.handler;

import ru.otus.javadeveloper.nio.framework.Context;

public interface ChannelHandler {
//    todo remove Object from return
    Object handle(Context ctx, Object message);
}
