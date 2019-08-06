package ru.otus.javadeveloper.hw11.cache;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.javadeveloper.hw11.model.User;
import ru.otus.javadeveloper.hw11.utils.DefaultBuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static ru.otus.javadeveloper.hw11.utils.DefaultBuilder.createDefaultTestUser;

public class LRUCacheTest {
    //1) Thread.sleep добавлены для того, чтобы всегда был явно определен последний запрошенный,
    //если разница меньше миллисекнуды -> из кеша удалится первый попавшийся с min(lastAccessTime)
    //2) запускать тесты с параметром -Xmx10m

    private final User defaultTestUser = createDefaultTestUser();
    private Cache<Long, User> limitedEternalCache;
    private Cache<Long, User> autoCleanedCache;
    private Cache<Long, User> unlimitedCache;

    @BeforeEach
    public void init() {
        limitedEternalCache = new LRUCache<>(2);
        autoCleanedCache = new LRUCache<>(1, 150);
        unlimitedCache = new LRUCache<>();
    }

    @Test
    public void shouldNotExceedMaxCacheSize() throws InterruptedException {
        limitedEternalCache.put(1L, defaultTestUser);
        Thread.sleep(1);
        limitedEternalCache.put(2L, defaultTestUser);
        limitedEternalCache.put(3L, defaultTestUser);

        assertNull(limitedEternalCache.get(1L));
        assertEquals(defaultTestUser, limitedEternalCache.get(2L));
        assertEquals(defaultTestUser, limitedEternalCache.get(3L));
    }

    @Test
    public void shouldUpdateValueAndLastAccessTimeIfKeyAlreadyExists() throws InterruptedException {

        User updatedUser = defaultTestUser.toBuilder().build();
        updatedUser.setAge(35);
        limitedEternalCache.put(1L, defaultTestUser);
        Thread.sleep(1);
        limitedEternalCache.put(2L, defaultTestUser);
        Thread.sleep(1);
        limitedEternalCache.put(1L, updatedUser);
        limitedEternalCache.put(3L, defaultTestUser);

        assertEquals(updatedUser, limitedEternalCache.get(1L));
        assertNull(limitedEternalCache.get(2L));
        assertEquals(defaultTestUser, limitedEternalCache.get(3L));
    }

    @Test
    public void shouldNotUpdateValueAndUpdateLastAccessTimeIfKeyExists() throws InterruptedException {

        limitedEternalCache.put(1L, defaultTestUser);
        limitedEternalCache.put(2L, defaultTestUser);
        limitedEternalCache.get(1L);
        Thread.sleep(1);
        limitedEternalCache.get(1L);
        Thread.sleep(1);
        limitedEternalCache.get(2L);
        limitedEternalCache.put(3L, defaultTestUser);

        assertNull(limitedEternalCache.get(1L));
        assertEquals(defaultTestUser, limitedEternalCache.get(2L));
        assertEquals(defaultTestUser, limitedEternalCache.get(3L));
    }

    @Test
    public void shouldRemoveSmallAccessed() throws InterruptedException {
        autoCleanedCache.put(1L, defaultTestUser);
        Thread.sleep(200);
        assertNull(autoCleanedCache.get(1L));
    }

    @Test
    public void shouldNotThrowOutOfMemory() {
        for (long i = 0; i < 300_000; i++) {
            User defaultTestUser = DefaultBuilder.createDefaultTestUser();
            unlimitedCache.put(i, defaultTestUser);
            defaultTestUser = null;
        }
    }
}