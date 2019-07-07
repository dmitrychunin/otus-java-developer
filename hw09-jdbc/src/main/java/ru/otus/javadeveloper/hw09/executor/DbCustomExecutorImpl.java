package ru.otus.javadeveloper.hw09.executor;

import ru.otus.javadeveloper.hw09.executor.builder.QueryBuilder;
import ru.otus.javadeveloper.hw09.executor.builder.QueryBuilderImpl;
import ru.otus.javadeveloper.hw09.executor.scanner.ClassScannerResult;
import ru.otus.javadeveloper.hw09.executor.scanner.EntityScanner;
import ru.otus.javadeveloper.hw09.executor.scanner.EntityScannerImpl;
import ru.otus.javadeveloper.hw09.executor.scanner.ObjectScannerResult;
import ru.otus.javadeveloper.hw09.executor.utils.ReflectionUtils;

import java.sql.*;
import java.util.Objects;

public class DbCustomExecutorImpl<T> implements DbExecutor<T> {

    private final Connection connection;
    private final EntityScanner entityScanner = new EntityScannerImpl();
    private final QueryBuilder queryBuilder = new QueryBuilderImpl();
    private final ReflectionUtils<T> reflectionUtils = new ReflectionUtils<>();

    public DbCustomExecutorImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public T create(T objectData) throws SQLException {
        Savepoint savePoint = this.connection.setSavepoint("savePointName");
        ObjectScannerResult objectScannerResult = entityScanner.scanObject(objectData);
        final String insert = queryBuilder.buildInsert(objectScannerResult);
        try (PreparedStatement pst = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {
            pst.executeUpdate();
            try (ResultSet rs = pst.getGeneratedKeys()) {
                rs.next();
                int anInt = rs.getInt(1);
                return load(anInt, (Class<T>) objectData.getClass());
            }
        } catch (SQLException ex) {
            this.connection.rollback(savePoint);
            System.out.println(ex.getMessage());
            throw ex;
        }
    }

    @Override
    public T update(T objectData) throws SQLException {
        Class<T> aClass = (Class<T>) objectData.getClass();
        ObjectScannerResult objectScannerResult1 = entityScanner.scanObject(objectData);
        Long id = objectScannerResult1.getIdValue();
        T load = load(id, aClass);
        if (Objects.isNull(load)) {
            throw new RuntimeException("Entity is not found");
        }
        Savepoint savePoint = this.connection.setSavepoint("savePointName");
        ObjectScannerResult objectScannerResult = entityScanner.scanObject(objectData);
        final String update = queryBuilder.buildUpdateById(objectScannerResult);
        try (PreparedStatement pst = this.connection.prepareStatement(update)) {
            pst.setLong(1, id);
            pst.executeUpdate();
            return load(id, (Class<T>) objectData.getClass());

        } catch (SQLException ex) {
            this.connection.rollback(savePoint);
            System.out.println(ex.getMessage());
            throw ex;
        }
    }

    @Override
    public T createOrUpdate(T objectData) throws SQLException {
        ObjectScannerResult objectScannerResult1 = entityScanner.scanObject(objectData);
        Long id = objectScannerResult1.getIdValue();
        T load = load(id, (Class<T>) objectData.getClass());
        if (Objects.isNull(load)) {
            return create(objectData);
        } else {
            return update(objectData);
        }
    }

    @Override
    public T load(long id, Class<T> clazz) throws SQLException {
        ClassScannerResult classScannerResult = entityScanner.scanClass(clazz);
        final String select = queryBuilder.buildSelectById(classScannerResult);
        try (PreparedStatement pst = this.connection.prepareStatement(select)) {
            pst.setLong(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    Object[] rowColumnValues = extractRowColumnValues(rs);
                    return reflectionUtils.createObject(rowColumnValues, clazz);
                }
                return null;
            }
        }
    }

    private Object[] extractRowColumnValues(ResultSet rs) throws SQLException {
        int columnCount = rs.getMetaData().getColumnCount();
        Object[] rowColumnValues = new Object[columnCount];
        for (int i = 1; i < columnCount + 1; i++) {
            Object object = rs.getObject(i);
            rowColumnValues[i - 1] = object;
        }
        return rowColumnValues;
    }
}
