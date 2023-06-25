package de.ait.services;

import de.ait.models.User;
import de.ait.repositories.UsersRepository;
import de.ait.repositories.UsersRepositoryListImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class UsersServiceImplTest {
    private UsersService usersService;

    @BeforeEach
    public void setUp() {
        UsersRepository usersRepository = new UsersRepositoryListImpl();
        usersService = new UsersServiceImpl(usersRepository);
    }

    @Test
    public void testSaveUser() {

        User user = new User("1", "test@example.com", "password", "John", "Doe", "123456789", "Street", "1");

        usersService.save(user);

        List<User> users = usersService.getAllUsers();
        Assertions.assertEquals(1, users.size());
        Assertions.assertEquals(user, users.get(0));
    }

    @Test
    public void testRemoveUser() {
        User user = new User("1", "test@example.com", "password", "John", "Doe", "123456789", "Street", "1");
        usersService.save(user);

        usersService.remove(user);

        List<User> users = usersService.getAllUsers();
        Assertions.assertTrue(users.isEmpty());
    }

    @Test
    public void testLoginWithValidCredentials() {

        User user = new User("1", "test@example.com", "password",
                "John", "Doe", "123456789", "Street", "1");
        usersService.save(user);

        User loggedInUser = usersService.login("test@example.com", "password");

        Assertions.assertEquals(user, loggedInUser);
    }

    @Test
    public void testLoginWithInvalidCredentials() {

        User user = new User("1", "test@example.com", "password", "John", "Doe", "123456789", "Street", "1");
        usersService.save(user);

        User loggedInUser = usersService.login("test@example.com", "wrongpassword");

        Assertions.assertNull(loggedInUser);
    }
}

