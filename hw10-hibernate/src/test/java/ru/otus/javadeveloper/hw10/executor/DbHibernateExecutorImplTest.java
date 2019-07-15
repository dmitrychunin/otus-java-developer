package ru.otus.javadeveloper.hw10.executor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.javadeveloper.hw10.model.AddressDataSet;
import ru.otus.javadeveloper.hw10.model.PhoneDataSet;
import ru.otus.javadeveloper.hw10.model.User;
import ru.otus.javadeveloper.hw10.utils.DefaultBuilder;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DbHibernateExecutorImplTest {
    private final DbExecutorHibernate<User> dbExecutorHibernate = new DbHibernateExecutorHibernateImpl<>();
    private User defaultUser;

    @BeforeEach
    public void setUp() {
        defaultUser = DefaultBuilder.createDefaultTestUser();
    }

    @Test
    public void shouldCreateAndReadCascadeUser() {
        Long id = dbExecutorHibernate.create(defaultUser).getId();
        var savedUser = dbExecutorHibernate.load(id, User.class);
        assertEquals(defaultUser, savedUser);
    }

    @Test
    public void shouldUpdateAndReadCascadeUser() {
        var existedUser = dbExecutorHibernate.create(defaultUser);
        var addressDataSet = new AddressDataSet();
        addressDataSet.setStreet("new street");
        existedUser.setAddressDataSet(addressDataSet);
        Long updatedUserId = dbExecutorHibernate.update(existedUser).getId();

        var updatedUser = dbExecutorHibernate.load(updatedUserId, User.class);
        assertEquals(addressDataSet, updatedUser.getAddressDataSet());
        assertEquals(existedUser.getId(), updatedUserId);
    }

    @Test
    public void shouldCreateCascadeUserIfNotExist() {
        defaultUser.setName("new created user");

        var createdUser = dbExecutorHibernate.createOrUpdate(defaultUser);
        assertEquals(defaultUser, createdUser);
    }

    @Test
    public void shouldUpdateCascadeUserIfExists() {
        var existedUser = dbExecutorHibernate.create(defaultUser);
        var addressDataSet = new AddressDataSet();
        addressDataSet.setStreet("new street");
        existedUser.setAddressDataSet(addressDataSet);
        var updatedUser = dbExecutorHibernate.createOrUpdate(existedUser);
        assertEquals(existedUser.getId(), updatedUser.getId());
        assertEquals(existedUser, updatedUser);
    }

    @Test
    public void shouldCreateCascadeUser() {
        var addressDataSet = new AddressDataSet();
        addressDataSet.setStreet("new street");

        PhoneDataSet phone = new PhoneDataSet();
        phone.setUser(defaultUser);

        defaultUser.setAddressDataSet(addressDataSet);
        defaultUser.setPhoneDataSets(new HashSet<>(Collections.singletonList(phone)));

        dbExecutorHibernate.create(defaultUser);
    }
}
