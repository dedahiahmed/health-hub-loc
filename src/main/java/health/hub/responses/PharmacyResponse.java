package health.hub.responses;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PharmacyResponse {
    private Long id;
    private String name;
    private Double longitude;
    private Double latitude;
    private  String willaya;
    private String moughataa;
    private boolean isOpenTonight;
}
