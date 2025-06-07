package ru.eremin.crudHibernateWithoutSpring.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.eremin.crudHibernateWithoutSpring.mapper.AbstractMapper;
import ru.eremin.crudHibernateWithoutSpring.model.User;
import ru.eremin.crudHibernateWithoutSpring.model.dto.UserDTO;
import ru.eremin.crudHibernateWithoutSpring.repository.Repository;

import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    Repository<User, Long> repository;

    @Mock
    AbstractMapper<User, UserDTO> mapper;

    @InjectMocks
    UserService userService;

    User user;
    UserDTO userDTO;


    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setName("testName");
        user.setEmail("testEmail@com");
        user.setAge(36);

        userDTO = new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getAge());
    }

    @Test
    void shouldReturnDtoByIdIfUserExists() {
        when(repository.findById(1L)).thenReturn(Optional.of(user));
        when(mapper.toDto(user)).thenReturn(userDTO);

        UserDTO found = userService.findById(1L);

        assertNotNull(found);
        assertEquals("testName", found.name());
        verify(repository).findById(1L);
    }

    @Test
    void shouldReturnMappedListOfUsers() {
        when(repository.findAll()).thenReturn(List.of(user));
        when(mapper.toDto(user)).thenReturn(userDTO);

        List<UserDTO> users = userService.findAll();

        assertEquals(1, users.size());
        assertEquals("testName", users.get(0).name());
        verify(repository).findAll();
    }

    @Test
    void shouldCreateUserAndSaveToRepository() {
        String name = "testName2";
        String email = "testMail";
        int age = 45;
        User expectedUser = new User(name, email, age);

        userService.save(name, email, age);

        verify(repository).save(refEq(expectedUser));
    }

    @Test
    void shouldCallRepositoryIfIdValid() {
        Long id = 1L;

        userService.deleteById(id);

        verify(repository).deleteById(id);
    }

    @Test
    void shouldThrowExceptionIfdNegative() {
        Long id = -5L;

        assertThrows(IllegalArgumentException.class, () -> userService.deleteById(id));
        verifyNoInteractions(repository);
    }


    @Test
    void shouldThrowExceptionIfAgeIsNegative() {
        Long userId = 1L;
        int invalidAge = -10;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.updateUserAge(userId, invalidAge));
        assertEquals("Возраст должен быть положительным числом", exception.getMessage());

        verifyNoInteractions(repository);
        verifyNoInteractions(mapper);
    }

    @Test
    void shouldUpdateAgeAndReturnDtoIfValidInput() {
        Long id = 1L;
        int newAge = 35;
        User existingUser = new User();
        existingUser.setId(id);
        existingUser.setName("vasya");
        existingUser.setEmail("vasya@mail");
        existingUser.setAge(20);

        User updatedUser = new User();
        updatedUser.setId(id);
        updatedUser.setName("vasya");
        updatedUser.setEmail("vasya@mail");
        updatedUser.setAge(newAge);

        UserDTO dto = new UserDTO(id, "vasya", "vasya@mail", newAge);

        when(repository.findById(id)).thenReturn(Optional.of(existingUser));
        when(repository.update(any(User.class))).thenReturn(updatedUser);
        when(mapper.toDto(updatedUser)).thenReturn(dto);

        UserDTO result = userService.updateUserAge(id, newAge);

        assertNotNull(result);
        assertEquals(newAge, result.age());
        verify(repository).findById(id);
        verify(repository).update(existingUser);
        verify(mapper).toDto(updatedUser);
    }

    @Test
    void updateUserAge_ShouldThrowExceptionIfUserNotFound() {
        Long id = 95L;
        int newAge = 40;

        when(repository.findById(id)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.updateUserAge(id, newAge));
        assertEquals("User with id " + id + " not found", exception.getMessage());

        verify(repository).findById(id);
        verifyNoMoreInteractions(repository);
        verifyNoInteractions(mapper);
    }

    @Test
    void shouldUpdateEmailAndReturnDtoIfUserExists() {
        Long id = 1L;
        String newEmail = "new@Mail";

        User existingUser = new User();
        existingUser.setId(id);
        existingUser.setEmail("oldMail");

        User updatedUser = new User();
        updatedUser.setId(id);
        updatedUser.setEmail(newEmail);

        UserDTO expectedDto = new UserDTO(id, "petya", newEmail, 20);

        when(repository.findById(id)).thenReturn(Optional.of(existingUser));
        when(repository.update(existingUser)).thenReturn(updatedUser);
        when(mapper.toDto(updatedUser)).thenReturn(expectedDto);

        UserDTO expected = userService.updateUserEmail(id, newEmail);

        assertNotNull(expected);
        assertEquals(newEmail, expected.email());
        verify(repository).findById(id);
        verify(repository).update(existingUser);
        verify(mapper).toDto(updatedUser);
    }

    @Test
    void updateUserEmail_ShouldThrowExceptionIfUserNotFound() {
        Long userId = 153L;
        String newEmail = "mail";

        when(repository.findById(userId)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.updateUserEmail(userId, newEmail));
        assertEquals("User with id " + userId + " not found", exception.getMessage());

        verify(repository).findById(userId);
        verifyNoMoreInteractions(repository);
        verifyNoInteractions(mapper);
    }
}
