package ru.otus.javadeveloper.hw11.cache;

/**
 * @author sergey
 * created on 14.12.18.
 */
public interface Cache<K, V> {

    void put(K key, V value);

    void remove(K key);

    void removeAll();

    V get(K key);
}
