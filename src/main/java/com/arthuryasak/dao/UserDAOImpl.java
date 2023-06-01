package application.dao;

import application.exceptions.DAOException;
import application.models.User;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import application.utils.HibernateSessionFactoryUtil;

import java.util.List;
import java.util.function.Supplier;

public class UserDAOImpl implements UserDAO {

    public UserDAOImpl() {

    }

    @Override
    public void add(User user) throws DAOException {
        try (Session session = HibernateSessionFactoryUtil.getSession()) {
            withTransaction(session.beginTransaction(), () -> session.persist(user));
        }
    }

    @Override
    public List<User> getAll() throws DAOException {
        try (Session session = HibernateSessionFactoryUtil.getSession()) {
            Query<User> query = session.createQuery("from User", User.class);
            System.out.println("ALL users: \n" + query.list() + '\n');
            return query.list();
        }
    }

    @Override
    public User getById(Integer userId) throws DAOException {
        try (Session session = HibernateSessionFactoryUtil.getSession()) {
            User user = session.get(User.class, userId);
            return user;
        }
    }

    @Override
    public User update(User user) throws DAOException {
        try (Session session = HibernateSessionFactoryUtil.getSession()) {
            return withTransaction(session.beginTransaction(), () -> session.merge(user));
        }
    }

    @Override
    public void deleteAll() throws DAOException {
        try (Session session = HibernateSessionFactoryUtil.getSession()) {
            withTransaction(session.beginTransaction(), () -> {
                Query<User> query = session.createNativeQuery("DELETE FROM Users", User.class);
                query.executeUpdate();
            });
        }
    }

    @Override
    public void deleteById(Integer userId) throws DAOException {
        try (Session session = HibernateSessionFactoryUtil.getSession()) {
            withTransaction(session.beginTransaction(), () -> {
                User userForRemove = getById(userId);
                System.out.println("USER FOR DELETE: " + userForRemove);
                session.remove(userForRemove);
            });

            System.out.println("User with id: " + userId + " was DELETED \n");
        }
    }

    public User getFirstUser() throws DAOException {
        try (Session session = HibernateSessionFactoryUtil.getSession()) {
            Query<User> query = session.createQuery("from User", User.class);
            ScrollableResults<User> scroll = query.scroll();
            scroll.first();
            User firstUser = scroll.get();
            System.out.println("FIRST user is: " + firstUser + '\n');
            return firstUser;
        }
    }

    public User getLastUser() throws DAOException {
        try (Session session = HibernateSessionFactoryUtil.getSession()) {
            Query<User> query = session.createQuery("from User", User.class);
            ScrollableResults<User> scroll = query.scroll();
            scroll.last();
            User lastUser = scroll.get();
            System.out.println("LAST user is: " + lastUser + '\n');
            return lastUser;
        }
    }

    private <T> T withTransaction(Transaction transaction, Supplier<T> supplier) throws DAOException {
        try {
            T result = supplier.get();
            transaction.commit();
            return result;
        } catch(Exception e) {
            transaction.rollback();
            throw new DAOException("Error at transaction", e);
        }
    }

    private void withTransaction(Transaction transaction, Runnable runnable) throws DAOException {
        try {
            runnable.run();
            transaction.commit();
        } catch(Exception e) {
            transaction.rollback();
            throw new DAOException("Error at transaction", e);
        }
    }

}
