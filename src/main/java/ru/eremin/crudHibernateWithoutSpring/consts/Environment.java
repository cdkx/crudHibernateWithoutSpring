package ru.eremin.crudHibernateWithoutSpring.consts;

import lombok.experimental.UtilityClass;


@UtilityClass
public class Environment {
    public static final String DB_URL = "jdbc:postgresql://localhost:5432/crudApp";
    public static final String DB_USER = "user";
    public static final String DB_PASSWORD = "1234";
    public static final String DB_DRIVER_CLASS = "org.postgresql.Driver";
    public static final String DB_DIALECT = "org.hibernate.dialect.PostgreSQLDialect";
}
