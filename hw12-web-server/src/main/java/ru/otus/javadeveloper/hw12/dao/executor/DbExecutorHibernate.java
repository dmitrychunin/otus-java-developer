package ru.otus.javadeveloper.hw12.dao.executor;

import java.util.List;

public interface DbExecutorHibernate {
    Object create(Object objectData);

    Object update(Object objectData);

    Object createOrUpdate(Object objectData);

    Object load(long id, Class<?> clazz);


    List<?> loadAll(Class<?> clazz);
}
