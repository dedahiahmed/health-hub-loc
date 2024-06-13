package health.hub.services;

import health.hub.entities.Doctor;
import health.hub.entities.User;
import health.hub.repositories.DoctorRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.util.List;

public class DoctorService {
    @Inject
    private DoctorRepository doctorRepository;

    public List<Doctor> getAllDoctors() {
        return doctorRepository.getAll();
    }

    public String addDoctor(Doctor doctor) {
        // Vérifiez si un docteur avec le même nom existe déjà
        if (doctorRepository.findByName(doctor.getName()) != null) {
            throw new IllegalArgumentException("Un docteur avec le même nom existe déjà");
        }
        doctorRepository.add(doctor);

        // Retourner un message de succès
        return "Docteur ajouté avec succès";
    }

    public Doctor getDoctorById(Long id) {
        Doctor doctor = doctorRepository.getDoctor(id);
        if (doctor == null) {
            throw new NotFoundException("Docteur non trouvé avec l'id: " + id);
        }

        return doctor;
    }

    public Doctor updateDoctor(Long id, Doctor doctorDetails) {
        Doctor existingDoctor = doctorRepository.getDoctor(id);
        if (existingDoctor == null) {
            throw new NotFoundException("Doctor not found with id " + id);
        }

        existingDoctor.setName(doctorDetails.getName());
        existingDoctor.setSpeciality(doctorDetails.getSpeciality());
        existingDoctor.setSchedule(doctorDetails.getSchedule());
        existingDoctor.setCabinet(doctorDetails.getCabinet());

        return doctorRepository.update(existingDoctor);
    }
    public void deleteDoctorById(Long id) {
        Doctor doctor = doctorRepository.getDoctor(id);
        if (doctor == null) {
            throw new NotFoundException("Docteur non trouvé avec l'id: " + id);
        }

        doctorRepository.delete(doctor);
    }

    public List<Doctor> getDoctorsByCabinet(Long cabinetId) {
        return doctorRepository.getDoctorsByCabinet(cabinetId);
    }
}
