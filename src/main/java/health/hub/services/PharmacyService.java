package health.hub.services;

import health.hub.entities.Pharmacy;
import health.hub.repositories.PharmacyRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;


public class PharmacyService {
    @Inject
    PharmacyRepository pharmacyRepository;


    public List<Pharmacy> getAllPharmacies() {
        return pharmacyRepository.getAll();
    }

    public List<Pharmacy> Listepharmacie() {
        LocalDateTime now = LocalDateTime.now();
        LocalTime currentTime = now.toLocalTime();
        DayOfWeek currentDay = now.getDayOfWeek();
        boolean isNightTime = currentTime.isAfter(LocalTime.of(23, 59)) || currentTime.isBefore(LocalTime.of(8, 0));
        boolean isSunday = currentDay == DayOfWeek.SUNDAY;

        return pharmacyRepository.getAll().stream()
                .filter(pharmacy -> {
                    boolean isOpenTonight = pharmacy.isOpenTonight();
                    return !(isNightTime || isSunday) || isOpenTonight;
                })
                .collect(Collectors.toList());
    }


    public String updateIsOpenTonight(Long pharmacyId, boolean isOpenTonight) {

        return pharmacyRepository.updateIsOpenTonight(pharmacyId, isOpenTonight);

    }

    public Pharmacy addPharmacy(Pharmacy request) {
        if (pharmacyRepository.existsByLocation(request.getLongitude(), request.getLatitude())) {
            throw new IllegalArgumentException("A pharmacy already exists at the provided location");
        }
        if (pharmacyRepository.existsByNameAndLocation(request.getName(), request.getLongitude(), request.getLatitude())) {
            throw new IllegalArgumentException("A pharmacy with the same name and location already exists");
        }
        return pharmacyRepository.add(request);
    }

    public Pharmacy getPharmacyById(Long id) {
        Pharmacy result = pharmacyRepository.getPharmacy(id);
        if (result == null) {
            throw new NotFoundException("Pharmacy not found with id: " + id);
        }

        return result;
    }

    public void deletePharmacyById(Long id) {
        Pharmacy pharmacy = pharmacyRepository.getPharmacy(id);
        if (pharmacy == null) {
            throw new NotFoundException("Pharmacy not found with id: " + id);
        }

        pharmacyRepository.delete(pharmacy);
    }

}
