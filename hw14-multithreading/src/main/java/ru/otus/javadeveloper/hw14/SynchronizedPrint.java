package ru.otus.javadeveloper.hw14;

import static java.lang.String.format;

public class SynchronizedPrint implements Runnable {

    private final Object monitor = new Object();

    @Override
    public void run() {
        try {
            int i = 1;
            while (true) {
                incrementAndDecrement(i);
                Thread.sleep(1_000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void incrementAndDecrement(int i) throws InterruptedException {
        for (int j = 1; j < 10; j++) {
            synchronized (monitor) {
                monitor.notify();
                System.out.print(format("%s(%s) ", i++, Thread.currentThread().getName()));
                monitor.wait();
            }
        }
        for (int k = 1; k < 10; k++) {
            synchronized (monitor) {
                monitor.notify();
                System.out.print(format("%s(%s) ", i--, Thread.currentThread().getName()));
                monitor.wait();
            }
        }
    }
}
