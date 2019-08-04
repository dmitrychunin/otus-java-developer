package ru.otus.javadeveloper.hw11.cache;

import lombok.Getter;
import lombok.Setter;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;

@Getter
@Setter
public class CustomSoftReference<K, V> extends SoftReference<V> {
    private K key;
    private Long lastAccessTime;

    public CustomSoftReference(K key, V value, ReferenceQueue<? super V> queue) {
        super(value, queue);
        this.key = key;
    }

    @Override
    public boolean equals(Object obj) {
        return key.equals(((CustomSoftReference<K, V>) obj).getKey());
    }
}
