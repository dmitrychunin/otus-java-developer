package ru.otus.javadeveloper.hw09.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.otus.javadeveloper.hw09.model.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class DBServiceImplUserTest {
    private final static User originalUser = new User("John", 20);
    private final static DataSource dataSource = new DataSourceH2();
    private static DBService<User> dbService;

    @BeforeAll
    public static void setUp() throws SQLException {
        dbService = new DBServiceImpl<>(dataSource, User.class);
        createUserTable(dataSource);
    }

    private static void createUserTable(DataSource dataSource) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pst = connection.prepareStatement("create table user(id bigint(20) NOT NULL auto_increment, name varchar(255), age int(3))")) {
            pst.executeUpdate();
        }
        System.out.println("table created");
    }

    @Test
    public void saveUser() {
        User savedUser = dbService.save(originalUser);
        assertEquals(originalUser, savedUser);
    }

    @Test
    public void updateUser() {
        User savedUser = dbService.save(originalUser);

        savedUser.setName("Sam");
        savedUser.setAge(25);
        User updatedUser = dbService.update(savedUser);

        assertEquals(savedUser.getId(), updatedUser.getId());
        assertEquals(savedUser, updatedUser);
    }
}