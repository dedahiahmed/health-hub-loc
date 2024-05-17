package health.hub.services;

import health.hub.repositories.PharmacyRepository;
import health.hub.requests.PharmacyRequest;
import health.hub.responses.PharmacyResponse;

import java.util.ArrayList;
import java.util.List;

public class PharmacyService {
    PharmacyRepository pharmacyRepository = new PharmacyRepository();

    public PharmacyService() {
        this.pharmacyRepository = pharmacyRepository;
    }

    public List<PharmacyResponse> getAllPharmacies() {
        List<Object[]> results = pharmacyRepository.getAll();
        List<PharmacyResponse> responses = new ArrayList<>();
        for (Object[] row : results) {
            Long id = ((Number) row[0]).longValue();
            String name = (String) row[1];
            Double longitude = ((Number) row[2]).doubleValue();
            Double latitude = ((Number) row[3]).doubleValue();
            boolean isOpenTonight = (boolean) row[4];

            PharmacyResponse response = PharmacyResponse.builder()
                    .id(id)
                    .name(name)
                    .longitude(longitude)
                    .latitude(latitude)
                    .isOpenTonight(isOpenTonight)
                    .build();

            responses.add(response);
        }
        return responses;
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


}
