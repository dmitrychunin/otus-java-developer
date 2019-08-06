package ru.otus.javadeveloper.hw14;

public class MainMultithreading {
    public static void main(String[] args) {
        SynchronizedPrint synchronizedPrint = new SynchronizedPrint();
        Thread t1 = new Thread(synchronizedPrint);
        Thread t2 = new Thread(synchronizedPrint);

        t1.start();
        t2.start();
    }
}
