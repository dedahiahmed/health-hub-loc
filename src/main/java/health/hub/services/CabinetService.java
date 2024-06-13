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

import health.hub.entities.Cabinet;
import health.hub.repositories.CabinetRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

import java.util.List;
import java.util.Optional;

public class CabinetService {
    @Inject
    CabinetRepository cabinetRepository;


    public List<Cabinet> getAllCabinets() {
        return cabinetRepository.getAll();

    }

    public Cabinet addCabinet(Cabinet request) {
        if (cabinetRepository.existsByLocation(request.getLongitude(), request.getLatitude())) {
            throw new IllegalArgumentException("Cabinet already exists at this location");
        }
        if (cabinetRepository.existsByName(request.getNom())) {
            throw new IllegalArgumentException("Cabinet with this name already exists\"");
        }
        return cabinetRepository.add(request);
    }

    public Cabinet getCabinetByID(Long id) {
        Cabinet result = cabinetRepository.getCabinet(id);
        if (result == null) {
            throw new NotFoundException("Cabinet not found with id: " + id);
        }

        return result;
    }

    public void delete(Long id) {
        Cabinet cabinet = cabinetRepository.getCabinet(id);
        if (cabinet == null) {
            throw new NotFoundException("cabinet not found with id: " + id);
        }

        cabinetRepository.delete(cabinet);
    }

    public Cabinet updateCabinet(Long id, Cabinet cabinet) {
        Optional<Cabinet> existingCabinetOptional = cabinetRepository.findById(id);
        if (existingCabinetOptional.isPresent()) {
            Cabinet existingCabinet = existingCabinetOptional.get();
            if (cabinet.getNom() != null) {
                existingCabinet.setNom(cabinet.getNom());
            }
            if (cabinet.getWillaya() != null) {
                existingCabinet.setWillaya(cabinet.getWillaya());
            }
            if (cabinet.getMoughataa() != null) {
                existingCabinet.setMoughataa(cabinet.getMoughataa());
            }
            if (cabinet.getDoctors() != null) {
                existingCabinet.setDoctors(cabinet.getDoctors());
            }

            return cabinetRepository.update(existingCabinet);
        } else {
            throw new IllegalArgumentException("Cabinet with ID " + id + " not found");
        }
    }


}
