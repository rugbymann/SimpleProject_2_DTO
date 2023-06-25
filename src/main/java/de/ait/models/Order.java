package de.ait.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

public class Order {

    private final String id;
    private final LocalDateTime dateTime;

    private final List<Pizza> pizzas;
    private final String userId;


    public Order(String userId, List<Pizza> pizzas) {
        this.userId = userId;
        this.pizzas = pizzas;
        this.id = UUID.randomUUID().toString();
        this.dateTime = LocalDateTime.now();
    }

    public Order(String id, LocalDateTime dateTime, List<Pizza> pizzas, String userId) {
        this.id = id;
        this.dateTime = dateTime;
        this.pizzas = pizzas;
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public List<Pizza> getPizzas() {
        return pizzas;
    }

    public String getUserId() {
        return userId;
    }


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        System.out.println("-------------------------------------");
        stringBuilder.append("Заказ: \n");
        for (Pizza pizza : pizzas) {
            stringBuilder.append(pizza.getTitle())
                    .append(", размер: ")
                    .append(pizza.getSize())
                    .append(", цена: ")
                    .append(pizza.getPrice())
                    .append(" €")
                    .append("\n");
        }
        return stringBuilder.toString() + "\nВремя заказа: " + getDateTime().format(DateTimeFormatter.ofPattern("dd.MM.yyy HH:mm:ss"));

    }
}
