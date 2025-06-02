package ru.eremin.crudHibernateWithoutSpring.repository;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.eremin.crudHibernateWithoutSpring.model.User;
import ru.eremin.crudHibernateWithoutSpring.provider.SessionProvider;
import ru.eremin.crudHibernateWithoutSpring.provider.UserSessionProvider;


//TODO
// Для тестирования DAO-слоя написать интеграционные тесты с использованием Testcontainers.
class UserRepositoryTest {
    SessionProvider provider;
    SessionFactory factory;
    Repository<User, Long> repository;

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:13.3");


    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @BeforeEach
    void beforeEach() {
        String url = postgres.getJdbcUrl();
        String username = postgres.getUsername();
        String password = postgres.getPassword();
        provider = new UserSessionProvider(url, username, password);
        factory = provider.getSessionFactory();
        repository = new UserRepository(factory);
    }

    @AfterEach
    void afterEach() {

    }


    @Test
    void findById() {
    }

    @Test
    void findAll() {
    }

    @Test
    void save() {
        User user = new User("testname", "testemail", 10);
        repository.save(user);
        Assertions.assertNotNull(user.getId());
        Assertions.assertEquals("testname", user.getName());
        Assertions.assertEquals("testemail", user.getEmail());
        Assertions.assertEquals(10, user.getAge());
    }

    @Test
    void delete() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void update() {
    }
}