package ru.otus.javadeveloper.hw02;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        List<Integer> list0 = new DIYArrayList<>(0);
        IntStream.range(0, 5).forEachOrdered(list0::add);
        System.out.println(list0);
        List<Integer> list1 = new DIYArrayList<>(10);
        IntStream.range(0, 5).forEachOrdered(list1::add);
        System.out.println(list1);

        List<Integer> list = new DIYArrayList<>(1, 2, 3, 4, 5);
        ListIterator<Integer> stringListIterator = list.listIterator();
        System.out.println(stringListIterator.next());
        System.out.println(stringListIterator.next());

        List<Integer> copiedList = new DIYArrayList<>(IntStream.range(0, 20).boxed().toArray());
        System.out.println(copiedList);
        Collections.copy(copiedList, new DIYArrayList<>(1, 2, 3, 4, null, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20));
        System.out.println(copiedList);

        List<Integer> originList = new DIYArrayList<>(1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, null, 25, 27, 29, 31, 33, 35, 37, 39);
        System.out.println(originList);
        Collections.addAll(originList, 2, 4, 6, 8, 10, 12, 14, 16, null, 20, 22, 24, 26, 28, 30, 32, 34, 36, 38, 40);
        System.out.println(originList);
        Comparator<Integer> integerComparator = Comparator.nullsFirst(Comparator.comparingInt((Integer integer) -> integer));
        Collections.sort(originList, integerComparator);
        System.out.println(originList);

        ListIterator<Integer> integerListIterator = originList.listIterator(3);
        integerListIterator.next();
        integerListIterator.add(77);
        System.out.println(originList);
    }
}
