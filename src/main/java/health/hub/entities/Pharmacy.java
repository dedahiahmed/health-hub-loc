package health.hub.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "pharmacy")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pharmacy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "name cannot be null or empty")
    @Column(unique = true, nullable = false)
    private String name;

    @NotNull(message = "longitude cannot be null")
    @Column(nullable = false)
    private double longitude;

    @NotNull(message = "latitude cannot be null")
    @Column(nullable = false)
    private double latitude;

    @NotBlank(message = "willaya cannot be null or empty")
    @Column(nullable = false)
    private String willaya;

    @NotBlank(message = "moughataa cannot be null or empty")
    @Column(nullable = false)
    private String moughataa;

    // @NotBlank(message = "image cannot be null or empty")
    // @Column(nullable = false)
    private String img;

    @Column(name = "is_open_tonight")
    private boolean isOpenTonight;
}
