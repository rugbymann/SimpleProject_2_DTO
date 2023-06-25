package de.ait.app;

import de.ait.models.Order;
import de.ait.models.Pizza;
import de.ait.models.User;
import de.ait.repositories.*;
import de.ait.services.*;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        initialize();
        Scanner scanner = new Scanner(System.in);

        UsersRepository usersRepository = new UsersRepositoryTextFileImpl("users.txt");
        PizzasRepository pizzasRepository = new PizzasRepositoryTextFileImpl("pizzas.txt");
        OrdersRepository ordersRepository = new OrdersRepositoryTextFileImpl("orders.txt");

        UsersService usersService = new UsersServiceImpl(usersRepository);
        PizzasService pizzasService = new PizzasServiceImpl(pizzasRepository);
        OrdersService ordersService = new OrdersServiceImpl(usersRepository, pizzasRepository, ordersRepository);

        System.out.println("Введите email:");
        String email = scanner.next();
        System.out.println("Введите пароль:");
        String password = scanner.next();

        User user = usersService.login(email, password);

        if (user == null) {
            System.out.println("Мне кажется, мы еще не встречались.");
            System.out.println("Познакомимся? (Y/N)");
            String answer = scanner.next();
            if (answer.equalsIgnoreCase("N")) {
                System.out.println("Вы лишили себя гастрономического оргазма!");
                return;
            } else if (answer.equalsIgnoreCase("Y")) {

                System.out.println("Введите имя: ");
                String firstName = scanner.next();
                System.out.println("Введите фамилию: ");
                String lastName = scanner.next();
                System.out.println("Скиньте телефончик: ");
                String phoneNumber = scanner.next();
                System.out.println("Введите улицу: ");
                String street = scanner.next();
                System.out.println("Введите номер дома: ");
                String houseNumber = scanner.next();
                User newUser = new User(email, password,
                        firstName, lastName, phoneNumber, street, houseNumber);
                usersService.save(newUser);
                System.out.println("Наконец-то мы вместе, " + firstName + " " + lastName + "!");
            } else {
                System.out.println("Что-то пошло не так. Сосредоточьтесь!");
                return;
            }
        }

        System.out.println("Наш ассортимент пицц:");
        for (Pizza pizza : pizzasService.getAll()) {
            System.out.println(pizza.getId() + " " +
                    pizza.getTitle() + " " +
                    pizza.getSize() + " " +
                    pizza.getPrice());
        }
        List<Pizza> selectedPizzas = new ArrayList<>();

        boolean continueOrdering = true;
        while (continueOrdering) {
            System.out.println("Выберите номер пиццы из списка:");
            int pizzaId = scanner.nextInt();
            Pizza selectedPizza = pizzasService.getAll().get(pizzaId - 1);

            selectedPizzas.add(selectedPizza);
            System.out.println("Пицца добавлена в корзину!");

            System.out.println("Это все на что Вы способны? Или продолжим? (y/n):");
            String answer = scanner.next().toLowerCase();
            continueOrdering = answer.equals("y");
        }

        Order madeOrder = ordersService.makeOrder(email, selectedPizzas);
        if (madeOrder != null) {
            LocalDateTime orderTime = LocalDateTime.now();
            System.out.println("Заказ успешно оформлен!\n" +
                    "Время доставки: " + orderTime.plusMinutes(40).
                    format(DateTimeFormatter.ofPattern("dd.MM.yyy HH:mm:ss")));
            String.format("%8s%30s%8s%20s", "Позиция", "Название", "Цена", "ИД");
            System.out.println(madeOrder.toString());
            double totalPrice = ordersService.calculateTotalPrice(madeOrder);
            System.out.println("Общая стоимость заказа: " + totalPrice + " €");
            System.out.println("-------------------------------------");
        } else {
            System.out.println("Не удалось оформить заказ.");
        }
        FileWriter fileWriter =
                new FileWriter("./orders/" + madeOrder.getId() + ".txt");
        PrintWriter printWriter = new PrintWriter(fileWriter);
        String orderReceipt = ordersService.generateOrderReceipt(madeOrder);
        printWriter.write(orderReceipt);
        printWriter.close();

        System.out.println("Пицца уже в пути! Наш Руслан доставит заказ в лучшем виде.\nЧаевые приветствуются.\nЧек отправлен на ваш email.");
    }

    public static void initialize() throws IOException {
       /* PrintWriter writer = new PrintWriter(new FileWriter("pizzas.txt"));
        List<Pizza> pizzas = new ArrayList<>();
        pizzas.add(new Pizza("1", "Margarita", "SMALL", 6.00));
        pizzas.add(new Pizza("2", "Margarita", "MIDDLE", 9.00));
        pizzas.add(new Pizza("3", "Margarita", "FAMILY", 12.00));
        pizzas.add(new Pizza("4", "Funghi", "SMALL", 7.00));
        pizzas.add(new Pizza("5", "Funghi", "MIDDLE", 10.50));
        pizzas.add(new Pizza("6", "Funghi", "FAMILY", 14.00));
        pizzas.add(new Pizza("7", "Salami", "SMALL", 7.90));
        pizzas.add(new Pizza("8", "Salami", "MIDDLE", 12.00));
        pizzas.add(new Pizza("9", "Salami", "FAMILY", 15.50));
        pizzas.add(new Pizza("10", "Prosciutto", "SMALL", 8.00));
        pizzas.add(new Pizza("11", "Prosciutto", "MIDDLE", 13.10));
        pizzas.add(new Pizza("12", "Prosciutto", "FAMILY", 16.50));
        pizzas.add(new Pizza("13", "FruttiDiMare", "SMALL", 8.70));
        pizzas.add(new Pizza("14", "FruttiDiMare", "MIDDLE", 13.90));
        pizzas.add(new Pizza("15", "FruttiDiMare", "FAMILY", 16.50));
        for (Pizza pizza : pizzas) {
            writer.println(pizza.getId() + "|" + pizza.getTitle() + "|" + pizza.getSize() + "|" + pizza.getPrice());
        }
        writer.close();*/
    }
}
