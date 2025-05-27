package ru.eremin.crudHibernateWithoutSpring;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;

import ru.eremin.crudHibernateWithoutSpring.model.User;
import ru.eremin.crudHibernateWithoutSpring.model.dto.UserDTO;
import ru.eremin.crudHibernateWithoutSpring.provider.*;
import ru.eremin.crudHibernateWithoutSpring.repository.Repository;
import ru.eremin.crudHibernateWithoutSpring.repository.UserRepository;
import ru.eremin.crudHibernateWithoutSpring.service.UserService;

import java.util.Scanner;


@Slf4j
public class Main {
    public static void main(String[] args) {
        SessionProvider provider = new UserSessionProvider();
        log.info("provider created");
        SessionFactory factory = provider.getSessionFactory();
        log.info("SessionFactory created");
        Repository<User, Long> repository = new UserRepository(factory);
        log.info("UserRepository created");
        UserService service = new UserService(repository);
        log.info("UserService created");

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\nЧего желаете?");
            System.out.println("1. Добавить нового пользователя");//
            System.out.println("2. Найти пользователя по ID");//
            System.out.println("3. Показать всех пользователей");//
            System.out.println("4. Удалить пользователя по ID");//
            System.out.println("5. Обновить email пользователя");//
            System.out.println("6. Обновить возраст пользователя");//
            System.out.println("0. Выход");
            System.out.print("\nВыберите действие: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        System.out.print("Введите имя: ");
                        String name = scanner.nextLine();
                        System.out.print("Введите email: ");
                        String email = scanner.nextLine();
                        System.out.print("Введите возраст: ");
                        int age = Integer.parseInt(scanner.nextLine());
                        service.save(name, email, age);
                        System.out.println("Пользователь успешно добавлен!");
                        break;

                    case 2:
                        System.out.print("Введите ID пользователя: ");
                        Long id = Long.parseLong(scanner.nextLine());
                        try {
                            UserDTO foundUser = service.findById(id);
                            System.out.println("Найден пользователь: " + foundUser);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Пользователь с ID " + id + " не найден");
                        }
                        break;

                    case 3:
                        System.out.println("Список всех пользователей:");
                        service.findAll().forEach(System.out::println);
                        break;

                    case 4:
                        System.out.print("Введите ID пользователя для удаления: ");
                        Long deleteId = Long.parseLong(scanner.nextLine());
                        try {
                            service.delete(deleteId);
                            System.out.println("пользователь c id " + deleteId + " удален");
                        } catch (IllegalArgumentException e) {
                            System.out.println("Пользователь с id " + deleteId + " не найден");
                        }
                        break;

                    case 5:
                        System.out.print("Введите ID пользователя: ");
                        Long emailUpdateId = Long.parseLong(scanner.nextLine());
                        System.out.print("Введите новый email: ");
                        String newEmail = scanner.nextLine();
                        System.out.println(service.updateUserEmail(emailUpdateId, newEmail));
                        System.out.println("Email пользователя обновлен");
                        break;

                    case 6:
                        System.out.print("Введите ID пользователя: ");
                        Long ageUpdateId = Long.parseLong(scanner.nextLine());
                        System.out.print("Введите новый возраст: ");
                        int newAge = Integer.parseInt(scanner.nextLine());
                        System.out.println(service.updateUserAge(ageUpdateId, newAge));
                        System.out.println("Возраст пользователя обновлен");
                        break;

                    case 0:
                        running = false;
                        System.out.println("Всего хорошего");
                        break;

                    default:
                        System.out.println("Неверный выбор. Пожалуйста, выберите действие из меню");
                }
            } catch (NumberFormatException e) {
                System.out.println("Ошибка! Пожалуйста, введите корректное числовое значение");
            } catch (Exception e) {
                log.error("Произошла ошибка: ", e);
                System.out.println("Произошла ошибка: " + e.getMessage());
            }
        }
        scanner.close();
    }
}
