package ru.otus.javadeveloper.nio.framework.pipeline;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import ru.otus.javadeveloper.nio.framework.RequestContext;
import ru.otus.javadeveloper.nio.framework.pipeline.handler.ChannelHandler;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Value
public class ChannelPipeline {
    private final RequestContext context;
    private final List<ChannelHandler> pipelineHandlerOrder = new ArrayList<>();

    public void addLast(ChannelHandler handler) {
        pipelineHandlerOrder.add(handler);
    }
    public RequestContext start(Object message) {
        for (ChannelHandler channelHandler : pipelineHandlerOrder) {
            message = channelHandler.handle(context, message);
        }
        context.setPayload(message);
        return context;
    }
}
