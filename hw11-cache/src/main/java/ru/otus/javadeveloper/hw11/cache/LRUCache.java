package ru.otus.javadeveloper.hw11.cache;

import lombok.val;

import java.lang.ref.ReferenceQueue;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

public class LRUCache<K, V> implements Cache<K, V> {

    public static final long DEFAULT_MAX_VALUE = Long.MAX_VALUE / 2;
    private final long maxSize;
    private final long idleTimeMs;
    private Set<CustomSoftReference<K, V>> linkedHashSet = new LinkedHashSet<>();
    private ReferenceQueue<V> queue = new ReferenceQueue<>();
    private long size = 0;

    public LRUCache() {
        maxSize = DEFAULT_MAX_VALUE;
        idleTimeMs = DEFAULT_MAX_VALUE;
    }

    public LRUCache(int maxSize) {
        idleTimeMs = DEFAULT_MAX_VALUE;
        this.maxSize = maxSize > 0 ? maxSize : DEFAULT_MAX_VALUE;
    }

    public LRUCache(long maxSize, long maxIdleTimeMs) {
        this.maxSize = maxSize > 0 ? maxSize : DEFAULT_MAX_VALUE;
        this.idleTimeMs = maxIdleTimeMs > 0 ? maxIdleTimeMs : DEFAULT_MAX_VALUE;
    }

    @Override
    public V get(Object key) {
        clearAllCacheOnOutOfMemoryAlert();
        removeIfIdleTooLong();
        CustomSoftReference<K, V> lruValue = removeIfExistsWithKey((K) key);
        if (lruValue == null) {
            return null;
        } else {
            putNew(lruValue);
            return lruValue.get();
        }
    }

    @Override
    public void put(K key, V value) {
        clearAllCacheOnOutOfMemoryAlert();
        removeIfIdleTooLong();
        CustomSoftReference<K, V> lruValue = removeIfExistsWithKey(key);
        if (lruValue == null && size == maxSize) {
            removeLeastRecentlyUsed();
        }

        putNew(new CustomSoftReference<>(key, value, queue));
    }

    @Override
    public void remove(Object key) {
        clearAllCacheOnOutOfMemoryAlert();
        removeIfIdleTooLong();
        CustomSoftReference<K, V> element = removeIfExistsWithKey((K) key);
        if (element == null) {
            throw new RuntimeException();
        }
    }

    @Override
    public void removeAll() {
        linkedHashSet.clear();
    }

    private boolean isIdleTooLong(CustomSoftReference<K, V> softLruValue) {
        val now = System.currentTimeMillis();
        val lastAccessTime = softLruValue.getLastAccessTime();
        return (lastAccessTime + idleTimeMs) <= now;
    }

    private void removeIfIdleTooLong() {
        Iterator<CustomSoftReference<K, V>> iterator = linkedHashSet.iterator();
        CustomSoftReference<K, V> next;
        while (iterator.hasNext() && (next = iterator.next()) != null && isIdleTooLong(next)) {
            iterator.remove();
        }
    }

    private void removeLeastRecentlyUsed() {
        Iterator<CustomSoftReference<K, V>> iterator = linkedHashSet.iterator();
        iterator.next();
        iterator.remove();
        size--;
    }

    private void putNew(CustomSoftReference<K, V> lruValue) {
        val now = System.currentTimeMillis();
        lruValue.setLastAccessTime(now);
        size++;
        linkedHashSet.add(lruValue);
    }

    private CustomSoftReference<K, V> removeIfExistsWithKey(K key) {
//        todo use spliterator or parallel stream to improve performance?
        Iterator<CustomSoftReference<K, V>> iterator = linkedHashSet.iterator();
        while (iterator.hasNext()) {
            CustomSoftReference<K, V> next = iterator.next();
            if (next.getKey().equals(key)) {
                iterator.remove();
                size--;
                return next;
            }
        }
        return null;
    }

    private void clearAllCacheOnOutOfMemoryAlert() {
//        todo call two parallel tasks?
        while (queue.poll() != null) {
        }

        Iterator<CustomSoftReference<K, V>> iterator = linkedHashSet.iterator();
        CustomSoftReference<K, V> next;
        while (iterator.hasNext() && (next = iterator.next()) != null) {
            if (next.get() == null) {
                iterator.remove();
            }
        }
    }
}
