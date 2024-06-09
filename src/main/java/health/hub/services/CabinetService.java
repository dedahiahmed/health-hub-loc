//package health.hub.services;
//import health.hub.repositories.CabinetRepository;
//import health.hub.requests.CabinetRequest;
//import health.hub.responses.CabinetResponse;
//import jakarta.ws.rs.NotFoundException;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Objects;
//
//public class CabinetService {
//    CabinetRepository cabinetRepository = new CabinetRepository();
//
//    public CabinetService() {
//        this.cabinetRepository = cabinetRepository;
//    }
//
//    public List<CabinetResponse> getAllCabinets() {
//        List<Object[]> results = cabinetRepository.getAll();
//        List<CabinetResponse> responses = new ArrayList<>();
//        for (Object[] row : results) {
//            Long id = ((Number) row[0]).longValue();
//            String nom = (String) row[1];
//            Double longitude = ((Number) row[2]).doubleValue();
//            Double latitude = ((Number) row[3]).doubleValue();
//
//            CabinetResponse response = CabinetResponse.builder()
//                    .id(id)
//                    .nom(nom)
//                    .longitude(longitude)
//                    .latitude(latitude)
//                    .build();
//
//            responses.add(response);
//        }
//        return responses;
//    }
//
//
//    public String addCabinet(CabinetRequest request) {
//        // Check if a cabinet already exists at the provided location
//        if (cabinetRepository.existsByLocation(request.getLongitude(), request.getLatitude())) {
//            throw new IllegalArgumentException("A cabinet already exists at the provided location");
//        }
//
//        // Check if a cabinet with the same name already exists
//        if (cabinetRepository.existsByName(request.getNom())) {
//            throw new IllegalArgumentException("A cabinet with the same name already exists");
//        }
//
//        // If the cabinet doesn't exist, proceed with adding it
//        cabinetRepository.add(request);
//
//        // Return a success message
//        return "Cabinet added successfully";
//    }
//    public void deleteCabinet(Long id) {
//        Object[] cabinet=cabinetRepository.getCabinetById(id);
//        if (cabinet == null) {
//            throw new NotFoundException("Cabinet not found with id: " + id);
//        }
//        cabinetRepository.deleteCabinetById(id);
//    }
//
//    public CabinetResponse getcabinetById(Long id) {
//        Object[] result = cabinetRepository.getCabinetById(id);
//        if (result == null) {
//            throw new NotFoundException("cabinet not found with id: " + id);
//        }
//
//        Long cabinetId = ((Number) result[0]).longValue();
//        String nom = (String) result[1];
//        Double longitude = ((Number) result[2]).doubleValue();
//        Double latitude = ((Number) result[3]).doubleValue();
//       // boolean isOpenTonight = (boolean) result[4];
//
//        return CabinetResponse.builder()
//                .id(cabinetId)
//                .nom(nom)
//                .longitude(longitude)
//                .latitude(latitude)
//                .build();
//    }
//}
package health.hub.services;

import health.hub.repositories.CabinetRepository;
import health.hub.requests.CabinetRequest;
import health.hub.responses.CabinetResponse;
import jakarta.ws.rs.NotFoundException;

import java.util.ArrayList;
import java.util.List;

public class CabinetService {
    CabinetRepository cabinetRepository;

    public CabinetService() {
        this.cabinetRepository = new CabinetRepository();
    }

    public List<CabinetResponse> getAllCabinets() {
        List<Object[]> results = cabinetRepository.getAll();
        List<CabinetResponse> responses = new ArrayList<>();
        for (Object[] row : results) {
            Long id = ((Number) row[0]).longValue();
            String nom = (String) row[1];
            Double longitude = ((Number) row[2]).doubleValue();
            Double latitude = ((Number) row[3]).doubleValue();
            String willaya = (String) row[4];
            String Moughataa = (String) row[5];

            CabinetResponse response = CabinetResponse.builder()
                    .id(id)
                    .nom(nom)
                    .longitude(longitude)
                    .latitude(latitude)
                    .willaya(willaya)
                    .Moughataa(Moughataa)
                    .build();

            responses.add(response);
        }
        return responses;
    }

    public String addCabinet(CabinetRequest request) {
        if (cabinetRepository.existsByLocation(request.getLongitude(), request.getLatitude())) {
            return "Cabinet already exists at this location";
        }
        if (cabinetRepository.existsByName(request.getNom())) {
            return "Cabinet with this name already exists";
        }
        return cabinetRepository.add(request);
    }

    public CabinetResponse getCabinetById(Long id) {
        Object[] result = cabinetRepository.getCabinetById(id);
        if (result == null) {
            throw new NotFoundException("Cabinet not found");
        }
        return CabinetResponse.builder()
                .id(((Number) result[0]).longValue())
                .nom((String) result[1])
                .longitude(((Number) result[2]).doubleValue())
                .latitude(((Number) result[3]).doubleValue())
                .willaya((String) result[4])
                .Moughataa((String) result[5])
                .build();
    }

    public String deleteCabinet(Long id) {
        cabinetRepository.deleteCabinetById(id);
        return "Cabinet deleted successfully";
    }

}
