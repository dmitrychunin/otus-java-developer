package ru.otus.javadeveloper.hw11.cache;

import lombok.val;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class LRUCache<K, V> implements Cache<K, V> {

    public static final long DEFAULT_MAX_VALUE = Long.MAX_VALUE / 2;
    private final long maxSize;
    private final long lifeTimeMs;
    private final long idleTimeMs;
    private Map<LRUKey<K>, WeakReference<LRUValue<K, V>>> valueHashMap = new HashMap<>();
    private TreeMap<SoftReference<Long>, SoftReference<K>> keyTreeMap = new TreeMap<>(Comparator.comparingLong(SoftReference::get));
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
        val softKey = new SoftReference<>((K) key);
        WeakReference<LRUValue<K, V>> weakLruValue = valueHashMap.get(new LRUKey<>(softKey));
        if (weakLruValue == null) {
            return null;
        }

        if (isTimeExceed(weakLruValue)) {
            val lastAccessTime = weakLruValue.get().getLastAccessTime();
            remove(new SoftReference<>(lastAccessTime), new SoftReference<>(weakLruValue.get().getKey()));
            return null;
        }
        return updateExisted(weakLruValue, weakLruValue.get().getValue()).getValue();
    }

    public boolean isTimeExceed(WeakReference<LRUValue<K, V>> weakLruValue) {
        val now = System.currentTimeMillis();
        val creationTime = weakLruValue.get().getCreationTime();
        val lastAccessTime = weakLruValue.get().getLastAccessTime();
        return (creationTime + lifeTimeMs) <= now || (lastAccessTime + idleTimeMs) <= now;
    }

    @Override
    public void put(K key, V value) {
        val softKey = new SoftReference<>(key);
        WeakReference<LRUValue<K, V>> weakLruValue = valueHashMap.get(new LRUKey<>(softKey));
        if (weakLruValue == null) {
            putNew(softKey, value);
        } else {
            updateExisted(weakLruValue, value);
        }
    }

    @Override
    public void removeAll() {
        keyTreeMap = new TreeMap<>();
        valueHashMap = new HashMap<>();
    }

    @Override
    public void remove(Object key) {
        WeakReference<LRUValue<K, V>> element = valueHashMap.get(new LRUKey<>(new SoftReference<>((K) key)));
        if (element == null) {
            throw new RuntimeException();
        }
        remove(new SoftReference<>(element.get().getLastAccessTime()), new SoftReference<>((K) key));
    }

    private void remove(SoftReference<Long> lastAccessTime, SoftReference<K> key) {
        size--;
        keyTreeMap.remove(lastAccessTime, key);
        valueHashMap.remove(new LRUKey<>(key));
    }

    private void putNew(SoftReference<K> key, V value) {
        if (size == maxSize) {
            removeLeastRecentlyUsed();
        }
        size++;
        val now = System.currentTimeMillis();
        val softNow = new SoftReference<>(now);
        keyTreeMap.put(softNow, key);
        WeakReference<LRUValue<K, V>> weakResult = new WeakReference<>(new LRUValue<>(key.get(), value, now, now));
        valueHashMap.put(new LRUKey<>(key), weakResult);
    }

    private LRUValue<K, V> updateExisted(WeakReference<LRUValue<K, V>> lruValue, V value) {
        keyTreeMap.remove(new SoftReference<>(lruValue.get().getLastAccessTime()));
        val key = lruValue.get().getKey();
        val softKey = new SoftReference<>(key);
        val now = new SoftReference<>(System.currentTimeMillis());
        keyTreeMap.put(now, softKey);
        WeakReference<LRUValue<K, V>> weakResult = new WeakReference<>(new LRUValue<K, V>(key, value, lruValue.get().getCreationTime(), now.get()));
        valueHashMap.put(new LRUKey<>(softKey), weakResult);
        return weakResult.get();
    }

    private void removeLeastRecentlyUsed() {
        Map.Entry<SoftReference<Long>, SoftReference<K>> leastRecentlyUsed = keyTreeMap.firstEntry();
        val lastAccessTime = leastRecentlyUsed.getKey();
        val key = leastRecentlyUsed.getValue();

        size--;
        keyTreeMap.remove(lastAccessTime, key);
        valueHashMap.remove(new LRUKey<>(key));
    }
}
