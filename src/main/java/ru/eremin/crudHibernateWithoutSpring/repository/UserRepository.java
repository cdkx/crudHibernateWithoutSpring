package ru.eremin.crudHibernateWithoutSpring.repository;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.eremin.crudHibernateWithoutSpring.model.User;

import java.util.List;


@RequiredArgsConstructor
public class UserRepository implements Repository<User, Long> {
    private final SessionFactory sessionFactory;


    @Override
    public User findById(Long aLong) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return List.of();
    }

    @Override
    public void save(User user) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
        }
    }

    @Override
    public void delete(User user) {

    }

    @Override
    public User update(User user) {
        return null;
    }
}
