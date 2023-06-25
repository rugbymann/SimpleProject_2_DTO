package de.ait.repositories;

import de.ait.models.Pizza;

import java.util.ArrayList;
import java.util.List;


public class PizzasRepositoryListImpl implements PizzasRepository {
    private List<Pizza> pizzas;


    public PizzasRepositoryListImpl() {
        pizzas = new ArrayList<>();
        initializePizzas();
    }

    private void initializePizzas() {
    }

    @Override
    public List<Pizza> getAll() {
        return pizzas;
    }

    @Override
    public void remove(Pizza pizza) {
        pizzas.remove(pizza);
    }

    @Override
    public void save(Pizza pizza) {
        pizzas.add(pizza);
    }

    @Override
    public Pizza findById(String id) {
        for (Pizza pizza : pizzas) {
            if (pizza.getId().equals(id)) {
                return pizza;
            }
        }
        return null;
    }

    @Override
    public Pizza findByTitle(String title) {
        for (Pizza pizza : pizzas) {
            if (pizza.getTitle().equals(title)) {
                return pizza;
            }
        }
        return null;
    }
}
