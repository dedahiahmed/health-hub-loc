package health.hub.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cabinet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nom cannot be null or empty")
    private String nom;

    @NotBlank(message = "Willaya cannot be null or empty")
    private String willaya;

    @NotBlank(message = "Moughataa cannot be null or empty")
    private String moughataa;

    @NotNull(message = "Longitude cannot be null")
    private Double longitude;

    @NotNull(message = "Latitude cannot be null")
    private Double latitude;
}
