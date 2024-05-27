package health.hub.requests;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class AuthRequest {
    @NotBlank(message = "username must be provided")
    @JsonbProperty("username")
    private String username;
    @NotBlank(message = "password must be provided")
    @JsonbProperty("password")
    private String password;
}
