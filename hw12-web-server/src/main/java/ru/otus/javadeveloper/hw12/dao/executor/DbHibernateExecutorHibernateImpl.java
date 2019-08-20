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
import java.util.function.BiConsumer;

public class DbHibernateExecutorHibernateImpl implements DbExecutorHibernate {
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
    public Object create(Object objectData) {
        return makeActionWithinHibernateSessionTransaction(objectData, (Session::save));
    }

    @Override
    public Object update(Object objectData) {
        return makeActionWithinHibernateSessionTransaction(objectData, (Session::merge));
    }

    @Override
    public Object createOrUpdate(Object objectData) {
        return makeActionWithinHibernateSessionTransaction(objectData, (Session::saveOrUpdate));
    }

    @Override
    public Object load(long id, Class<?> clazz) {
        Object result;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            result = session.get(clazz, id);
        }
        return result;
    }

    @Override
    public List<?> loadAll(Class<?> clazz) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            return session.createQuery("SELECT entity FROM " + clazz.getSimpleName() + " entity", clazz).getResultList();
        }
    }

    private Object makeActionWithinHibernateSessionTransaction(Object objectData, BiConsumer<Session, Object> action) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            action.accept(session, objectData);
            session.getTransaction().commit();
        }
        return objectData;
    }
}
