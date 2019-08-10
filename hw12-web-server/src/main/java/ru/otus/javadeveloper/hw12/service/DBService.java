package ru.otus.javadeveloper.hw12.service;

import java.util.List;
import java.util.Optional;

public interface DBService {
    Object save(Object entity);

    Optional<?> get(long id, Class<?> clazz);

    Object update(Object entity);

    List<?> getAll(Class<?> clazz);
}
