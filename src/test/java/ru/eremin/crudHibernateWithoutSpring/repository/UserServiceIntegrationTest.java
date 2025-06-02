package ru.eremin.crudHibernateWithoutSpring.repository;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.eremin.crudHibernateWithoutSpring.mapper.UserMapper;
import ru.eremin.crudHibernateWithoutSpring.model.User;
import ru.eremin.crudHibernateWithoutSpring.model.dto.UserDTO;
import ru.eremin.crudHibernateWithoutSpring.provider.SessionProvider;
import ru.eremin.crudHibernateWithoutSpring.provider.UserSessionProvider;
import ru.eremin.crudHibernateWithoutSpring.service.UserService;

import java.util.List;


//TODO
// Для тестирования DAO-слоя написать интеграционные тесты с использованием Testcontainers.
class UserServiceIntegrationTest {
    SessionProvider provider;
    SessionFactory factory;
    Repository<User, Long> repository;
    UserService userService;

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
        userService = new UserService(UserMapper.INSTANCE, repository);

        User user = new User("testname", "testemail", 10);
        repository.save(user);
    }

    @AfterEach
    void afterEach() {

    }


    @Test
    void findById() {
        /*
        * Проверьте, что данные успешно извлекаются.
Проверьте, что извлекаются правильные данные.
Проверьте, что данные соответствуют фильтрам.
        * */
    }

    @Test
    void shouldGetAllUsers() {

        List<UserDTO> list =  userService.findAll();
        Assertions.assertEquals(1, list.size());
    }

    @Test
    void save() {
        /*
        * Проверьте, что данные успешно создаются.
Проверьте, что соблюдены правила валидации данных.
Проверьте, что данные сохраняются в соответствии с требованиями.
* */


//        Assertions.assertNotNull(user.getId());
//        Assertions.assertEquals("testname", user.getName());
//        Assertions.assertEquals("testemail", user.getEmail());
//        Assertions.assertEquals(10, user.getAge());
    }

    @Test
    void delete() {
    }

    @Test
    void deleteById() {
        /* Проверьте, что данные успешно удаляются.
Проверьте, что данные удаляются в соответствии с требованиями.
*/
    }

    @Test
    void update() {
        /*Проверьте, что данные успешно обновляются.
Проверьте, что соблюдены правила валидации данных.
Проверьте, что данные обновляются в соответствии с требованиями.
*/
    }
}