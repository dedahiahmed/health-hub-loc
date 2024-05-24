package health.hub.repositories;
import health.hub.entities.Cabinet;
import health.hub.requests.CabinetRequest;
import health.hub.responses.CabinetResponse;
import jakarta.persistence.*;
import org.locationtech.jts.geom.Coordinate;

import java.util.List;
public class CabinetRepository {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("myUnit");
    EntityManager entityManager = entityManagerFactory.createEntityManager();

    public List<Object[]> getAll() {
        String jpql = "SELECT id, nom, ST_X(location) AS longitude, ST_Y(location) AS latitude FROM Cabinet";
        Query query = entityManager.createNativeQuery(jpql);
        return query.getResultList();
    }


    public String add(CabinetRequest request) {
        try {
            entityManager.getTransaction().begin();


            // Build the SQL query with parameters
            String sql = "INSERT INTO Cabinet (nom, location) " +
                    "VALUES (?, ST_SetSRID(ST_MakePoint(?, ?), 4326))";

            // Create a native SQL query
            Query query = entityManager.createNativeQuery(sql);

            // Set parameters
            query.setParameter(1, request.getNom());
            query.setParameter(2, request.getLongitude());
            query.setParameter(3, request.getLatitude());

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
    /////
    public void deleteCabinetById(Long id) {
        try {
            entityManager.getTransaction().begin();

            // Récupérer l'entité Cabinet par son identifiant
            Cabinet cabinet = entityManager.find(Cabinet.class, id);

            if (cabinet != null) {
                // Supprimer l'entité
                entityManager.remove(cabinet);
            }

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        }
    }
    public Object[] getCabinetById(Long id) {
        String jpql = "SELECT new health.hub.responses.CabinetResponse(c.id, c.nom, ST_X(c.location), ST_Y(c.location)) FROM Cabinet c WHERE c.id = :id";
        Query query = entityManager.createQuery(jpql);
        query.setParameter("id", id);
        try {
            return (Object[]) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    /////
//    public void update(CabinetRequest request) {
//        try {
//            entityManager.getTransaction().begin();
//
//            // Récupérer l'entité Cabinet par ses coordonnées
//            String sql = "SELECT c FROM Cabinet c WHERE ST_X(c.location) = :longitude AND ST_Y(c.location) = :latitude";
//            Query query = entityManager.createQuery(sql);
//            query.setParameter("longitude", request.getLongitude());
//            query.setParameter("latitude", request.getLatitude());
//            Cabinet cabinet = (Cabinet) query.getSingleResult();
//
//            if (cabinet != null) {
//                // Mettre à jour les propriétés de l'entité
//                cabinet.setNom(request.getNom());
//                cabinet.setLocation(geometryFactory.createPoint(new Coordinate(request.getLongitude(), request.getLatitude())));
//
//                // Mettre à jour l'entité dans la base de données
//                entityManager.merge(cabinet);
//            }
//
//            entityManager.getTransaction().commit();
//        } catch (Exception e) {
//            entityManager.getTransaction().rollback();
//            throw e;
//        }
//    }

}
