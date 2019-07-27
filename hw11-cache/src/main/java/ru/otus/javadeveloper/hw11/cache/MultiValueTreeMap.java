package ru.otus.javadeveloper.hw11.cache;

import lombok.Value;

import java.util.*;

public class MultiValueTreeMap<K, V> implements Map<K, V> {
    private TreeMap<K, Set<V>> keyTreeMap = new TreeMap<>();

    @Override
    public int size() {
        return keyTreeMap.size();
    }

    public Pair<K, V> firstEntry() {
        Map.Entry<K, Set<V>> entry = keyTreeMap.firstEntry();
        Iterator<V> iterator = entry.getValue().iterator();
        if (iterator.hasNext()) {
            V next = iterator.next();
            return new Pair<>(entry.getKey(), next);
        }
        throw new RuntimeException("empty map has not first entry");
    }

    @Override
    public boolean isEmpty() {
        return keyTreeMap.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return keyTreeMap.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public V get(Object key) {
        return null;
    }

    @Override
    public V put(K key, V value) {
        Set<V> vs = keyTreeMap.get(key);
        if (vs == null) {
            vs = new HashSet<>();
        }
        vs.add(value);
        keyTreeMap.put(key, vs);
        return value;
    }

    @Override
    public V remove(Object key) {
        return null;
    }

    @Override
    public boolean remove(Object key, Object value) {
        Set<V> vs = keyTreeMap.get(key);
        boolean remove = vs.remove(value);
        if (vs.isEmpty()) {
            keyTreeMap.remove(key);
        }
        return remove;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {

    }

    @Override
    public void clear() {
        keyTreeMap.clear();
    }

    @Override
    public Set<K> keySet() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return null;
    }

    @Value
    public static class Pair<K, V> {
        private final K key;
        private final V value;
    }
}
