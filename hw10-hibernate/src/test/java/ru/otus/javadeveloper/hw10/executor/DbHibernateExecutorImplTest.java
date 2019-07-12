package ru.otus.javadeveloper.hw10.executor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.javadeveloper.hw10.model.AddressDataSet;
import ru.otus.javadeveloper.hw10.model.User;
import ru.otus.javadeveloper.hw10.utils.DefaultBuilder;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DbHibernateExecutorImplTest {
    private final DbExecutor<User> dbExecutor = new DbHibernateExecutorImpl<>();
    private User defaultUser;

    @BeforeEach
    public void setUp() {
        defaultUser = DefaultBuilder.createDefaultTestUser();
    }

    @Test
    public void shouldCreateAndReadCascadeUser() throws SQLException {
        Long id = dbExecutor.create(defaultUser).getId();
        var savedUser = dbExecutor.load(id, User.class);
        assertEquals(defaultUser, savedUser);
    }

    @Test
    public void shouldUpdateAndReadCascadeUser() throws SQLException {
        var existedUser = dbExecutor.create(defaultUser);
        var addressDataSet = new AddressDataSet();
        addressDataSet.setStreet("new street");
        existedUser.setAddressDataSet(addressDataSet);
        Long updatedUserId = dbExecutor.update(existedUser).getId();

        var updatedUser = dbExecutor.load(updatedUserId, User.class);
        assertEquals(addressDataSet, updatedUser.getAddressDataSet());
        assertEquals(existedUser.getId(), updatedUserId);
    }

    @Test
    public void shouldCreateCascadeUserIfNotExist() throws SQLException {
        defaultUser.setName("new created user");

        var createdUser = dbExecutor.createOrUpdate(defaultUser);
        assertEquals(defaultUser, createdUser);
    }

    @Test
    public void shouldUpdateCascadeUserIfExists() throws SQLException {
        var existedUser = dbExecutor.create(defaultUser);
        var addressDataSet = new AddressDataSet();
        addressDataSet.setStreet("new street");
        existedUser.setAddressDataSet(addressDataSet);
        var updatedUser = dbExecutor.createOrUpdate(existedUser);
        assertEquals(existedUser.getId(), updatedUser.getId());
        assertEquals(existedUser, updatedUser);
    }
}
