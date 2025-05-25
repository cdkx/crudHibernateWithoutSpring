package ru.eremin.crudHibernateWithoutSpring.repository;

import java.io.Serializable;
import java.util.List;


public interface Repository<T, ID extends Serializable> {
    T findById(ID id);

    List<T> findAll();

    void save(T t);

    void delete(T t);

    T update(T t);
}
