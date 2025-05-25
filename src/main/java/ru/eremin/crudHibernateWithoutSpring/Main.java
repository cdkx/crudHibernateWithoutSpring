package ru.eremin.crudHibernateWithoutSpring;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import ru.eremin.crudHibernateWithoutSpring.model.User;
import ru.eremin.crudHibernateWithoutSpring.provider.*;
import ru.eremin.crudHibernateWithoutSpring.repository.UserRepository;


@Slf4j
public class Main {
    public static void main(String[] args) {
        SessionProvider provider = new UserSessionProvider();
        SessionFactory factory = provider.getSessionFactory();
        User user = new User("Vasya","emai",122);


        new UserRepository(factory).save(user);
    }
}
