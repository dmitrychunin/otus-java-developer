package ru.otus.javadeveloper.hw01maven;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class HelloOtus {
    public static void main(String[] args) {
        Multimap<String, String> courseToStudentName = ArrayListMultimap.create();

        courseToStudentName.put("Java developer", "Ivan");
        courseToStudentName.put("Data science", "Petr");
        courseToStudentName.put("Java developer", "Jack");
        courseToStudentName.put("Python developer", "Michel");
        courseToStudentName.put("Java developer", "Harry");
        courseToStudentName.put("Data science", "Bogdan");

        System.out.println(courseToStudentName);
    }
}
