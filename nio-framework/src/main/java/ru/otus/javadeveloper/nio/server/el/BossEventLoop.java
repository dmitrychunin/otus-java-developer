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
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

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
            int workerCounter = 0;
            while (!Thread.currentThread().isInterrupted()) {
                log.info("boss: listen new client connection");
                List<Integer> collect = acceptSelector.selectedKeys().stream().map(SelectionKey::interestOps).collect(Collectors.toList());
                log.info("boss: selector has keys: " + collect);
                Iterator<SelectionKey> keys = acceptSelector.selectedKeys().iterator();
                while (keys.hasNext()) {
                    try {
                        SelectionKey key = keys.next();
                        if (key.isAcceptable()) {
                            log.info("boss: accept client connection");

                            SocketChannel socketChannel = serverSocketChannel.accept(); //The socket channel for the new connection
                            socketChannel.configureBlocking(false);

                            workerCounter++;
                            String workerName = "worker-" + workerCounter;
                            EventLoop workerEventLoop = new WorkerEventLoop(socketChannel, workerName);
//                            todo stop disconnected clients???
                            log.info("boss: submit new " + workerName + " event loop");
                            workerExecutor.submit(workerEventLoop::go);
                        } else {
                            throw new RuntimeException("boss: key is not acceptable");
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    keys.remove();
                }
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
