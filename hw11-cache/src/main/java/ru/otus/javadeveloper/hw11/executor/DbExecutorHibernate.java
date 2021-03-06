package ru.otus.javadeveloper.hw11.executor;

public interface DbExecutorHibernate<T> {
    T create(T objectData);

    T update(T objectData);

    T createOrUpdate(T objectData);

    T load(long id, Class<T> clazz);
}
