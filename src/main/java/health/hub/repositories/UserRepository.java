package health.hub.repositories;


import health.hub.entities.Role;
import health.hub.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class UserRepository {

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("myUnit");
    EntityManager entityManager = entityManagerFactory.createEntityManager();

    public List<User> getAll() {
        String sql = "select u from User u ";
        TypedQuery<User> qr = entityManager.createQuery(sql, User.class);
        return qr.getResultList();
    }

    public User add(User user) {
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
        return user;
    }

    public boolean checkCredentials(String username, String password) {
        String sql = "SELECT COUNT(u) FROM User u WHERE u.username = :username AND u.password = :password";
        TypedQuery<Long> query = entityManager.createQuery(sql, Long.class);
        query.setParameter("username", username);
        query.setParameter("password", password);
        Long count = query.getSingleResult();
        return count > 0;
    }

    public Role getRoleByUsername(String username) {
        String sql = "SELECT u.role FROM User u WHERE u.username = :username";
        TypedQuery<Role> query = entityManager.createQuery(sql, Role.class);
        query.setParameter("username", username);
        List<Role> resultList = query.getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }

}
