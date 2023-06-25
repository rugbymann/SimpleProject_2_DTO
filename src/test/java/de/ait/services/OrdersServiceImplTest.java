package de.ait.services;

import de.ait.models.Order;
import de.ait.models.Pizza;
import de.ait.models.User;
import de.ait.repositories.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrdersServiceImplTest {
    private OrdersRepository ordersRepository;
    private OrdersService ordersService;
    private UsersRepository usersRepository;
    private PizzasRepository pizzasRepository;

    @BeforeEach
    void setUp() {

        usersRepository = new UsersRepositoryTextFileImpl("users.txt");
        pizzasRepository = new PizzasRepositoryTextFileImpl("pizzas.txt");
        ordersRepository = new OrdersRepositoryTextFileImpl("orders.txt");
        ordersService = new OrdersServiceImpl(usersRepository, pizzasRepository, ordersRepository);
    }

    @Test
    void makeOrder_withValidUserAndValidPizzas_ReturnOrderObject() throws IOException {

        User user = usersRepository.findByEmail("first@example.com");

        List<Pizza> pizzas = new ArrayList<>();
        pizzas.add(pizzasRepository.findById("3"));
        pizzas.add(pizzasRepository.findById("9"));

        Order expectedOrder = new Order("a2c3a740-3d3b-4a7b-9364-90ab83c0aa06", LocalDateTime.now(), pizzas, "38");
        double expectedTotalPrice = 27.50;


        Order actualOrder = ordersService.makeOrder(user.getEmail(), pizzas);
        double actualTotalPrice = ordersService.calculateTotalPrice(actualOrder);

        assertEquals(expectedOrder.getUserId(), actualOrder.getUserId());
        assertEquals(expectedOrder.getPizzas(), actualOrder.getPizzas());
        assertTrue(actualOrder.getDateTime().isAfter(expectedOrder.getDateTime().minusSeconds(1)));
        assertEquals(expectedTotalPrice, actualTotalPrice);
    }


    @Test
    void makeOrder_withNullUser_ReturnIllegalArgumentException() {

        List<Pizza> pizzas = new ArrayList<>();
        pizzas.add(pizzasRepository.findById("3"));
        pizzas.add(pizzasRepository.findById("9"));


        assertThrows(IllegalArgumentException.class, () -> {
            ordersService.makeOrder(null, pizzas);
        });
    }

    @Test
    void calculateTotalPrice_SinglePizza_ReturnsPizzaPrice() {

        List<Pizza> pizzas = new ArrayList<>();
        Pizza pizza = new Pizza("1", "Margarita", "SMALL", 6.00);
        pizzas.add(pizza);
        Order order = new Order("123", LocalDateTime.now(), pizzas, "olaf@web.de");

        double totalPrice = ordersService.calculateTotalPrice(order);

        assertEquals(pizza.getPrice(), totalPrice);
    }

    @Test
    void generateOrderReceipt_ReturnsExpectedReceipt() {
        List<Pizza> pizzas = new ArrayList<>();
        pizzas.add(pizzasRepository.findById("3"));
        pizzas.add(pizzasRepository.findById("9"));

        Order expectedOrder = new Order("a2c3a740-3d3b-4a7b-9364-90ab83c0aa06", LocalDateTime.now(), pizzas, "38");

        String receipt = ordersService.generateOrderReceipt(expectedOrder);

        String expectedReceipt = "Квитанция № a2c3a740-3d3b-4a7b-9364-90ab83c0aa06\n" +
                "Позиции:" + "_".repeat(62) + "\n\n" +
                String.format("%8s%30s%8s%20s", "Позиция", "Название", "Цена", "ИД") + "\n" +
                String.format("%8d%30s%8.2f%20s", 1, "Margarita", 12.0, "3") + "\n" +
                String.format("%8d%30s%8.2f%20s", 2, "Salami", 15.5, "9") + "\n" +
                "_".repeat(70) + "\n" +
                String.format("%8s%30s%8.2f", "К оплате:", "", 27.5) + "€\n\n\n" +
                "Время заказа: \t" +
                expectedOrder.getDateTime().format(DateTimeFormatter.ofPattern("dd.MM.yyy HH:mm:ss"));
        assertEquals(expectedReceipt, receipt);
    }
}