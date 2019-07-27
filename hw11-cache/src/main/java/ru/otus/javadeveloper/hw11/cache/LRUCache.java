package ru.otus.javadeveloper.hw11.cache;

import lombok.val;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.HashMap;
import java.util.Map;

public class LRUCache<K, V> implements Cache<K, V> {

    public static final long DEFAULT_MAX_VALUE = Long.MAX_VALUE / 2;
    private final long maxSize;
    private final long lifeTimeMs;
    private final long idleTimeMs;
    private Map<K, CustomSoftReference<K, V>> valueHashMap = new HashMap<>();
    private ReferenceQueue<V> queue = new ReferenceQueue<>();
    private MultiValueTreeMap<Long, K> keyTreeMap = new MultiValueTreeMap<>();
    private long size = 0;

    public LRUCache() {
        maxSize = DEFAULT_MAX_VALUE;
        lifeTimeMs = DEFAULT_MAX_VALUE;
        idleTimeMs = DEFAULT_MAX_VALUE;
    }

    public LRUCache(int maxSize, long lifeTimeMs, long idleTimeMs) {
        this.maxSize = maxSize > 0 ? maxSize : DEFAULT_MAX_VALUE;
        this.lifeTimeMs = lifeTimeMs > 0 ? lifeTimeMs : DEFAULT_MAX_VALUE;
        this.idleTimeMs = idleTimeMs > 0 ? idleTimeMs : DEFAULT_MAX_VALUE;
    }

    public LRUCache(int maxSize) {
        this.maxSize = maxSize > 0 ? maxSize : DEFAULT_MAX_VALUE;
        this.lifeTimeMs = DEFAULT_MAX_VALUE;
        this.idleTimeMs = DEFAULT_MAX_VALUE;
    }

    @Override
    public V get(Object key) {
        clearAllCacheOnOutOfMemoryAlert();
        CustomSoftReference<K, V> softLruValue = valueHashMap.get(key);
        if (softLruValue == null) {
            return null;
        }

        if (isTimeExceed(softLruValue)) {
            val lastAccessTime = softLruValue.getLastAccessTime();
            remove(lastAccessTime, softLruValue.getKey());
            return null;
        }
        return updateExisted(softLruValue, softLruValue.get());
    }

    private boolean isTimeExceed(CustomSoftReference<K, V> softLruValue) {
        val now = System.currentTimeMillis();
        val creationTime = softLruValue.getCreationTime();
        val lastAccessTime = softLruValue.getLastAccessTime();
        return (creationTime + lifeTimeMs) <= now || (lastAccessTime + idleTimeMs) <= now;
    }

    @Override
    public void put(K key, V value) {
        clearAllCacheOnOutOfMemoryAlert();
        CustomSoftReference<K, V> weakLruValue = valueHashMap.get(key);
        if (weakLruValue == null) {
            putNew(key, value);
        } else {
            updateExisted(weakLruValue, value);
        }
    }

    @Override
    public void removeAll() {
        keyTreeMap.clear();
        valueHashMap.clear();
    }

    @Override
    public void remove(Object key) {
        clearAllCacheOnOutOfMemoryAlert();
        CustomSoftReference<K, V> element = valueHashMap.get((K) key);
        if (element == null) {
            throw new RuntimeException();
        }
        remove(element.getLastAccessTime(), (K) key);
    }

    private void remove(Long lastAccessTime, K key) {
        size--;
        keyTreeMap.remove(lastAccessTime, key);
        valueHashMap.remove(key);
    }

    private void putNew(K key, V value) {
        if (size == maxSize) {
            removeLeastRecentlyUsed();
        }
        size++;
        val now = System.currentTimeMillis();
        keyTreeMap.put(now, key);
        CustomSoftReference<K, V> softResult = new CustomSoftReference<>(key, value, now, now, queue);
        valueHashMap.put(key, softResult);
    }

    private void clearAllCacheOnOutOfMemoryAlert() {
        Reference<? extends V> poll;
        while ((poll = queue.poll()) != null) {
            CustomSoftReference<K, V> softRefOnGarbageCollectedObject = (CustomSoftReference<K, V>) poll;
            remove(softRefOnGarbageCollectedObject.getLastAccessTime(), softRefOnGarbageCollectedObject.getKey());
        }
    }

    private V updateExisted(CustomSoftReference<K, V> lruValue, V newValue) {
        keyTreeMap.remove(lruValue.getLastAccessTime(), lruValue.getKey());
        val key = lruValue.getKey();
        val now = System.currentTimeMillis();
        keyTreeMap.put(now, key);
        CustomSoftReference<K, V> weakResult = new CustomSoftReference<>(key, newValue, lruValue.getCreationTime(), now, queue);
        valueHashMap.put(key, weakResult);
        return weakResult.get();
    }

    private void removeLeastRecentlyUsed() {
        MultiValueTreeMap.Pair<Long, K> leastRecentlyUsed = keyTreeMap.firstEntry();
        val lastAccessTime = leastRecentlyUsed.getKey();
        val key = leastRecentlyUsed.getValue();

        size--;
        keyTreeMap.remove(lastAccessTime, key);
        valueHashMap.remove(key);
    }
}
