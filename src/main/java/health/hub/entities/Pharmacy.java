package health.hub.entities;

import health.hub.Enums.Moughataa;
import health.hub.Enums.Wilaya;
import health.hub.exceptions.InvalidEnumValueException;
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
    @Column(nullable = false)
    private String name;

    @NotNull(message = "longitude cannot be null")
    @Column(nullable = false)
    private double longitude;

    @NotNull(message = "latitude cannot be null")
    @Column(nullable = false)
    private double latitude;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Willaya cannot be null")
    @Column(nullable = false)
    private Wilaya willaya;

    @NotNull(message = "Moughataa cannot be null")
    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "moughataa", nullable = false)
    private Moughataa moughataa;

    // @NotBlank(message = "image cannot be null or empty")
    // @Column(nullable = false)
    private String img;

    @Column(name = "is_open_tonight")
    private boolean isOpenTonight;

    public void setWillaya(String willaya) {
        try {
            this.willaya = Wilaya.valueOf(willaya.replaceAll("-", "_"));
        } catch (IllegalArgumentException ex) {
            throw new InvalidEnumValueException("Wilaya", willaya);
        }
    }

    // Setter for Moughataa enum with validation
    public void setMoughataa(String moughataa) {
        try {
            this.moughataa = Moughataa.valueOf(moughataa.replaceAll("-", "_"));
        } catch (IllegalArgumentException ex) {
            throw new InvalidEnumValueException("Moughataa", moughataa);
        }
    }
}
