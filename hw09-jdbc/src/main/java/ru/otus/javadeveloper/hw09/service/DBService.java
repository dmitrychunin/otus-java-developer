package ru.otus.javadeveloper.hw09.service;

import java.util.Optional;

public interface DBService<T> {
    T save(T entity);

    Optional<T> get(long id);

    T update(T entity);
}
