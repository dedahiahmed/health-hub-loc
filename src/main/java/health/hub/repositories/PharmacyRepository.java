package health.hub.repositories;

import health.hub.entities.Pharmacy;
import jakarta.persistence.*;

import java.util.List;
import java.util.Optional;

public class PharmacyRepository {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("myUnit");
    EntityManager entityManager = entityManagerFactory.createEntityManager();

    public List<Pharmacy> getAll() {
        String sql = "SELECT p FROM Pharmacy p";
        TypedQuery<Pharmacy> query = entityManager.createQuery(sql, Pharmacy.class);
        return query.getResultList();
    }

    public Optional<Pharmacy> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Pharmacy.class, id));
    }

    public Pharmacy add(Pharmacy pharmacy) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(pharmacy);
            entityManager.getTransaction().commit();
            return pharmacy;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        }
    }

    public Pharmacy update(Pharmacy pharmacy) {
        entityManager.getTransaction().begin();
        Pharmacy updatedPharmacy = entityManager.merge(pharmacy);
        entityManager.getTransaction().commit();
        return updatedPharmacy;
    }

    public boolean existsByLocation(double longitude, double latitude) {
        String jpql = "SELECT COUNT(p) FROM Pharmacy p WHERE p.longitude = :longitude AND p.latitude = :latitude";
        TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
        query.setParameter("longitude", longitude);
        query.setParameter("latitude", latitude);
        Long count = query.getSingleResult();
        return count != 0;
    }

    public boolean existsByName(String name) {
        String jpql = "SELECT COUNT(p) FROM Pharmacy p WHERE p.name = :name";
        TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
        query.setParameter("name", name);
        Long count = query.getSingleResult();
        return count != 0;
    }

    public boolean existsByNameAndLocation(String name, double longitude, double latitude) {
        String jpql = "SELECT COUNT(p) FROM Pharmacy p WHERE p.name = :name AND p.longitude = :longitude AND p.latitude = :latitude";
        TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
        query.setParameter("name", name);
        query.setParameter("longitude", longitude);
        query.setParameter("latitude", latitude);
        Long count = query.getSingleResult();
        return count != 0;
    }

    public Pharmacy getPharmacy(Long id) {
        return entityManager.find(Pharmacy.class, id);
    }

    public void delete(Pharmacy pharmacy) {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.contains(pharmacy) ? pharmacy : entityManager.merge(pharmacy));
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        }
    }

    public String updateIsOpenTonight(Long id, boolean isOpenTonight) {
        try {
            entityManager.getTransaction().begin();

            String sql = "UPDATE pharmacy SET is_open_tonight = ? WHERE id = ?";
            Query query = entityManager.createNativeQuery(sql);
            query.setParameter(1, isOpenTonight);
            query.setParameter(2, id);

            int updatedRows = query.executeUpdate();
            
            entityManager.getTransaction().commit();

            if (updatedRows == 0) {
                return "Pharmacy with ID " + id + " not found.";
            }

            return "Successfully updated isOpenTonight.";
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        }
    }


}
