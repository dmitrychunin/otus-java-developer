package ru.otus.javadeveloper.hw11.service;

import ru.otus.javadeveloper.hw11.cache.Cache;
import ru.otus.javadeveloper.hw11.executor.DbExecutorHibernate;

import java.util.Optional;

public class DbHibernateCachedServiceImpl<T> implements DBService<T> {
    private final Class<T> clazz;
    private final DbExecutorHibernate<T> dbExecutorHibernate;
    private final Cache<Long, T> cache;

    public DbHibernateCachedServiceImpl(Class<T> clazz, DbExecutorHibernate<T> dbExecutorHibernate, Cache<Long, T> cache) {
        this.clazz = clazz;
        this.dbExecutorHibernate = dbExecutorHibernate;
        this.cache = cache;
    }


    @Override
    public T save(T entity) {
        return dbExecutorHibernate.create(entity);
    }

    @Override
    public Optional<T> get(long id) {
        T entity = cache.get(id);
        if (entity == null) {
            T load = dbExecutorHibernate.load(id, clazz);
            cache.put(id, load);
            return Optional.of(load);
        }
        return Optional.of(entity);
    }

    @Override
    public T update(T entity) {
        return dbExecutorHibernate.update(entity);
    }
}
