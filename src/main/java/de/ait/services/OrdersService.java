package de.ait.services;

import de.ait.models.Order;
import de.ait.models.Pizza;

import java.util.List;

public interface OrdersService {
    Order makeOrder(String email, List<Pizza> pizzas);

    double calculateTotalPrice(Order order);

    public String generateOrderReceipt(Order order);
}
