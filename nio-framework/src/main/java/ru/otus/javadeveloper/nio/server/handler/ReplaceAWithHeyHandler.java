package ru.otus.javadeveloper.nio.server.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import ru.otus.javadeveloper.nio.framework.RequestContext;
import ru.otus.javadeveloper.nio.framework.pipeline.handler.ChannelHandler;

@Slf4j
public class ReplaceAWithHeyHandler implements ChannelHandler {
    @Override
    public Object handle(RequestContext ctx, Object message) {
        log.info("{}: before replace: {} ", ctx.getWorkerName(), message);
        message = StringUtils.replaceIgnoreCase((String) message, "A", "Hey");
        log.info("{}: after replace: {}", ctx.getWorkerName(), message);
        return message;
    }
}
