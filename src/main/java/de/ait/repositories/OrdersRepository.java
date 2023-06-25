package de.ait.repositories;
import de.ait.models.Order;

public interface OrdersRepository extends CrudRepository<Order> {
    int getQuantity(Order order);

    void save(Order order);
}

