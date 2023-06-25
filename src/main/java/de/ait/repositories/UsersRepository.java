package de.ait.repositories;

import de.ait.models.User;

public interface UsersRepository extends CrudRepository<User> {
    User findByEmail(String email, String pass);
    User findByEmail(String email);
}

