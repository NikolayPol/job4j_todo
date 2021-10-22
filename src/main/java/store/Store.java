package store;

import model.Task;

import java.util.List;

/**
 * Интерфейс Store описывает методы для взаимоействия с БД.
 *
 * @author Nikolay Polegaev
 * @version 1.0 05.10.2021
 */
public interface Store {
    Task add(Task task);

    boolean delete(int id);

    List<Task> findAll();

    Task findById(int id);

    List<Task> showFilterItems();

    boolean update(Task task);
}
