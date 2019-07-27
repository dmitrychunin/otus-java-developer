package ru.otus.javadeveloper.hw11.cache;

import lombok.Data;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;

@Data
public class CustomSoftReference<K, V> extends SoftReference<V> {
    private K key;
    private Long creationTime;
    private Long lastAccessTime;

    public CustomSoftReference(K key, V value, Long creationTime, Long lastAccessTime, ReferenceQueue<? super V> queue) {
        super(value, queue);
        this.key = key;
        this.creationTime = creationTime;
        this.lastAccessTime = lastAccessTime;
    }
}
