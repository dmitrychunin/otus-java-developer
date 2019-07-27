package ru.otus.javadeveloper.hw12.service;

import ru.otus.javadeveloper.hw12.dao.executor.DbExecutorHibernate;

import java.util.List;
import java.util.Optional;

public class DbHibernateServiceImpl<T> implements DBService<T> {
    private final Class<T> clazz;
    DbExecutorHibernate<T> dbExecutorHibernate;

    public DbHibernateServiceImpl(Class<T> clazz, DbExecutorHibernate<T> dbExecutorHibernate) {
        this.clazz = clazz;
        this.dbExecutorHibernate = dbExecutorHibernate;
    }

    @Override
    public T save(T entity) {
        return dbExecutorHibernate.create(entity);
    }

    @Override
    public Optional<T> get(long id) {
        return Optional.of(dbExecutorHibernate.load(id, clazz));
    }

    @Override
    public T update(T entity) {
        return dbExecutorHibernate.update(entity);
    }

    @Override
    public List<T> getAll() {
        return dbExecutorHibernate.loadAll(clazz);
    }
}
