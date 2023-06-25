package de.ait.repositories;

import de.ait.models.Order;
import de.ait.models.Pizza;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class OrdersRepositoryTextFileImpl implements OrdersRepository {

    private String fileName;

    public OrdersRepositoryTextFileImpl(String fileName) {
        this.fileName = fileName;

    }

    @Override
    public void save(Order order) {

        List<Pizza> pizzas = order.getPizzas();
        List<String> pizzasIds = new ArrayList<>();
        for (Pizza pizza : pizzas) {
            pizzasIds.add(pizza.getId());
        }


        try (BufferedWriter writer =
                     new BufferedWriter(
                             new FileWriter(
                                     fileName, true))) {
            String orderAsTxt = String.format("%s|%s|%s",
                    order.getId(),
                    pizzasIds,
                    order.getUserId()
            );
            writer.write(orderAsTxt);
            writer.newLine();
        } catch (IOException e) {
            throw new IllegalStateException("Проблемы с файлом");
        }
    }

    @Override
    public void remove(Order model) {
    }

    @Override
    public List<Order> getAll() {
        return null;
    }

    @Override
    public int getQuantity(Order order) {
        int count = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 3 && parts[1].equals(order.getPizzas()) && parts[2].equals(order.getUserId())) {
                    count++;
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException("Проблемы с файлом");
        }
        return count;
    }

    @Override
    public Order findById(String id) {
//        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                String[] parts = line.split("\\|");
//                if (parts.length == 3 && parts[0].equals(id)) {
//                    return new Order(
//                            parts[0],
//                            LocalDateTime.parse(parts[1]),
//                            parts[2],
//                            parts[3]
//                    );
//                }
//            }
//        } catch (IOException e) {
//            throw new IllegalStateException("Проблемы с файлом");
//        }
        return null;
    }
}
