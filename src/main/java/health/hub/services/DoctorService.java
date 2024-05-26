package health.hub.services;

import health.hub.entities.Cabinet;
import health.hub.entities.Doctor;
import health.hub.repositories.DoctorRepository;
import health.hub.requests.DoctorRequest;
import health.hub.responses.DoctorResponse;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

import java.util.ArrayList;
import java.util.List;

public class DoctorService {
    @Inject
    private DoctorRepository doctorRepository;

    public List<DoctorResponse> getAllDoctors() {
        List<Doctor> doctors = doctorRepository.getAll();
        List<DoctorResponse> responses = new ArrayList<>();
        for (Doctor doctor : doctors) {
            DoctorResponse response = DoctorResponse.builder()
                    .id(doctor.getId())
                    .name(doctor.getName())
                    .speciality(doctor.getSpeciality())
                    .schedule(doctor.getSchedule().toString()) // Convertir le planning en chaîne JSON
                    .cabinetId(doctor.getCabinet().getId())
                    .build();
            responses.add(response);
        }
        return responses;
    }

    public String addDoctor(DoctorRequest request) {
        // Vérifiez si un docteur avec le même nom existe déjà
        if (doctorRepository.findByName(request.getName()) != null) {
            throw new IllegalArgumentException("Un docteur avec le même nom existe déjà");
        }

        // Créer un nouvel objet Doctor
        Doctor doctor = Doctor.builder()
                .name(request.getName())
                .speciality(request.getSpeciality())
                .schedule(request.getScheduleMap())
                .build();

        // Ajouter le docteur
        doctorRepository.add(doctor);

        // Retourner un message de succès
        return "Docteur ajouté avec succès";
    }

    public DoctorResponse getDoctorById(Long id) {
        Doctor doctor = doctorRepository.getDoctor(id);
        if (doctor == null) {
            throw new NotFoundException("Docteur non trouvé avec l'id: " + id);
        }

        return DoctorResponse.builder()
                .id(doctor.getId())
                .name(doctor.getName())
                .speciality(doctor.getSpeciality())
                .schedule(doctor.getSchedule().toString()) // Convertir le planning en chaîne JSON
                .cabinetId(doctor.getCabinet().getId())
                .build();
    }

    public void deleteDoctorById(Long id) {
        Doctor doctor = doctorRepository.getDoctor(id);
        if (doctor == null) {
            throw new NotFoundException("Docteur non trouvé avec l'id: " + id);
        }

        doctorRepository.delete(doctor);
    }
}
