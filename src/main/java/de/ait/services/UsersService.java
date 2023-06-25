package de.ait.services;
import de.ait.models.User;

import java.util.List;

public interface UsersService {
    User login(String email, String password);

    void save(User user);

    void remove(User user);

    List<User> getAllUsers();
}
