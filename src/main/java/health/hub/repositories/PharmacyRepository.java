package health.hub.repositories;

import health.hub.entities.Pharmacy;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class PharmacyRepository {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("myUnit");
    EntityManager entityManager = entityManagerFactory.createEntityManager();

    public List<Pharmacy> getAll() {
        String sql = "select p from Pharmacy p ";
        TypedQuery<Pharmacy> qr = entityManager.createQuery(sql, Pharmacy.class);
        return qr.getResultList();
    }
}
