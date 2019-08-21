package ru.otus.javadeveloper.hw15;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.javadeveloper.hw15.backend.executor.DbExecutorHibernate;
import ru.otus.javadeveloper.hw15.backend.executor.DbHibernateExecutorHibernateImpl;
import ru.otus.javadeveloper.hw15.backend.model.User;

@Configuration
public class BackendConfig {

    @Bean
    public DbExecutorHibernate<User> dbService() {
        return new DbHibernateExecutorHibernateImpl<>();
    }
}
