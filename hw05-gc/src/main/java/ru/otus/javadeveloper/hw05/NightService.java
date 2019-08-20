package ru.otus.javadeveloper.hw05;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.List;

public class NightService {
    private final List<String> database = new ArrayList<>();
    private static int count;


    public void calc() {
        while (true) {
            for (int i = 0; i < (count + 1) * 200; i++) {
                String randomAlphanumeric = RandomStringUtils.randomAlphanumeric(5_000);
                database.add(randomAlphanumeric);
            }
            System.out.println(++count);
            clear();
        }
    }

    private void clear() {
        for (int i = 0; i < database.size()/2; i++) {
            database.remove(i);
        }
    }
}
