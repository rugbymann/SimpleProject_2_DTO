package de.ait.services;

import de.ait.models.Order;
import de.ait.models.Pizza;
import de.ait.models.User;
import de.ait.repositories.OrdersRepository;
import de.ait.repositories.PizzasRepository;
import de.ait.repositories.UsersRepository;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class OrdersServiceImpl implements OrdersService {
    private UsersRepository usersRepository;
    private PizzasRepository pizzasRepository;
    private OrdersRepository ordersRepository;

    public OrdersServiceImpl(UsersRepository usersRepository,
                             PizzasRepository pizzasRepository,
                             OrdersRepository ordersRepository) {
        this.usersRepository = usersRepository;
        this.pizzasRepository = pizzasRepository;
        this.ordersRepository = ordersRepository;
    }

    @Override

    public Order makeOrder(String email, List<Pizza> pizzas) {
        User user = usersRepository.findByEmail(email);
        if (user == null) {
            throw new IllegalArgumentException("Пользователь не найден");
        }

        for (Pizza pizza : pizzas) {
            checkPizza(pizza);
        }

        Order order = new Order(user.getId(), pizzas);
        ordersRepository.save(order);

        return order;
    }

    @Override
    public double calculateTotalPrice(Order order) {
        double totalPrice = 0;
        for (Pizza pizza : order.getPizzas()) {
            totalPrice += pizza.getPrice();
        }
        return totalPrice;
    }

    private void checkPizza(Pizza pizza) {
        Pizza pizzaFromTxt = pizzasRepository.findById(pizza.getId());

        if (pizzaFromTxt == null) {
            throw new IllegalArgumentException("Пицца не найдена");
        }
    }

    public String generateOrderReceipt(Order order) {
        double totalPrice = calculateTotalPrice(order);

        StringBuilder receiptBuilder = new StringBuilder();
        receiptBuilder.append("Квитанция № ")
                .append(order.getId())
                .append("\n")
                .append("Позиции:")
                .append("_".repeat(62))
                .append("\n")
                .append("\n")
                .append(String.format("%8s%30s%8s%20s", "Позиция", "Название", "Цена", "ИД"))
                .append("\n");

        List<Pizza> orderedPizzas = order.getPizzas();
        for (int i = 0; i < orderedPizzas.size(); i++) {
            Pizza orderedPizza = orderedPizzas.get(i);
            int posNumber = i + 1;
            String pizzaTitle = orderedPizza.getTitle();
            double pizzaPrice = orderedPizza.getPrice();
            String pizzaId = orderedPizza.getId();
            String pos = String.format("%8d%30s%8.2f%20s", posNumber, pizzaTitle, pizzaPrice, pizzaId);
            receiptBuilder.append(pos);
            receiptBuilder.append("\n");
        }
        receiptBuilder.append("_".repeat(70))
                .append("\n")
                .append(String.format("%8s%30s%8.2f", "К оплате:", "", totalPrice))
                .append("€")
                .append("\n".repeat(3))
                .append("Время заказа: \t")
                .append(order.getDateTime().format(DateTimeFormatter.ofPattern("dd.MM.yyy HH:mm:ss")));

        return receiptBuilder.toString();
    }
}
