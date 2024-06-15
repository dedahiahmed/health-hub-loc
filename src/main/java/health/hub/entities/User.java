package health.hub.entities;

import health.hub.Enums.Role;
import health.hub.services.PasswordService;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "_user")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Basic
    @Column(name = "fullName", nullable = false)
    private String fullName;

    @Basic
    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Basic
    @Column(name = "password", nullable = false)
    private String password;

    //    @Basic
//    @Column(name = "created_at", nullable = false, updatable = false)
//    @CreationTimestamp
//    private Date createdAt;
    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    public void setPassword(String plainTextPassword) {
        // Utiliser le service PasswordService pour crypter le mot de passe
        this.password = PasswordService.hashPassword(plainTextPassword);
        //PasswordService.hashPassword(plainTextPassword);
    }
}
