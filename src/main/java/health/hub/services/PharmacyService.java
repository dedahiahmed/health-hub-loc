package health.hub.services;

import health.hub.repositories.PharmacyRepository;
import health.hub.requests.PharmacyRequest;
import health.hub.responses.PharmacyResponse;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


public class PharmacyService {
    @Inject
    PharmacyRepository pharmacyRepository;


    public List<PharmacyResponse> getAllPharmacies() {
        List<Object[]> results = pharmacyRepository.getAll();
        List<PharmacyResponse> responses = new ArrayList<>();
        for (Object[] row : results) {
            Long id = ((Number) row[0]).longValue();
            String name = (String) row[1];
            Double longitude = ((Number) row[2]).doubleValue();
            Double latitude = ((Number) row[3]).doubleValue();
            String willaya = ((String) row[4]);
            String moughataa = ((String) row[5]);
            String img = ((String) row[6]);
            boolean isOpenTonight = (boolean) row[7];

            PharmacyResponse response = PharmacyResponse.builder()
                    .id(id)
                    .name(name)
                    .longitude(longitude)
                    .latitude(latitude)
                    .willaya(willaya)
                    .moughataa(moughataa)
                    .img(img)
                    .isOpenTonight(isOpenTonight)
                    .build();

            responses.add(response);
        }
        return responses;
    }

    public List<PharmacyResponse> Listepharmacie() {
        LocalDateTime now = LocalDateTime.now();
        LocalTime currentTime = now.toLocalTime();
        DayOfWeek currentDay = now.getDayOfWeek();
        System.out.println(now);
        boolean isNightTime = currentTime.isAfter(LocalTime.of(23, 59)) || currentTime.isBefore(LocalTime.of(8, 0));
        boolean isSunday = currentDay == DayOfWeek.FRIDAY;

        List<Object[]> results = pharmacyRepository.getAll();
        List<PharmacyResponse> responses = new ArrayList<>();

        for (Object[] row : results) {
            Long id = ((Number) row[0]).longValue();
            String name = (String) row[1];
            Double longitude = ((Number) row[2]).doubleValue();
            Double latitude = ((Number) row[3]).doubleValue();
            String willaya = ((String) row[4]);
            String moughataa = ((String) row[5]);
            String img = ((String) row[6]);
            boolean isOpenTonight = (boolean) row[7];


            // Filtrer les pharmacies en fonction des conditions
            if ((isNightTime || isSunday) && !isOpenTonight) {
                continue;
            }


            PharmacyResponse response = PharmacyResponse.builder()
                    .id(id)
                    .name(name)
                    .longitude(longitude)
                    .latitude(latitude)
                    .willaya(willaya)
                    .moughataa(moughataa)
                    .img(img)
                    .isOpenTonight(isOpenTonight)
                    .build();

            responses.add(response);
        }
        return responses;
    }


    public String updateIsOpenTonight(Long pharmacyId, boolean isOpenTonight) {

        return pharmacyRepository.updateIsOpenTonight(pharmacyId, isOpenTonight);

    }

    public String addPharmacy(PharmacyRequest request) {
        // Check if a pharmacy already exists at the provided location
        if (pharmacyRepository.existsByLocation(request.getLongitude(), request.getLatitude())) {
            throw new IllegalArgumentException("A pharmacy already exists at the provided location");
        }

        // Check if a pharmacy with the same name already exists
        if (pharmacyRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException("A pharmacy with the same name already exists");
        }

        // If the pharmacy doesn't exist, proceed with adding it
        pharmacyRepository.add(request);

        // Return a success message
        return "Pharmacy added successfully";
    }

    public PharmacyResponse getPharmacyById(Long id) {
        Object[] result = pharmacyRepository.getPharmacyById(id);
        if (result == null) {
            throw new NotFoundException("Pharmacy not found with id: " + id);
        }


        Long pharmacyId = ((Number) result[0]).longValue();
        String name = (String) result[1];
        Double longitude = ((Number) result[2]).doubleValue();
        Double latitude = ((Number) result[3]).doubleValue();
        String willaya = ((String) result[4]);
        String moughataa = ((String) result[5]);
        String img = ((String) result[6]);
        boolean isOpenTonight = (boolean) result[7];

        return PharmacyResponse.builder()
                .id(pharmacyId)
                .name(name)
                .longitude(longitude)
                .latitude(latitude)
                .willaya(willaya)
                .moughataa(moughataa)
                .img(img)
                .isOpenTonight(isOpenTonight)
                .build();
    }

    public void deletePharmacyById(Long id) {
        Object[] pharmacy = pharmacyRepository.getPharmacyById(id);
        if (pharmacy == null) {
            throw new NotFoundException("Pharmacy not found with id: " + id);
        }

        pharmacyRepository.deletePharmacyById(id);
    }

}
