package ru.otus.javadeveloper.hw09.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.otus.javadeveloper.hw09.model.Account;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class DBServiceImplAccountTest {
    private final static Account originalAccount = new Account("some", new BigDecimal(2));
    private final static DataSource dataSource = new DataSourceH2();
    private static DBService<Account> dbService;

    @BeforeAll
    public static void setUp() throws SQLException {
        dbService = new DBServiceImpl<>(dataSource, Account.class);
        createAccountTable(dataSource);
    }

    private static void createAccountTable(DataSource dataSource) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pst = connection.prepareStatement("create table account(no bigint(20) NOT NULL auto_increment, type varchar(255), rest number)")) {
            pst.executeUpdate();
        }
        System.out.println("table created");
    }

    @Test
    public void saveAccount() {
        Account savedAccount = dbService.save(originalAccount);
        assertEquals(originalAccount, savedAccount);
    }

    @Test
    public void updateAccount() {
        Account savedAccount = dbService.save(originalAccount);

        savedAccount.setType("Sam");
        savedAccount.setRest(new BigDecimal(25));
        Account updatedAccount = dbService.update(savedAccount);

        assertEquals(savedAccount.getNo(), updatedAccount.getNo());
        assertEquals(savedAccount, updatedAccount);
    }
}