package ru.otus.javadeveloper.hw11.cache;


import lombok.RequiredArgsConstructor;

import java.lang.ref.SoftReference;

@RequiredArgsConstructor
class LRUKey<K> {
    private final SoftReference<K> key;

    @Override
    public int hashCode() {
        return key.get().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return key.get().equals(((LRUKey<K>) obj).key.get());
    }
}
