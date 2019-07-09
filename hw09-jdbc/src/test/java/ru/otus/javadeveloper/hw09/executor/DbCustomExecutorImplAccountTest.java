package ru.otus.javadeveloper.hw09.executor;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.otus.javadeveloper.hw09.model.Account;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class DbCustomExecutorImplAccountTest {
    private static final String URL = "jdbc:h2:mem:";
    private static Connection connection;
    private static DbExecutor<Account> accountDbExecutor;
    private final static Account originalAccount = new Account("some", new BigDecimal(2));
    private static final long DOES_NOT_EXISTED_ACCOUNT_ID = 123L;

    @BeforeAll
    public static void init() throws SQLException {
        connection = getConnection();
        accountDbExecutor = new DbCustomExecutorImpl<>(connection);
        createAccountTable(connection);
    }

    private static Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(URL);
        connection.setAutoCommit(false);
        return connection;
    }

    private static void createAccountTable(Connection connection) throws SQLException {
        try (PreparedStatement pst = connection.prepareStatement("create table account(no bigint(20) NOT NULL auto_increment, type varchar(255), rest number)")) {
            pst.executeUpdate();
        }
    }

    @Test
    public void shouldCreateAccount() throws SQLException {
        Account savedAccountInDb = accountDbExecutor.create(originalAccount);

        assertEquals(originalAccount, savedAccountInDb);
    }

    @Test
    public void shouldUpdateExistedAccount() throws SQLException {
        Account existedAccount = accountDbExecutor.create(originalAccount);

        existedAccount.setType("John2");
        Account updatedAccount = accountDbExecutor.update(existedAccount);

        assertEquals(existedAccount, updatedAccount);
    }

    @Test
    public void shouldThrowExceptionWhenAccountIsNotExisted() {
        originalAccount.setNo(DOES_NOT_EXISTED_ACCOUNT_ID);
        assertThrows(RuntimeException.class, () -> accountDbExecutor.update(originalAccount));
    }

    @Test
    public void shouldCreateWhenAccountIsNotExist() throws SQLException {
        Account load = accountDbExecutor.load(DOES_NOT_EXISTED_ACCOUNT_ID, Account.class);
        assertNull(load);
        originalAccount.setNo(DOES_NOT_EXISTED_ACCOUNT_ID);
        Account createdAccount = accountDbExecutor.createOrUpdate(originalAccount);

        assertNotEquals(DOES_NOT_EXISTED_ACCOUNT_ID, createdAccount.getNo());
        assertEquals(originalAccount, createdAccount);
    }

    @Test
    public void shouldUpdateWhenAccountExisted() throws SQLException {
        Account existedAccount = accountDbExecutor.create(originalAccount);
        existedAccount.setType("Sam");
        existedAccount.setRest(new BigDecimal(25));
        Account updatedAccount = accountDbExecutor.createOrUpdate(existedAccount);

        assertEquals(existedAccount.getNo(), updatedAccount.getNo());
        assertEquals(existedAccount, updatedAccount);
    }
}
