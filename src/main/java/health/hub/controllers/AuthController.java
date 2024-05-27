package health.hub.controllers;

import health.hub.entities.User;
import health.hub.services.AuthService;
import health.hub.services.UserService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/auth")
@RequestScoped
public class AuthController {
    @Inject
    UserService userService;
    @Inject
    private AuthService authService;

    @POST
    @Path("/register")
    public User register(User user) {
        return userService.add(user);

    }

    @POST
    @Path("/login")
    public Response login(JsonObject jsonObject) {
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");

        String token = authService.authenticate(username, password);

        if (token != null) {
            JsonObject jsonResponse = Json.createObjectBuilder()
                    .add("token", token)
                    .build();
            return Response.ok(jsonResponse).build();
        } else {
            JsonObject errorResponse = Json.createObjectBuilder()
                    .add("message", "Invalid credentials provided")
                    .build();
            return Response.status(Response.Status.FORBIDDEN)
                    .entity(errorResponse)
                    .build();
        }
    }


}
