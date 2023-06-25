package de.ait.repositories;

import de.ait.models.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UsersRepositoryTextFileImpl implements UsersRepository {
    private String fileName;

    public UsersRepositoryTextFileImpl(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void save(User user) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, true))) {
            String id = generateUniqueId();
            user.setId(id);
            writer.println(
                    id + "|" +
                            user.getEmail() + "|" +
                            user.getPassword() + "|" +
                            user.getFirstName() + "|" +
                            user.getLastName() + "|" +
                            user.getPhoneNumber() + "|" +
                            user.getStreet() + "|" +
                            user.getHouseNumber());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void remove(User user) {
        List<User> users = getAll();
        users.removeIf(u ->
                u.getId().equals(user.getId()) &&
                        u.getEmail().equals(user.getEmail()) &&
                        u.getPassword().equals(user.getPassword()) &&
                        u.getFirstName().equals(user.getFirstName()) &&
                        u.getLastName().equals(user.getLastName()) &&
                        u.getPhoneNumber().equals(user.getPhoneNumber()) &&
                        u.getStreet().equals(user.getStreet()) &&
                        u.getHouseNumber().equals(user.getHouseNumber()));
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            for (User u : users) {
                writer.println(u.getId() + "|" +
                        u.getEmail() + "|" +
                        u.getPassword() + "|" +
                        u.getFirstName() + "|" +
                        u.getLastName() + "|" +
                        u.getPhoneNumber() + "|" +
                        u.getStreet() + "|" +
                        u.getHouseNumber());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();

        try (FileReader fileReader = new FileReader(fileName);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            String line = bufferedReader.readLine();

            while (line != null) {
                User user = parseLine(line);
                users.add(user);
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            System.err.println("Невозможно прочитать файл");
        }
        return users;
    }

    @Override
    public User findById(String id) {
        List<User> users = getAll();
        for (User user : users) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public User findByEmail(String email, String pass) {
        User user = findByEmail(email);
        if (user != null && (user.getPassword().equals(pass))) {
            return user;
        }
        return null;
    }

    @Override
    public User findByEmail(String email) {
        List<User> users = getAll();
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return user;
            }
        }
        return null;
    }

    private String generateUniqueId() {
        List<User> users = getAll();
        int id = users.size() + 1;
        return String.valueOf(id);
    }

    private static User parseLine(String line) {
        String[] parsed = line.split("\\|");
        String id = parsed[0];
        String email = parsed[1];
        String password = parsed[2];
        String firstName = parsed[3];
        String lastName = parsed[4];
        String phoneNumber = parsed[5];
        String street = parsed[6];
        String houseNumber = parsed[7];
        return new User(id, email, password, firstName, lastName, phoneNumber, street, houseNumber);
    }
}
