package ru.otus.javadeveloper.hw11.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.javadeveloper.hw11.cache.Cache;
import ru.otus.javadeveloper.hw11.cache.LRUCache;
import ru.otus.javadeveloper.hw11.executor.DbExecutorHibernate;
import ru.otus.javadeveloper.hw11.executor.DbHibernateExecutorHibernateImpl;
import ru.otus.javadeveloper.hw11.model.User;
import ru.otus.javadeveloper.hw11.utils.DefaultBuilder;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DbHibernateCachedServiceImplTest {

    private final Cache<Long, User> userCache = Mockito.spy(new LRUCache<>(3, 0, 0));
    private final DbExecutorHibernate<User> dbExecutorHibernate = Mockito.spy(new DbHibernateExecutorHibernateImpl<>());
    private final DBService<User> dbService = new DbHibernateCachedServiceImpl<>(User.class, dbExecutorHibernate, userCache);

    @Test
    public void shouldGetUserFromDbIfItIsNotCached() {
        User defaultTestUser = DefaultBuilder.createDefaultTestUser();
        Long savedUserId = dbService.save(defaultTestUser).getId();
        dbService.get(savedUserId);
        dbService.save(defaultTestUser.toBuilder().phoneDataSets(null).build());
        dbService.save(defaultTestUser.toBuilder().phoneDataSets(null).build());
        dbService.save(defaultTestUser.toBuilder().phoneDataSets(null).build());
        dbService.get(savedUserId);

        verify(dbExecutorHibernate, times(1)).load(eq(savedUserId), eq(User.class));
    }

    @Test
    public void shouldGetUserFromCache() {
        User defaultTestUser = DefaultBuilder.createDefaultTestUser();
        Long savedUserId = dbService.save(defaultTestUser).getId();
        dbService.get(savedUserId);

        verify(dbExecutorHibernate, never()).load(eq(savedUserId), eq(User.class));
    }
}