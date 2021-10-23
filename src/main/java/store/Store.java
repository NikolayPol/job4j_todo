package store;

import model.Task;
import model.User;

import java.util.List;

/**
 * Интерфейс Store описывает методы для взаимоействия с БД.
 *
 * @author Nikolay Polegaev
 * @version 2.0 23.10.2021
 */
public interface Store {
    Task add(Task task);

    boolean delete(int id);

    List<Task> findAll();

    List<Task> findAll(User user);

    Task findById(int id);

    List<Task> showFilterItems(User user);

    boolean update(Task task);

    void addUser(User user);

    User findUserByEmail(String email);
}
