package de.ait.repositories;
import de.ait.models.Pizza;

public interface PizzasRepository extends CrudRepository<Pizza> {
    Pizza findByTitle(String title);

}
