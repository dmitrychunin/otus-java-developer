package ru.otus.javadeveloper.hw10.service;

import ru.otus.javadeveloper.hw10.executor.DbExecutor;
import ru.otus.javadeveloper.hw10.executor.DbHibernateExecutorImpl;

import java.sql.SQLException;
import java.util.Optional;

public class DbHibernateServiceImpl<T> implements DBService<T> {
    private final Class<T> clazz;

    public DbHibernateServiceImpl(Class<T> clazz) {
        this.clazz = clazz;
    }


    @Override
    public T save(T entity) {
        DbExecutor<T> dbExecutor = new DbHibernateExecutorImpl<>();
        try {
            return dbExecutor.create(entity);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<T> get(long id) {

        DbExecutor<T> dbExecutor = new DbHibernateExecutorImpl<>();
        try {
            return Optional.of(dbExecutor.load(id, clazz));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public T update(T entity) {

        DbExecutor<T> dbExecutor = new DbHibernateExecutorImpl<>();
        try {
            return dbExecutor.update(entity);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
