package ru.otus.javadeveloper.hw12.service;

import java.util.List;
import java.util.Optional;

public interface DBService<T> {
    T save(T entity);

    Optional<T> get(long id);

    T update(T entity);

    List<T> getAll();
}
