package health.hub.requests;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PharmacyRequest {
    private String name;
    private double longitude;
    private double latitude;
    private boolean isOpenTonight;
}
