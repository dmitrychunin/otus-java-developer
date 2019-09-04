package ru.otus.javadeveloper.nio.server;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;

@Slf4j
public class NettyMain {

    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException {
        new NettyMain().go();
    }

    private void go() throws IOException {
        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
             Selector selector = Selector.open()) {
            serverSocketChannel.configureBlocking(false);

            ServerSocket serverSocket = serverSocketChannel.socket();
            serverSocket.bind(new InetSocketAddress(PORT));

            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            selector.select();
            while (!Thread.currentThread().isInterrupted()) {
                log.info("something happened");
                Iterator<SelectionKey> keys = selector.selectedKeys().iterator();

                while (keys.hasNext()) {
                    SelectionKey key = keys.next();
                    if (key.isAcceptable()) {
                        log.info("accept client connection");
                        SocketChannel socketChannel = serverSocketChannel.accept(); //The socket channel for the new connection

                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector, SelectionKey.OP_READ);
                    } else if (key.isReadable()) {
                        log.info("read from client");
                        SocketChannel socketChannel = (SocketChannel) key.channel();

                        ByteBuffer buffer = ByteBuffer.allocate(5);
                        StringBuilder inputBuffer = new StringBuilder(100);

                        while (socketChannel.read(buffer) > 0) {
                            buffer.flip();
                            String input = Charset.forName("UTF-8").decode(buffer).toString();
                            log.info("from client: {} ", input);

                            buffer.flip();
                            buffer.clear();
                            inputBuffer.append(input);
                        }

                        String requestFromClient = inputBuffer.toString();
                        log.info("requestFromClient: {} ", requestFromClient);

                        byte[] response = ("echo: " + requestFromClient).getBytes();
                        for (byte b : response) {
                            buffer.put(b);
                            if (buffer.position() == buffer.limit()) {
                                buffer.flip();
                                socketChannel.write(buffer);
                                buffer.flip();
                                buffer.clear();
                            }
                        }
                        if (buffer.hasRemaining()) {
                            buffer.flip();
                            socketChannel.write(buffer);
                        }

                        if ("stop\n".equals(requestFromClient)) {
                            socketChannel.close();
                        }
                    } else if (key.isWritable()) {

                    }

                    keys.remove();
                }
            }
        }
    }
}
