package ru.eremin.crudHibernateWithoutSpring.provider;

import lombok.AllArgsConstructor;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.eremin.crudHibernateWithoutSpring.model.User;

import java.util.Properties;

import static ru.eremin.crudHibernateWithoutSpring.consts.Environment.*;


@AllArgsConstructor
public class UserSessionProvider implements SessionProvider {
    private final String url;
    private final String username;
    private final String password;

    @Override
    public SessionFactory getSessionFactory() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.connection.driver_class", DB_DRIVER_POSTGRESQL);
        hibernateProperties.setProperty("hibernate.connection.url", url);
        hibernateProperties.setProperty("hibernate.connection.username", username);
        hibernateProperties.setProperty("hibernate.connection.password", password);
        hibernateProperties.setProperty("hibernate.connection.isolation", TRANSACTION_SERIALIZABLE);
        hibernateProperties.setProperty("hibernate.dialect", DB_DIALECT_POSTGRESQL);
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "update");
        hibernateProperties.setProperty("hibernate.show_sql", "true");

        return new Configuration()
                .addProperties(hibernateProperties)
                .addAnnotatedClass(User.class)
                .buildSessionFactory();
    }
}
