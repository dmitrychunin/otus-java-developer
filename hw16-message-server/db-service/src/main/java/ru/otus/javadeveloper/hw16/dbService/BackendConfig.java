package ru.otus.javadeveloper.hw16.dbService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.javadeveloper.hw16.common.model.User;
import ru.otus.javadeveloper.hw16.dbService.backend.executor.DbExecutorHibernate;
import ru.otus.javadeveloper.hw16.dbService.backend.executor.DbHibernateExecutorHibernateImpl;

@Configuration
public class BackendConfig {

    @Bean
    public DbExecutorHibernate<User> dbService() {
        return new DbHibernateExecutorHibernateImpl<>();
    }
}
