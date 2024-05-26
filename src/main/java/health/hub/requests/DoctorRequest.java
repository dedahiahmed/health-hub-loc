package health.hub.requests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.Map;
@Builder
@Data
public class DoctorRequest {
    @NotEmpty(message = "Le nom ne doit pas être vide")
    private String name;

    @NotEmpty(message = "La spécialité ne doit pas être vide")
    private String speciality;

    @NotEmpty(message = "Le planning ne doit pas être vide")
    private String schedule; // Planning sous forme de chaîne JSON


    public Map<String, String> getScheduleMap() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(schedule, new TypeReference<Map<String, String>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Erreur de conversion de l'horaire en Map", e);
        }
    }
}
