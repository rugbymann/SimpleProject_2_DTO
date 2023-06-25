package de.ait.repositories;
import java.util.List;

public interface CrudRepository<T>{
    void save(T model);
    void remove(T model);
    List<T> getAll();
    T findById(String id);
}

