package ru.otus.javadeveloper.nio.server;

import ru.otus.javadeveloper.nio.server.el.BossEventLoop;
import ru.otus.javadeveloper.nio.server.el.EventLoop;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NettyMain {
    private static final int PORT = 8080;

    public static void main(String[] args) {
        ExecutorService bossExecutor = Executors.newSingleThreadExecutor();
//        ExecutorService workerExecutor = Executors.newFixedThreadPool(2);
//        ExecutorService workerExecutor = Executors.newFixedThreadPool(2 * Runtime.getRuntime().availableProcessors());


        EventLoop bossEventLoop = new BossEventLoop(PORT, 2);
        bossExecutor.submit(bossEventLoop::go);
    }
}
