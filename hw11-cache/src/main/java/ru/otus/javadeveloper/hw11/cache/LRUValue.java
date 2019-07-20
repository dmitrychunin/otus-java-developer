package ru.otus.javadeveloper.hw11.cache;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
class LRUValue<K, V> {
    private K key;
    private V value;
    private Long creationTime;
    private Long lastAccessTime;
}