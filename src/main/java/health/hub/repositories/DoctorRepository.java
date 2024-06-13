package health.hub.repositories;

import health.hub.entities.Doctor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class DoctorRepository {
    private final EntityManagerFactory entityManagerFactory;
    private final EntityManager entityManager;

    public DoctorRepository() {
        entityManagerFactory = Persistence.createEntityManagerFactory("myUnit");
        entityManager = entityManagerFactory.createEntityManager();
    }

    public List<Doctor> getAll() {
        String sql = "SELECT d FROM Doctor d";
        TypedQuery<Doctor> query = entityManager.createQuery(sql, Doctor.class);
        return query.getResultList();
    }

    public Doctor getDoctor(Long id) {
        return entityManager.find(Doctor.class, id);
    }

    public Doctor findByName(String name) {
        try {
            return entityManager.createQuery("SELECT d FROM Doctor d WHERE d.name = :name", Doctor.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Doctor add(Doctor doctor) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(doctor);
            entityManager.getTransaction().commit();
            return doctor;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        }
    }

    public Doctor update(Doctor doctor) {
        try {
            entityManager.getTransaction().begin();
            Doctor updatedDoctor = entityManager.merge(doctor);
            entityManager.getTransaction().commit();
            return updatedDoctor;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        }
    }

    public void delete(Doctor doctor) {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.contains(doctor) ? doctor : entityManager.merge(doctor));
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        }
    }

    public List<Doctor> getDoctorsByCabinet(Long cabinetId) {
        String jpql = "SELECT d FROM Doctor d WHERE d.cabinet.id = :cabinetId";
        TypedQuery<Doctor> query = entityManager.createQuery(jpql, Doctor.class);
        query.setParameter("cabinetId", cabinetId);
        return query.getResultList();
    }
}
