package health.hub.repositories;

import health.hub.entities.Pharmacy;
import health.hub.requests.PharmacyRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;

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

            // Print the request
            System.out.println("Received Pharmacy Request: " + request);

            // Create a Coordinate object using longitude and latitude
            Coordinate coordinate = new Coordinate(request.getLongitude(), request.getLatitude());

            // Create a Point geometry using the Coordinate
            GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326); // 4326 is the SRID for WGS 84
            Point location = geometryFactory.createPoint(coordinate);


            // Build the pharmacy entity using the builder pattern
            Pharmacy pharmacy = Pharmacy.builder()
                    .name(request.getName())
                    .location(location)
                    .isOpenTonight(request.isOpenTonight())
                    .build();

            // Persist the pharmacy entity
            entityManager.persist(pharmacy);
            entityManager.getTransaction().commit();

            // Return a success message
            return "Pharmacy added successfully";
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        } finally {
            entityManager.close();
        }
    }


}
