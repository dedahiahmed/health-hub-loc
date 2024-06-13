package health.hub.responses;


import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Builder
@Data
@Getter
@Setter
public class DoctorResponse {
    private Long id;
    private String name;
    private String speciality;
    private String schedule; // Planning sous forme de chaîne JSON
    private Long cabinetId;

}
