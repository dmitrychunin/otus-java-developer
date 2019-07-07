package ru.otus.javadeveloper.hw09.executor;

import java.sql.SQLException;

public interface DbExecutor<T> {
    T create(T objectData) throws SQLException;

    T update(T objectData) throws SQLException;

    T createOrUpdate(T objectData) throws SQLException;

    T load(long id, Class<T> clazz) throws SQLException;
}
