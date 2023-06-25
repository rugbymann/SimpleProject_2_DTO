package de.ait.services;

import de.ait.models.Pizza;
import de.ait.repositories.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class PizzasServiceImplTest {
    private PizzasRepository pizzasRepository;
    private PizzasService pizzasService;

    @BeforeEach
    public void setUp() {

        pizzasRepository = new PizzasRepositoryListImpl();
        pizzasService = new PizzasServiceImpl(pizzasRepository);

    }
    @Test
    void save() {
        Pizza pizza = new Pizza("1", "Margarita", "SMALL", 6.00);

        pizzasService.save(pizza);

        Pizza savedPizza = pizzasRepository.findById(pizza.getId());

        Assertions.assertEquals(pizza, savedPizza, "Pizza should be saved successfully.");
    }

    @Test
    void remove() {
        Pizza pizza = new Pizza("1", "Margarita", "SMALL", 6.00);
        pizzasRepository.save(pizza);

        pizzasService.remove(pizza);

        Pizza removedPizza = pizzasRepository.findById(pizza.getId());

        Assertions.assertNull(removedPizza, "Pizza should be removed successfully.");
    }

    @Test
    void getAll() {
        Pizza pizza1 = new Pizza("1", "Margarita", "SMALL", 6.00);
        Pizza pizza2 = new Pizza("1", "Margarita", "SMALL", 6.00);
        pizzasRepository.save(pizza1);
        pizzasRepository.save(pizza2);

        List<Pizza> pizzas = pizzasService.getAll();

        Assertions.assertEquals(2, pizzas.size(), "Pizza list should contain 2 pizzas.");
        Assertions.assertTrue(pizzas.contains(pizza1), "Pizza list should contain pizza1.");
        Assertions.assertTrue(pizzas.contains(pizza2), "Pizza list should contain pizza2.");
    }
}

