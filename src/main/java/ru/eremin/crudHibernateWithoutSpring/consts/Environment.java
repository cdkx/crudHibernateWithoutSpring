package ru.eremin.crudHibernateWithoutSpring.consts;

import lombok.experimental.UtilityClass;


@UtilityClass
public class Environment {
    public static final String DB_URL = System.getenv("DB_URL_CRUDAPP");
    public static final String DB_USER = System.getenv("DB_USER");
    public static final String DB_PASSWORD = System.getenv("DB_PASSWORD");
    public static final String DRIVER_POSTGRESQL = System.getenv("DB_DRIVER_CLASS_POSTGRESQL");
    public static final String DIALECT_POSTGRESQL = System.getenv("DB_DIALECT_POSTGRESQL");
    public static final String TRANSACTION_SERIALIZABLE = "TRANSACTION_SERIALIZABLE";
}
