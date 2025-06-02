package ru.eremin.crudHibernateWithoutSpring.repository;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.eremin.crudHibernateWithoutSpring.mapper.UserMapper;
import ru.eremin.crudHibernateWithoutSpring.model.User;
import ru.eremin.crudHibernateWithoutSpring.model.dto.UserDTO;
import ru.eremin.crudHibernateWithoutSpring.provider.SessionProvider;
import ru.eremin.crudHibernateWithoutSpring.provider.UserSessionProvider;
import ru.eremin.crudHibernateWithoutSpring.service.UserService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;


@Slf4j
@Testcontainers
class UserServiceIntegrationTest {
    SessionProvider provider;
    SessionFactory sessionFactory;
    UserRepository userRepository;
    UserService userService;
    String username = "test";
    String password = "test";
    User user1;
    User user2;
    User user3;
    Long id1;
    Long id2;
    Long id3;

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:13.3");

    @BeforeEach
    void beforeEach() {
        provider = new UserSessionProvider(postgres.getJdbcUrl(), username, password);
        sessionFactory = provider.getSessionFactory();
        userRepository = new UserRepository(sessionFactory);
        userService = new UserService(UserMapper.INSTANCE, userRepository);

        user1 = new User("testName", "testEmail", 10);
        user2 = new User("testName2", "testEmai2", 12);
        user3 = new User("testName3", "testEmai3", 13);
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        id1 = user1.getId();
        id2 = user2.getId();
        id3 = user3.getId();
    }

    @AfterEach
    void afterEach() {
        userRepository.dropAll();
    }

    @Test
    void shouldGetCorrectUserById() {
        UserDTO userDTO = userService.findById(id1);
        assertEquals(id1, userDTO.id());
        assertEquals("testName", userDTO.name());
        assertEquals("testEmail", userDTO.email());
        assertEquals(10, userDTO.age());
    }

    @Test
    void shouldGetAllUsers() {
        List<UserDTO> list = userService.findAll();
        assertEquals(3, list.size());
    }

    @Test
    void shouldDeleteUserById() {
        userRepository.deleteById(id1);
        userRepository.deleteById(id2);
        userRepository.deleteById(id3);
        assertEquals(Optional.empty(), userRepository.findById(id1));
        assertEquals(Optional.empty(), userRepository.findById(id2));
        assertEquals(Optional.empty(), userRepository.findById(id3));
    }

    @Test
    void shouldUpdateUser() {
        String nameBeforeUpdate = user1.getName();
        log.info("name before update {}", nameBeforeUpdate);

        String expected = "newTestName";
        log.info("expected name before update {}", expected);

        user1.setName(expected);
        userRepository.update(user1);
        String actualNameAfterUpdate = user1.getName();
        log.info("actual name after update {}", actualNameAfterUpdate);

        assertEquals(expected, actualNameAfterUpdate);
    }
}