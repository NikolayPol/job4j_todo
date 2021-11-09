package model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Класс Task описывает модель задания.
 *
 * @author Nikolay Polegaev
 * @version 3.0 09.11.2021
 */
@Entity
@Table(name = "task", schema = "todolist", catalog = "todo")
public class Task {
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private final String description;

    private final LocalDateTime created;

    private boolean done;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public Task() {
        this.id = 0;
        this.description = "description";
        this.created = LocalDateTime.now();
        this.done = false;
    }

    public Task(int id) {
        this.id = id;
        this.description = "";
        this.created = LocalDateTime.now();
        this.done = false;
    }

    public Task(String description) {
        this.id = 0;
        this.description = description;
        this.created = LocalDateTime.now();
        this.done = false;
    }

    public Task(int id, String description) {
        this.id = id;
        this.description = description;
        this.created = LocalDateTime.now();
        this.done = false;
    }

    public Task(String description, String categoryId, String categoryName) {
        this.id = 0;
        this.description = description;
        this.created = LocalDateTime.now();
        this.done = false;
        this.category = new Category(Integer.parseInt(categoryId), categoryName);
    }

    public Task(int id, String description, LocalDateTime created, Boolean done) {
        this.id = id;
        this.description = description;
        this.created = created;
        this.done = done;
    }

    public Task(int id, String description, LocalDateTime created, Boolean done, User user) {
        this.id = id;
        this.description = description;
        this.created = created;
        this.done = done;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Task task = (Task) o;
        return id == task.id
                && Objects.equals(description, task.description)
                && Objects.equals(created, task.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, created);
    }

    @Override
    public String toString() {
        return "Task{"
                + "id=" + id
                + ", description='" + description + '\''
                + ", created=" + FORMATTER.format(created)
                + ", done=" + done
                + '}';
    }
}
