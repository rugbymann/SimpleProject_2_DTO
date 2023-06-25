package de.ait.services;

import de.ait.models.User;
import de.ait.repositories.UsersRepository;

import java.util.*;

public class UsersServiceImpl implements UsersService {
    private UsersRepository usersRepository;

    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public void save(User user) {
        usersRepository.save(user);
    }

    @Override
    public void remove(User user) { //id удаление
        usersRepository.remove(user);
    }

    @Override
    public List<User> getAllUsers() {
        return usersRepository.getAll();
    }

    @Override
    public User login(String email, String password) {
        for (User user : getAllUsers()) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
}
