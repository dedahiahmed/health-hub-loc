package health.hub.responses;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CabinetResponse {
    private Long id;
    private String nom;
    private Double longitude;
    private Double latitude;
}
