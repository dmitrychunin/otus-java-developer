package ru.otus.javadeveloper.hw10.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.javadeveloper.hw10.model.AddressDataSet;
import ru.otus.javadeveloper.hw10.model.User;
import ru.otus.javadeveloper.hw10.utils.DefaultBuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DBHibernateServiceImplTest {
    private static DBService<User> dbService;
    private User defaultUser;

    @BeforeEach
    public void setUp() {
        defaultUser = DefaultBuilder.createDefaultTestUser();
        dbService = new DbHibernateServiceImpl<>(User.class);
    }

    @Test
    public void saveCascadeUser() {
        var savedUser = dbService.save(defaultUser);
        assertEquals(defaultUser, savedUser);
    }

    @Test
    public void updateCascadeUser() {
        var savedUser = dbService.save(defaultUser);

        var updatedUser = dbService.update(savedUser);
        var addressDataSet = new AddressDataSet();
        addressDataSet.setStreet("new street");
        updatedUser.setAddressDataSet(addressDataSet);

        assertEquals(savedUser.getId(), updatedUser.getId());
        assertEquals(savedUser, updatedUser);
    }
}