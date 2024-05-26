package health.hub.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CabinetRequest {
    @NotBlank(message = "nom cannot be null or empty")
    private String nom;
    @NotNull(message = "longitude cannot be null")
    private double longitude;
    @NotNull(message = "latitude cannot be null")
    private double latitude;
}
