package de.ait.services;

import de.ait.models.Pizza;

import java.util.List;

public interface PizzasService {
    List<Pizza>getAll();
    void save(Pizza pizza);
    void remove(Pizza pizza);
}
