package ru.eremin.crudHibernateWithoutSpring.mapper;


public interface AbstractMapper<E, D> {
    E toEntity(D dto);

    D toDto(E entity);
}
