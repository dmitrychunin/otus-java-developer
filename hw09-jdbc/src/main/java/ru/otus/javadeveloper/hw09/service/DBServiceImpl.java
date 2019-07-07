package ru.otus.javadeveloper.hw09.service;

import ru.otus.javadeveloper.hw09.executor.DbExecutor;
import ru.otus.javadeveloper.hw09.executor.DbCustomExecutorImpl;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class DBServiceImpl<T> implements DBService<T> {
    private final DataSource dataSource;
    private final DbExecutor<T> executor;
    private final Class<T> clazz;

    public DBServiceImpl(DataSource dataSource, Class<T> clazz) throws SQLException {
        this.dataSource = dataSource;
        executor = new DbCustomExecutorImpl<>(this.dataSource.getConnection());
        this.clazz = clazz;
    }

    @Override
    public T save(T entity) {
        try (Connection connection = dataSource.getConnection()) {
            DbExecutor<T> executor = new DbCustomExecutorImpl<>(connection);
            T savedEntity = executor.create(entity);
            connection.commit();
            System.out.println("created entity:" + savedEntity);
            return savedEntity;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Optional<T> get(long id) {
        try (Connection connection = dataSource.getConnection()) {
            DbExecutor<T> executor = new DbCustomExecutorImpl<>(connection);
            Optional<T> entity = Optional.ofNullable(executor.load(id, clazz));
            System.out.println("entity:" + entity);
            return entity;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    @Override
    public T update(T entity) {
        try (Connection connection = dataSource.getConnection()) {
            DbExecutor<T> executor = new DbCustomExecutorImpl<>(connection);
            T update = executor.update(entity);
            connection.commit();
            System.out.println("updated entity:" + update);
            return update;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }
}
