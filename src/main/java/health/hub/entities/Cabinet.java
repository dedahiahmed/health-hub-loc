package health.hub.entities;

import health.hub.Enums.Moughataa;
import health.hub.Enums.Wilaya;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Willaya cannot be null")
    @Column(nullable = false)
    private Wilaya willaya;

    @NotNull(message = "Moughataa cannot be null")
    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "moughataa", nullable = false)
    private Moughataa moughataa;

    @NotNull(message = "Longitude cannot be null")
    private Double longitude;

    @NotNull(message = "Latitude cannot be null")
    private Double latitude;

    @OneToMany(mappedBy = "cabinet", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonbTransient
    private List<Doctor> doctors;
}
