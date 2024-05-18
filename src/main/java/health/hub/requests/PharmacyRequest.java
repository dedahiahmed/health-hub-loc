package health.hub.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PharmacyRequest {
    @NotBlank(message = "name cannot be null or empty")
    private String name;
    @NotNull(message = "longitude cannot be null")
    private double longitude;
    @NotNull(message = "latitude cannot be null")
    private double latitude;
    private boolean isOpenTonight;
}
