package de.ait.repositories;

import de.ait.models.Pizza;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PizzasRepositoryTextFileImpl implements PizzasRepository {
    private String fileName;

    public PizzasRepositoryTextFileImpl(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void save(Pizza pizza) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, true))) {
            String id = generateUniqueId();
            pizza.setId(id);
            writer.println(
                    id + "|" +
                            pizza.getId() + "|" +
                            pizza.getTitle() + "|" +
                            pizza.getSize() + "|" +
                            pizza.getPrice());
        } catch (IOException e) {
            throw new IllegalStateException("Ошибка при работе с файлом ");
        }
    }

    @Override
    public void remove(Pizza pizza) {
        List<Pizza> pizzas = getAll();
        pizzas.removeIf(p ->
                p.getId().equals(pizza.getId()) &&
                        p.getTitle().equals(pizza.getTitle()) &&
                        p.getSize().equals(pizza.getSize()) &&
                        p.getPrice() == (pizza.getPrice()));
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            for (Pizza p : pizzas) {
                writer.println(p.getId() + " " + p.getTitle() + " " + p.getSize() + " " + p.getPrice());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Pizza> getAll() {
        List<Pizza> pizzas = new ArrayList<>();

        try (FileReader fileReader = new FileReader(fileName);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            String line = bufferedReader.readLine();

            while (line != null) {
                Pizza pizza = parseLine(line);
                pizzas.add(pizza);
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            System.err.println("Произошла ошибка");
        }

        return pizzas;
    }


    @Override
    public Pizza findById(String id) {
        List<Pizza> pizzas = getAll();
        for (Pizza pizza : pizzas) {
            if (pizza.getId().equals(id)) {
                return pizza;
            }
        }
        return null;
    }

    @Override
    public Pizza findByTitle(String title) {
        List<Pizza> pizzas = getAll();
        for (Pizza pizza : pizzas) {
            if (pizza.getTitle().equals(title)) {
                return pizza;
            }
        }
        return null;
    }

    private String generateUniqueId() {
        List<Pizza> pizzas = getAll();
        int id = pizzas.size() + 1;
        return String.valueOf(id);
    }

    private static Pizza parseLine(String line) {
        String[] parsed = line.split("\\|");
        String id = parsed[0];
        String title = parsed[1];
        String size = parsed[2];
        double price = Double.parseDouble(parsed[3]);

        return new Pizza(id, title, size, price);
    }
}
