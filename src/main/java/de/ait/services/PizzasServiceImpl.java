package de.ait.services;

import de.ait.models.Pizza;
import de.ait.repositories.PizzasRepository;

import java.util.List;

public class PizzasServiceImpl implements PizzasService {
    private final PizzasRepository pizzasRepository;

    public PizzasServiceImpl(PizzasRepository pizzasRepository) {
        this.pizzasRepository = pizzasRepository;
    }


    @Override
    public void save(Pizza pizza) {
        pizzasRepository.save(pizza);
    }

    @Override
    public void remove(Pizza pizza) {
        pizzasRepository.remove(pizza);
    }

    @Override
    public List<Pizza> getAll() {
        return pizzasRepository.getAll();
    }

}
