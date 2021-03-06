package store;

import model.Category;
import model.Task;
import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

/**
 * Класс HbrStore реализует взаимодействие с БД через Hibernate.
 *
 * @author Nikolay Polegaev
 * @version 2.0 23.10.2021
 */
public class HbrStore implements Store, AutoCloseable {
    private static final Logger LOG = LoggerFactory
            .getLogger(store.HbrStore.class.getName());
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure()
            .build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata()
            .buildSessionFactory();

    private static final class Lazy {
        private static final Store INST = new store.HbrStore();
    }

    public static Store instOf() {
        return store.HbrStore.Lazy.INST;
    }

    @Override
    public Task add(Task task) {
        try (Session session = sf.withOptions()
                .jdbcTimeZone(TimeZone.getTimeZone("UTC")).openSession()) {
            session.beginTransaction();
            session.save(task);
            session.getTransaction().commit();
        } catch (Exception e) {
            sf.getCurrentSession().getTransaction().rollback();
            LOG.error(e.getMessage(), e);
        }
        return task;
    }

    @Override
    public boolean update(Task task) {
        boolean result = false;
        try (Session session = sf.withOptions()
                .jdbcTimeZone(TimeZone.getTimeZone("UTC")).openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("UPDATE Task SET done = :done1 where id = :id");
            query.setParameter("done1", task.getDone());
            query.setParameter("id", task.getId());
            query.executeUpdate();
            session.getTransaction().commit();
            result = true;
        } catch (Exception e) {
            sf.getCurrentSession().getTransaction().rollback();
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public boolean delete(int id) {
        boolean result = false;
        try (Session session = sf.withOptions()
                .jdbcTimeZone(TimeZone.getTimeZone("UTC")).openSession()) {
            session.beginTransaction();
            Task task = new Task();
            task.setId(id);
            session.delete(task);
            session.getTransaction().commit();
            result = true;
        } catch (Exception e) {
            sf.getCurrentSession().getTransaction().rollback();
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public List<Task> findAll() {
        List<Task> result = new ArrayList<>();
        try (Session session = sf.withOptions()
                .jdbcTimeZone(TimeZone.getTimeZone("UTC")).openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("FROM Task");
            result = query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            sf.getCurrentSession().getTransaction().rollback();
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public List<Task> findAll(User user) {
        List<Task> result = new ArrayList<>();
        try (Session session = sf.withOptions()
                .jdbcTimeZone(TimeZone.getTimeZone("UTC")).openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("FROM Task WHERE user.username =: user1");
            query.setParameter("user1", user.getUsername());
            result = query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            sf.getCurrentSession().getTransaction().rollback();
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public Task findById(int id) {
        Task task = null;
        try (Session session = sf.withOptions()
                .jdbcTimeZone(TimeZone.getTimeZone("UTC")).openSession()) {
            session.beginTransaction();
            task = session.get(Task.class, id);
            session.getTransaction().commit();
        } catch (Exception e) {
            sf.getCurrentSession().getTransaction().rollback();
            LOG.error(e.getMessage(), e);
        }
        return task;
    }

    @Override
    public List<Task> showFilterItems(User user) {
        List<Task> result = new ArrayList<>();
        try (Session session = sf.withOptions()
                .jdbcTimeZone(TimeZone.getTimeZone("UTC")).openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("FROM Task WHERE done = false "
                    + "and user.username =: user1");
            query.setParameter("user1", user.getUsername());
            result = query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            sf.getCurrentSession().getTransaction().rollback();
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public void close() {
        StandardServiceRegistryBuilder.destroy(registry);
    }

    @Override
    public void addUser(User user) {
        try (Session session = sf.withOptions()
                .jdbcTimeZone(TimeZone.getTimeZone("UTC")).openSession()) {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            sf.getCurrentSession().getTransaction().rollback();
            LOG.error(e.getMessage(), e);
        }
    }

    @Override
    public User findUserByEmail(String email) {
        User user = null;
        try (Session session = sf.withOptions()
                .jdbcTimeZone(TimeZone.getTimeZone("UTC")).openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("FROM User WHERE email = :email1");
            query.setParameter("email1", email).uniqueResult();
            user = session.get(User.class, email);
            session.getTransaction().commit();
        } catch (Exception e) {
            sf.getCurrentSession().getTransaction().rollback();
            LOG.error(e.getMessage(), e);
        }
        return user;
    }

    @Override
    public List<Category> showCategories() {
        List<Category> result = new ArrayList<>();
        try (Session session = sf.withOptions()
                .jdbcTimeZone(TimeZone.getTimeZone("UTC")).openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("FROM Category");
            result = query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            sf.getCurrentSession().getTransaction().rollback();
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * Ручное тестирвание HbmTracker
     */
    public static void main(String[] args) {
        store.HbrStore hb = new store.HbrStore();
        hb.add(new Task(1, "Выучить js"));
        for (Task task : hb.findAll()) {
            System.out.println(task);
        }
    }
}
