package ru.otus.javadeveloper.nio.server.el;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;

@Slf4j
@Value
public class BossEventLoop implements EventLoop {
    private final int port;
    private final ExecutorService workerExecutor;

    @Override
    public void go() {

        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
             Selector acceptSelector = Selector.open()) {
            serverSocketChannel.configureBlocking(false);
            ServerSocket serverSocket = serverSocketChannel.socket();
            serverSocket.bind(new InetSocketAddress(port));
            serverSocketChannel.register(acceptSelector, SelectionKey.OP_ACCEPT);
            acceptSelector.select();
            while (!Thread.currentThread().isInterrupted()) {
                log.info("listen new client connection");
                Iterator<SelectionKey> keys = acceptSelector.selectedKeys().iterator();
                while (keys.hasNext()) {
                    try {
                        SelectionKey key = keys.next();
                        if (key.isAcceptable()) {
                            log.info("accept client connection");

                            SocketChannel socketChannel = serverSocketChannel.accept(); //The socket channel for the new connection
                            socketChannel.configureBlocking(false);

                            EventLoop workerEventLoop = new WorkerEventLoop(socketChannel);
//                            todo stop disconnected clients???
                            workerExecutor.submit(workerEventLoop::go);
                        } else {
                            throw new RuntimeException("key is not acceptable");
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    keys.remove();
                }


            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
