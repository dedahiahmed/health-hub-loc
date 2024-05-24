package health.hub.services;
import health.hub.repositories.CabinetRepository;
import health.hub.requests.CabinetRequest;
import health.hub.responses.CabinetResponse;
import jakarta.ws.rs.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CabinetService {
    CabinetRepository cabinetRepository = new CabinetRepository();

    public CabinetService() {
        this.cabinetRepository = cabinetRepository;
    }

    public List<CabinetResponse> getAllCabinets() {
        List<Object[]> results = cabinetRepository.getAll();
        List<CabinetResponse> responses = new ArrayList<>();
        for (Object[] row : results) {
            Long id = ((Number) row[0]).longValue();
            String nom = (String) row[1];
            Double longitude = ((Number) row[2]).doubleValue();
            Double latitude = ((Number) row[3]).doubleValue();

            CabinetResponse response = CabinetResponse.builder()
                    .id(id)
                    .nom(nom)
                    .longitude(longitude)
                    .latitude(latitude)
                    .build();

            responses.add(response);
        }
        return responses;
    }


    public String addCabinet(CabinetRequest request) {
        // Check if a cabinet already exists at the provided location
        if (cabinetRepository.existsByLocation(request.getLongitude(), request.getLatitude())) {
            throw new IllegalArgumentException("A cabinet already exists at the provided location");
        }

        // Check if a cabinet with the same name already exists
        if (cabinetRepository.existsByName(request.getNom())) {
            throw new IllegalArgumentException("A cabinet with the same name already exists");
        }

        // If the cabinet doesn't exist, proceed with adding it
        cabinetRepository.add(request);

        // Return a success message
        return "Cabinet added successfully";
    }
    public void deleteCabinet(Long id) {
        Object[] cabinet=cabinetRepository.getCabinetById(id);
        if (cabinet == null) {
            throw new NotFoundException("Cabinet not found with id: " + id);
        }
        cabinetRepository.deleteCabinetById(id);
    }
}
