package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.Query;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    SessionFactory sessionFactory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "name VARCHAR(255)," +
                "lastname VARCHAR(255)," +
                "age TINYINT" +
                ")";

        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            Query query = session.createSQLQuery(sql).addEntity(User.class);
            query.executeUpdate();

            transaction.commit();
        } catch (Exception e) {
            System.out.println("Error creating the table!");
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            session.createSQLQuery("DROP TABLE IF EXISTS User").executeUpdate();

            transaction.commit();
        } catch (Exception e) {
            System.out.println("Error drop the table!");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            session.save(new User(name, lastName, age));

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Error save user!");
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            User user = session.get(User.class, id);
            if (user == null) {
                throw new IllegalArgumentException("User with id " + id + "does not exist!");
            }

            session.remove(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Error remove user by id!");
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from User", User.class).list();
        }
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("TRUNCATE TABLE users").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
