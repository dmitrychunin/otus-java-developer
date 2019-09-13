package ru.otus.javadeveloper.nio.server.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import ru.otus.javadeveloper.nio.framework.Context;
import ru.otus.javadeveloper.nio.framework.pipeline.handler.ChannelHandler;

@Slf4j
public class InverseLetterCaseHandler implements ChannelHandler {
    @Override
    public Object handle(Context ctx, Object message) {
        log.info("{}: before inverse: {} ", ctx.getWorkerName(), message);
        message = StringUtils.swapCase((String) message);
        log.info("{}: after inverse: {}", ctx.getWorkerName(), message);
        return message;
    }
}
