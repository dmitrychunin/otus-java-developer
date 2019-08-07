package ru.otus.javadeveloper.hw12.service;

import ru.otus.javadeveloper.hw12.dao.executor.DbExecutorHibernate;

import java.util.List;
import java.util.Optional;

public class DbHibernateServiceImpl implements DBService {
    private final DbExecutorHibernate dbExecutorHibernate;

    public DbHibernateServiceImpl(DbExecutorHibernate dbExecutorHibernate) {
        this.dbExecutorHibernate = dbExecutorHibernate;
    }

    @Override
    public Object save(Object entity) {
        return dbExecutorHibernate.create(entity);
    }

    @Override
    public Optional<?> get(long id, Class<?> clazz) {
        return Optional.of(dbExecutorHibernate.load(id, clazz));
    }

    @Override
    public Object update(Object entity) {
        return dbExecutorHibernate.update(entity);
    }

    @Override
    public List<?> getAll(Class<?> clazz) {
        return dbExecutorHibernate.loadAll(clazz);
    }
}
