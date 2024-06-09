//package health.hub.repositories;
//import health.hub.entities.Cabinet;
//import health.hub.requests.CabinetRequest;
//import health.hub.responses.CabinetResponse;
//import jakarta.persistence.*;
//import org.locationtech.jts.geom.Coordinate;
//
//import java.util.List;
//public class CabinetRepository {
//    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("myUnit");
//    EntityManager entityManager = entityManagerFactory.createEntityManager();
//
//    public List<Object[]> getAll() {
//        String jpql = "SELECT id, nom, ST_X(location) AS longitude, ST_Y(location) AS latitude FROM Cabinet";
//        Query query = entityManager.createNativeQuery(jpql);
//        return query.getResultList();
//    }
//
//
//    public String add(CabinetRequest request) {
//        try {
//            entityManager.getTransaction().begin();
//
//
//            // Build the SQL query with parameters
//            String sql = "INSERT INTO Cabinet (nom, location) " +
//                    "VALUES (?, ST_SetSRID(ST_MakePoint(?, ?), 4326))";
//
//            // Create a native SQL query
//            Query query = entityManager.createNativeQuery(sql);
//
//            // Set parameters
//            query.setParameter(1, request.getNom());
//            query.setParameter(2, request.getLongitude());
//            query.setParameter(3, request.getLatitude());
//
//            // Execute the query
//            query.executeUpdate();
//
//            entityManager.getTransaction().commit();
//
//            return "Cabinet added successfully";
//        } catch (Exception e) {
//            entityManager.getTransaction().rollback();
//            throw e;
//        } finally {
//            entityManager.close();
//        }
//    }
//
//    public boolean existsByLocation(double longitude, double latitude) {
//        String sql = "SELECT COUNT(*) FROM Cabinet WHERE ST_X(location) = ? AND ST_Y(location) = ?";
//        Query query = entityManager.createNativeQuery(sql);
//        query.setParameter(1, longitude);
//        query.setParameter(2, latitude);
//        Number count = (Number) query.getSingleResult();
//        return count.intValue() != 0;
//    }
//
//
//    public boolean existsByName(String nom) {
//        String jpql = "SELECT COUNT(c) FROM Cabinet c WHERE c.nom = :nom";
//        Query query = entityManager.createQuery(jpql);
//        query.setParameter("nom", nom);
//        Long count = (Long) query.getSingleResult();
//        return count != 0;
//    }
//    /////
//    public void deleteCabinetById(Long id) {
//        try {
//            entityManager.getTransaction().begin(); // Begin transaction
//
//            String sql = "DELETE FROM cabinet WHERE id = ?";
//            Query query = entityManager.createNativeQuery(sql);
//            query.setParameter(1, id);
//            query.executeUpdate();
//
//            entityManager.getTransaction().commit(); // Commit transaction
//        } catch (Exception e) {
//            if (entityManager.getTransaction().isActive()) {
//                entityManager.getTransaction().rollback(); // Rollback transaction if active
//            }
//            throw e; // Let the exception propagate
//        }
//    }
//    public Object[] getCabinetById(Long id) {
//        try {
//            String sql = "SELECT id, nom, ST_X(location) AS longitude, ST_Y(location) AS latitude FROM cabinet WHERE id = ?";
//            Query query = entityManager.createNativeQuery(sql);
//            query.setParameter(1, id);
//            return (Object[]) query.getSingleResult();
//        } catch (NoResultException e) {
//            // Handle the case where no result is found
//            return null;
//        }
//    }
//
//
//
//
//}
package health.hub.repositories;

import health.hub.entities.Cabinet;
import health.hub.requests.CabinetRequest;
import jakarta.persistence.*;

import java.util.List;

public class CabinetRepository {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("myUnit");
    EntityManager entityManager = entityManagerFactory.createEntityManager();

    public List<Object[]> getAll() {
        String jpql = "SELECT id, nom, ST_X(location) AS longitude, ST_Y(location) AS latitude, willaya, Moughataa FROM Cabinet";
        Query query = entityManager.createNativeQuery(jpql);
        return query.getResultList();
    }

    public String add(CabinetRequest request) {
        try {
            entityManager.getTransaction().begin();

            // Build the SQL query with parameters
            String sql = "INSERT INTO Cabinet (nom, location, willaya, Moughataa) " +
                    "VALUES (?, ST_SetSRID(ST_MakePoint(?, ?), 4326), ?, ?)";

            // Create a native SQL query
            Query query = entityManager.createNativeQuery(sql);

            // Set parameters
            query.setParameter(1, request.getNom());
            query.setParameter(2, request.getLongitude());
            query.setParameter(3, request.getLatitude());
            query.setParameter(4, request.getWillaya());
            query.setParameter(5, request.getMoughataa());

            // Execute the query
            query.executeUpdate();

            entityManager.getTransaction().commit();

            return "Cabinet added successfully";
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        } finally {
            entityManager.close();
        }
    }

    public boolean existsByLocation(double longitude, double latitude) {
        String sql = "SELECT COUNT(*) FROM Cabinet WHERE ST_X(location) = ? AND ST_Y(location) = ?";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, longitude);
        query.setParameter(2, latitude);
        Number count = (Number) query.getSingleResult();
        return count.intValue() != 0;
    }

    public boolean existsByName(String nom) {
        String jpql = "SELECT COUNT(c) FROM Cabinet c WHERE c.nom = :nom";
        Query query = entityManager.createQuery(jpql);
        query.setParameter("nom", nom);
        Long count = (Long) query.getSingleResult();
        return count != 0;
    }

    public void deleteCabinetById(Long id) {
        try {
            entityManager.getTransaction().begin();

            String sql = "DELETE FROM cabinet WHERE id = ?";
            Query query = entityManager.createNativeQuery(sql);
            query.setParameter(1, id);
            query.executeUpdate();

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        }
    }

    public Object[] getCabinetById(Long id) {
        try {
            String sql = "SELECT id, nom, ST_X(location) AS longitude, ST_Y(location) AS latitude, willaya, Moughataa FROM cabinet WHERE id = ?";
            Query query = entityManager.createNativeQuery(sql);
            query.setParameter(1, id);
            return (Object[]) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
