package health.hub.repositories;


import health.hub.entities.User;
import jakarta.persistence.*;

import java.util.List;

public class UserRepository {

    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("myUnit");
    private final EntityManager entityManager = entityManagerFactory.createEntityManager();

    public List<User> getAll() {
        String sql = "SELECT u FROM User u";
        TypedQuery<User> qr = entityManager.createQuery(sql, User.class);
        return qr.getResultList();
    }

    public User getUser(int id) {
        return entityManager.find(User.class, id);
    }

    public User findByUsername(String username) {
        try {
            return entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public User add(User user) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(user);
            entityManager.getTransaction().commit();
            return user;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        }
    }

    public User update(User user) {
        try {
            entityManager.getTransaction().begin();
            User updatedUser = entityManager.merge(user);
            entityManager.getTransaction().commit();
            return updatedUser;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        }
    }

    public void delete(User user) {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.contains(user) ? user : entityManager.merge(user));
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        }
    }
}
