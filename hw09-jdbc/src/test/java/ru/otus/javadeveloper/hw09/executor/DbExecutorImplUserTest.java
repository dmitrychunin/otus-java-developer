package ru.otus.javadeveloper.hw09.executor;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.otus.javadeveloper.hw09.model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class DbExecutorImplUserTest {
    private static final String URL = "jdbc:h2:mem:";
    private final static User originalUser = new User("John", 20);
    private static final long DOES_NOT_EXISTED_USER_ID = 123L;
    private static Connection connection;
    private static DbExecutor<User> userDbExecutor;

    @BeforeAll
    public static void init() throws SQLException {
        connection = getConnection();
        userDbExecutor = new DbExecutorImpl<>(connection);
        createUserTable(connection);
    }

    private static Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(URL);
        connection.setAutoCommit(false);
        return connection;
    }

    private static void createUserTable(Connection connection) throws SQLException {
        try (PreparedStatement pst = connection.prepareStatement("create table user(id bigint(20) NOT NULL auto_increment, name varchar(255), age int(3))")) {
            pst.executeUpdate();
        }
    }

    @Test
    public void shouldCreateUser() throws SQLException {
        User savedUserInDb = userDbExecutor.create(originalUser);

        assertEquals(originalUser, savedUserInDb);
    }

    @Test
    public void shouldUpdateExistedUser() throws SQLException {
        User existedUser = userDbExecutor.create(originalUser);

        existedUser.setName("John2");
        User updatedUser = userDbExecutor.update(existedUser);

        assertEquals(existedUser, updatedUser);
    }

    @Test
    public void shouldThrowExceptionWhenUserIsNotExisted() {
        originalUser.setId(DOES_NOT_EXISTED_USER_ID);
        assertThrows(RuntimeException.class, () -> userDbExecutor.update(originalUser));
    }

    @Test
    public void shouldCreateWhenUserIsNotExist() throws SQLException {
        User load = userDbExecutor.load(DOES_NOT_EXISTED_USER_ID, User.class);
        assertNull(load);
        originalUser.setId(DOES_NOT_EXISTED_USER_ID);
        User createdUser = userDbExecutor.createOrUpdate(originalUser);

        assertNotEquals(DOES_NOT_EXISTED_USER_ID, createdUser.getId());
        assertEquals(originalUser, createdUser);
    }

    @Test
    public void shouldUpdateWhenUserExisted() throws SQLException {
        User existedUser = userDbExecutor.create(originalUser);
        existedUser.setName("Sam");
        existedUser.setAge(25);
        User updatedUser = userDbExecutor.createOrUpdate(existedUser);

        assertEquals(existedUser.getId(), updatedUser.getId());
        assertEquals(existedUser, updatedUser);
    }
}
