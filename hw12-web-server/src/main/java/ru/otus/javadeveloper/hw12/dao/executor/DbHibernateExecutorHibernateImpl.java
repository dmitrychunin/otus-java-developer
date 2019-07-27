package ru.otus.javadeveloper.hw12.dao.executor;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import ru.otus.javadeveloper.hw12.dao.model.AddressDataSet;
import ru.otus.javadeveloper.hw12.dao.model.PhoneDataSet;
import ru.otus.javadeveloper.hw12.dao.model.User;

import java.util.List;

public class DbHibernateExecutorHibernateImpl<T> implements DbExecutorHibernate<T> {
    private final SessionFactory sessionFactory;


    public DbHibernateExecutorHibernateImpl() {
        var configuration = new Configuration()
                .configure("hibernate.cfg.xml");

        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();

        Metadata metadata = new MetadataSources(serviceRegistry)
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(AddressDataSet.class)
                .addAnnotatedClass(PhoneDataSet.class)
                .getMetadataBuilder()
                .build();

        sessionFactory = metadata.getSessionFactoryBuilder().build();
    }

    @Override
    public T create(T objectData) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(objectData);
            session.getTransaction().commit();
        }
        return objectData;
    }

    @Override
    public T update(T objectData) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(objectData);
            session.getTransaction().commit();
        }
        return objectData;
    }

    @Override
    public T createOrUpdate(T objectData) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.saveOrUpdate(objectData);
            session.getTransaction().commit();
        }
        return objectData;
    }

    @Override
    public T load(long id, Class<T> clazz) {
        T result;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            result = session.get(clazz, id);
        }
        return result;
    }

    @Override
    public List<T> loadAll(Class<T> clazz) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            return session.createQuery("SELECT entity FROM " + clazz.getSimpleName() + " entity", clazz).getResultList();
        }
    }
}
