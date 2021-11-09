package store;

import model.Category;
import model.Task;
import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Function;

/**
 * Класс HbrStoreWrapper реализует шаблон Wrapper(Обертка).
 *
 * @author Nikolay Polegaev
 * @version 2.0 23.10.2021
 */
public class HbrStoreWrapper implements Store, AutoCloseable {
    private static final Logger LOG = LoggerFactory
            .getLogger(store.HbrStoreWrapper.class.getName());
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure()
            .build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata()
            .buildSessionFactory();

    private static final class Lazy {
        private static final Store INST = new store.HbrStoreWrapper();
    }

    public static Store instOf() {
        return store.HbrStoreWrapper.Lazy.INST;
    }

    private <T> T tx(final Function<Session, T> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public Task add(Task task) {
        return (Task) this.tx(
                session -> session.save(task)
        );
    }

    @Override
    public boolean update(Task task) {
        return this.tx(
                session -> {
                    boolean result = false;
                    Query query = session.createQuery(
                            "UPDATE Task SET done = :done1 where id = :id");
                    query.setParameter("done1", task.getDone());
                    query.setParameter("id", task.getId());
                    query.executeUpdate();
                    result = true;
                    return result;
                }
        );
    }

    @Override
    public boolean delete(int id) {
        return this.tx(
                session -> {
                    boolean result = false;
                    Task task = new Task();
                    task.setId(id);
                    session.delete(task);
                    result = true;
                    return result;
                }
        );
    }

    @Override
    public List<Task> findAll() {
        return this.tx(
                session -> {
                    List<Task> result;
                    Query query = session.createQuery("FROM Task");
                    result = query.list();
                    return result;
                }
        );
    }

    @Override
    public List<Task> findAll(User user) {
        return this.tx(
                session -> {
                    List<Task> result;
                    Query query = session.createQuery("FROM Task WHERE user.username =: user1");
                    query.setParameter("user1", user.getUsername());
                    result = query.list();
                    return result;
                }
        );
    }

    @Override
    public Task findById(int id) {
        return this.tx(
                session -> {
                    Task task = session.get(Task.class, id);
                    return task;
                }
        );
    }

    @Override
    public List<Task> showFilterItems(User user) {
        return this.tx(
                session -> {
                    List<Task> result;
                    Query query = session.createQuery("FROM Task WHERE done = false "
                            + "and user.username =: user1");
                    query.setParameter("user1", user.getUsername());
                    result = query.list();
                    return result;
                }
        );
    }

    @Override
    public void addUser(User user) {
        this.tx(
                session -> session.save(user)
        );
    }

    @Override
    public User findUserByEmail(String email) {
        return (User) this.tx(
                session -> session
                        .createQuery("FROM User WHERE email = :email1")
                        .setParameter("email1", email)
                        .uniqueResult()
        );
    }

    @Override
    public List<Category> showCategories() {
        return this.tx(
                session -> {
                    List<Category> result;
                    Query query = session.createQuery("FROM Category");
                    result = query.list();
                    return result;
                }
        );
    }

    @Override
    public void close() {
        StandardServiceRegistryBuilder.destroy(registry);
    }

    /**
     * Ручное тестирвание HbmStoreWrapper
     */
    public static void main(String[] args) {
        store.HbrStoreWrapper hb = new store.HbrStoreWrapper();
        hb.add(new Task(1, "Выучить js"));
        for (Task task : hb.findAll()) {
            System.out.println(task);
        }
    }
}
