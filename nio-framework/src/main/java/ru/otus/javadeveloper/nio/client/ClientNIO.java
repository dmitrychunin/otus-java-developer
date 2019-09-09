package ru.otus.javadeveloper.nio.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

public class ClientNIO {
    private static Logger logger = LoggerFactory.getLogger(ClientNIO.class);

    private static final int PORT = 8080;
    private static final String HOST = "localhost";

    public static void main(String[] args) {
        new Thread(() -> new ClientNIO().go("testData_0")).start();
        new Thread(() -> new ClientNIO().go("testData_1")).start();
        new Thread(() -> new ClientNIO().go("testData_2")).start();
        new Thread(() -> new ClientNIO().go("testData_3")).start();
        new Thread(() -> new ClientNIO().go("testData_4")).start();
        new Thread(() -> new ClientNIO().go("testData_5")).start();
        new Thread(() -> new ClientNIO().go("testData_6")).start();
        new Thread(() -> new ClientNIO().go("testData_7")).start();
        new Thread(() -> new ClientNIO().go("testData_8")).start();
        new Thread(() -> new ClientNIO().go("testData_9")).start();
        new Thread(() -> new ClientNIO().go("testData_10")).start();
        new Thread(() -> new ClientNIO().go("testData_11")).start();
        new Thread(() -> new ClientNIO().go("testData_12")).start();
        new Thread(() -> new ClientNIO().go("testData_13")).start();
        new Thread(() -> new ClientNIO().go("testData_14")).start();
        new Thread(() -> new ClientNIO().go("testData_15")).start();
        new Thread(() -> new ClientNIO().go("testData_16")).start();
        new Thread(() -> new ClientNIO().go("testData_17")).start();
        new Thread(() -> new ClientNIO().go("testData_18")).start();
        new Thread(() -> new ClientNIO().go("testData_19")).start();
    }

    private void go(String request) {
        try {
            try (SocketChannel socketChannel = SocketChannel.open()) {
                socketChannel.configureBlocking(false);

                socketChannel.connect(new InetSocketAddress(HOST, PORT));

                logger.info(Thread.currentThread().getName() + ": connecting to server");
                while (!socketChannel.finishConnect()) {
                    logger.info(Thread.currentThread().getName() + ": connection established");
                }
                send(socketChannel, request);
                logger.info(Thread.currentThread().getName() + ": stop communication");
                send(socketChannel, "stop\n");
            }
        } catch (Exception ex) {
            logger.error(Thread.currentThread().getName() + ": error", ex);
        }
    }

    private void send(SocketChannel socketChannel, String request) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1000);
        buffer.put(request.getBytes());
        buffer.flip();
        logger.info(Thread.currentThread().getName() + ": sending to server request: " + request);
        socketChannel.write(buffer);

        Selector selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_READ);
        while (true) {
            logger.info(Thread.currentThread().getName() + ": waiting for response");
            if (selector.select() > 0) { //This method performs a blocking
                if (processServerResponse(selector)) {
                    return;
                }
            }
        }
    }

    private boolean processServerResponse(Selector selector) throws IOException {
        logger.info(Thread.currentThread().getName() + ": processServerResponse");
        ByteBuffer buffer = ByteBuffer.allocate(1000);
        Iterator<SelectionKey> selectedKeys = selector.selectedKeys().iterator();
        while (selectedKeys.hasNext()) {
            SelectionKey key = selectedKeys.next();
            if (key.isReadable()) {
                SocketChannel socketChannel = (SocketChannel) key.channel();
                int count = socketChannel.read(buffer);
                if (count > 0) {
                    buffer.flip();
                    String response = Charset.forName("UTF-8").decode(buffer).toString();
                    logger.info(Thread.currentThread().getName() + ": response: {}", response);
                    buffer.clear();
                    buffer.flip();
                    return true;
                }
            }
            selectedKeys.remove();
        }
        return false;
    }

    private static void sleep() {
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(10));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

