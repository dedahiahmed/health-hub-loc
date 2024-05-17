package health.hub.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PharmacyRequest {
    @NotBlank(message = "name cannot be null or empty")
    private String name;
    private double longitude;
    private double latitude;
    private boolean isOpenTonight;
}
