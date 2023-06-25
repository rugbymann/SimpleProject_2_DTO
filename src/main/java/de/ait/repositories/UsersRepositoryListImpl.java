package de.ait.repositories;

import de.ait.models.User;

import java.util.ArrayList;
import java.util.List;

public class UsersRepositoryListImpl implements UsersRepository {
    private List<User> users;

    public UsersRepositoryListImpl() {
        this.users = new ArrayList<>();
    }


    @Override
    public void save(User user) {
        users.add(user);
    }

    @Override
    public void remove(User user) {
        users.remove(user);
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(users);
    }

    @Override
    public User findById(String id) {
        return null;
    }// скорее всего меняем

    @Override
    public User findByEmail(String email, String pass) {
        return null;
    }

    @Override
    public User findByEmail(String email) {
        return null;
    }
}
