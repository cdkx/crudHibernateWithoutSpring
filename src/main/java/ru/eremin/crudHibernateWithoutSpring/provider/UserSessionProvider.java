package ru.eremin.crudHibernateWithoutSpring.provider;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.eremin.crudHibernateWithoutSpring.consts.Environment;
import ru.eremin.crudHibernateWithoutSpring.model.User;

import java.util.Properties;


public class UserSessionProvider implements SessionProvider {
    @Override
    public SessionFactory getSessionFactory() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.connection.driver_class", Environment.DB_DRIVER_CLASS);
        hibernateProperties.setProperty("hibernate.connection.url", Environment.DB_URL);
        hibernateProperties.setProperty("hibernate.connection.username", Environment.DB_USER);
        hibernateProperties.setProperty("hibernate.connection.password", Environment.DB_PASSWORD);
        hibernateProperties.setProperty("hibernate.connection.isolation", "TRANSACTION_SERIALIZABLE");
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "update");
        hibernateProperties.setProperty("hibernate.show_sql", "true");

        return new Configuration()
                .addProperties(hibernateProperties)
                .addAnnotatedClass(User.class)
                .buildSessionFactory();
    }
}
