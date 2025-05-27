package ru.eremin.crudHibernateWithoutSpring.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.eremin.crudHibernateWithoutSpring.mapper.UserMapper;
import ru.eremin.crudHibernateWithoutSpring.model.User;
import ru.eremin.crudHibernateWithoutSpring.model.dto.UserDTO;
import ru.eremin.crudHibernateWithoutSpring.repository.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final Repository<User, Long> repository;

    public UserDTO findById(Long id) {
        log.info("Find user by id {}", id);
        if (id < 0) {
            log.error("id cannot be negative");
            throw new IllegalArgumentException();
        }
        Optional<User> optionalUser = repository.findById(id);
        if (optionalUser.isEmpty()) {
            log.error("id {} not found", id);
            throw new IllegalArgumentException("User with id " + id + " not found");
        } else {
            return UserMapper.INSTANCE.mapToDTO(optionalUser.get());
        }
    }

    public List<UserDTO> findAll() {
        log.info("Find all");
        List<User> list = repository.findAll();
        if (list.isEmpty()) {
            log.error("Users not found");
            throw new IllegalArgumentException("Users not found");
        }
        return list.stream().map(UserMapper.INSTANCE::mapToDTO).collect(Collectors.toList());
    }

    public void save(String name, String email, int age) {
        repository.save(createUser(name, email, age));
    }

    public void delete(UserDTO userDTO) {
        User user = UserMapper.INSTANCE.mapToUser(userDTO);
        repository.delete(user);
    }

    public void delete(long id) {
        log.info("delete user by id {}", id);
        if (id < 0) {
            log.error("user cannot be deleted, id cannot be negative");
            throw new IllegalArgumentException();
        }
        repository.deleteById(id);
    }

    public UserDTO updateUserAge(Long id, int age) {
        if (age < 0) {
            log.error("age cannot be negative");
            throw new IllegalArgumentException("Возраст должен быть положительным числом");
        }
        Optional<User> optionalUser = repository.findById(id);
        if (optionalUser.isEmpty()) {
            log.error("id {} not found", id);
            throw new IllegalArgumentException("User with id " + id + " not found");
        }
        User user = optionalUser.get();
        user.setAge(age);
        return UserMapper.INSTANCE.mapToDTO(repository.update(user));
    }

    public UserDTO updateUserEmail(Long id, String email) {
        Optional<User> optionalUser = repository.findById(id);
        if (optionalUser.isEmpty()) {
            log.error("id {} not found", id);
            throw new IllegalArgumentException("User with id " + id + " not found");
        }
        User user = optionalUser.get();
        user.setEmail(email);
        return UserMapper.INSTANCE.mapToDTO(repository.update(user));
    }

    private User createUser(String name, String email, int age) {
        if (name == null) {
            log.error("name is null");
            throw new IllegalArgumentException("Имя не может быть null");
        }
        if (email == null) {
            log.error("email is null");
            throw new IllegalArgumentException("Email не может быть null");
        }
        if (age < 0) {
            log.error("age cannot be negative");
            throw new IllegalArgumentException("Возраст должен быть положительным числом");
        }
        return new User(name, email, age);
    }
}
