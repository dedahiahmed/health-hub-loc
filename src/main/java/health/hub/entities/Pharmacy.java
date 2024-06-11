package health.hub.entities;

import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Point;


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

    @Column(unique = true, nullable = false)
    private String name;

    @Column(unique = true, name = "location", columnDefinition = "geometry(Point,4326)", nullable = false)
    private Point location;

    @Column(nullable = false)
    private  String willaya;

    @Column(nullable = false)
    private String moughataa;

    @Column(name = "is_open_tonight")
    private boolean isOpenTonight;
}
