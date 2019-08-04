package ru.otus.javadeveloper.hw11.service;

import ru.otus.javadeveloper.hw11.cache.Cache;
import ru.otus.javadeveloper.hw11.executor.DbExecutorHibernate;

import javax.persistence.Id;
import java.lang.reflect.Field;
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
        T t = dbExecutorHibernate.create(entity);
        cache.put(getFirstIdAnnotatedField(entity), t);
        return t;
    }

//    todo move to utils?
    private Long getFirstIdAnnotatedField(T entity) {
        Class<?> aClass = entity.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            if (declaredField.isAnnotationPresent(Id.class)) {
                return getLongFieldValue(entity, declaredField);
            }
        }
        throw new RuntimeException();
    }

    private Long getLongFieldValue(T entity, Field declaredField) {
        declaredField.setAccessible(true);
        Long aLong = null;
        try {
            aLong = (Long) declaredField.get(entity);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        declaredField.setAccessible(false);
        return aLong;
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
        T update = dbExecutorHibernate.update(entity);
        cache.put(getFirstIdAnnotatedField(update), update);
        return update;
    }
}
