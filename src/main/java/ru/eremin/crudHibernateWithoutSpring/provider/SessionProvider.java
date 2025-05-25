package ru.eremin.crudHibernateWithoutSpring.provider;

import org.hibernate.SessionFactory;


public interface SessionProvider {
    SessionFactory getSessionFactory();
}
