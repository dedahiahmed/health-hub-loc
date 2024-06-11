package health.hub.repositories;

import health.hub.requests.PharmacyRequest;
import jakarta.persistence.*;

import java.util.List;

public class PharmacyRepository {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("myUnit");
    EntityManager entityManager = entityManagerFactory.createEntityManager();

    public List<Object[]> getAll() {
        String jpql = "SELECT id, name, ST_X(location) AS longitude, ST_Y(location) AS latitude, is_open_tonight FROM pharmacy";
        Query query = entityManager.createNativeQuery(jpql);
        return query.getResultList();
    }


    public String add(PharmacyRequest request) {
        try {
            entityManager.getTransaction().begin();


            // Build the SQL query with parameters
            String sql = "INSERT INTO pharmacy (name, location, is_open_tonight) " +
                    "VALUES (?, ST_SetSRID(ST_MakePoint(?, ?), 4326), ?)";

            // Create a native SQL query
            Query query = entityManager.createNativeQuery(sql);

            // Set parameters
            query.setParameter(1, request.getName());
            query.setParameter(2, request.getLongitude());
            query.setParameter(3, request.getLatitude());
            query.setParameter(4, request.isOpenTonight());

            // Execute the query
            query.executeUpdate();

            entityManager.getTransaction().commit();

            return "Pharmacy added successfully";
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        } finally {
            entityManager.close();
        }
    }

    public boolean existsByLocation(double longitude, double latitude) {
        String sql = "SELECT COUNT(*) FROM pharmacy WHERE ST_X(location) = ? AND ST_Y(location) = ?";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, longitude);
        query.setParameter(2, latitude);
        Number count = (Number) query.getSingleResult();
        return count.intValue() != 0;
    }


    public boolean existsByName(String name) {
        String jpql = "SELECT COUNT(p) FROM Pharmacy p WHERE p.name = :name";
        Query query = entityManager.createQuery(jpql);
        query.setParameter("name", name);
        Long count = (Long) query.getSingleResult();
        return count != 0;
    }

    public Object[] getPharmacyById(Long id) {
        try {
            String sql = "SELECT id, name, ST_X(location) AS longitude, ST_Y(location) AS latitude, is_open_tonight FROM pharmacy WHERE id = ?";
            Query query = entityManager.createNativeQuery(sql);
            query.setParameter(1, id);
            return (Object[]) query.getSingleResult();
        } catch (NoResultException e) {
            // Handle the case where no result is found
            return null;
        } finally {
            entityManager.close();
        }
    }

    public void deletePharmacyById(Long id) {
        try {
            entityManager.getTransaction().begin(); // Begin transaction

            String sql = "DELETE FROM pharmacy WHERE id = ?";
            Query query = entityManager.createNativeQuery(sql);
            query.setParameter(1, id);
            query.executeUpdate();

            entityManager.getTransaction().commit(); // Commit transaction
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback(); // Rollback transaction if active
            }
            throw e; // Let the exception propagate
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
