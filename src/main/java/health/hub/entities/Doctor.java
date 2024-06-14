package health.hub.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Map;


@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String speciality;

    @ElementCollection
    @CollectionTable(name = "schedule", joinColumns = @JoinColumn(name = "doctor_id"))
    @MapKeyColumn(name = "day")
    @Column(name = "hours")
    private Map<String, String> schedule;

    @ManyToOne
    @JoinColumn(name = "cabinet_id", nullable = false)
    @NotNull(message = "cabinet cannot be null")
    private Cabinet cabinet;
}
