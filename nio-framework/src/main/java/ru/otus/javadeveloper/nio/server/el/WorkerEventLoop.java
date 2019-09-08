package ru.otus.javadeveloper.nio.server.el;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;

@Slf4j
@Data
@AllArgsConstructor
public class WorkerEventLoop implements EventLoop {
    //    TODO ADD socket LIST???
//    todo make final and @value
    private SocketChannel socketChannel;
    private final String name;

    @Override
    public void go() {
        try (Selector readSelector = Selector.open()) {
            socketChannel.register(readSelector, SelectionKey.OP_READ);

            readSelector.select();
            while (!Thread.currentThread().isInterrupted()) {
                log.info(name + ": listen new read ready clients");
                Iterator<SelectionKey> readKeys = readSelector.selectedKeys().iterator();
                while (readKeys.hasNext()) {
                    SelectionKey key = readKeys.next();
                    if (key.isReadable()) {
                       handleReadAndWriteEvent();
                    } else {
                        throw new RuntimeException(name + ": key is not readable");
                    }
                    readKeys.remove();
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

    private void handleReadAndWriteEvent() throws IOException {
        log.info(name + ": read from client");

        ByteBuffer buffer = ByteBuffer.allocate(5);
        StringBuilder inputBuffer = new StringBuilder(100);

        while (socketChannel.read(buffer) > 0) {
            buffer.flip();
            String input = Charset.forName("UTF-8").decode(buffer).toString();
            log.info(name + ": from client: {} ", input);

            buffer.flip();
            buffer.clear();
            inputBuffer.append(input);
        }

        String requestFromClient = inputBuffer.toString();
        log.info(name + ": requestFromClient: {} ", requestFromClient);

        byte[] response = (name + ": echo: " + requestFromClient).getBytes();
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
    }

}
