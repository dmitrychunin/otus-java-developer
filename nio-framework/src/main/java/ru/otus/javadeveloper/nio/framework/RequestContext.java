package ru.otus.javadeveloper.nio.framework;

import lombok.Getter;

import java.net.URI;

@Getter
public class RequestContext {
//    todo add params?
    private final URI uri;
    private final String httpPayload;

    public RequestContext(String rawHttpRequest) {
        String[] splittedHttpRequest = rawHttpRequest.split("\r\n");
        String[] s = splittedHttpRequest[0].split(" ");
        String method = s[0];
        String url = s[1];
        URI uri = URI.create(url);
        this.uri = uri;
        this.httpPayload = splittedHttpRequest[splittedHttpRequest.length-1];
    }
}
