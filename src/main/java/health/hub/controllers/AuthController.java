package health.hub.controllers;

import health.hub.entities.User;
import health.hub.services.AuthService;
import health.hub.services.UserService;
import io.jsonwebtoken.Claims;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Date;

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

    @GET
    @Path("/user-me")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserClaims(@HeaderParam("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        String token = authHeader.substring(7);

        try {
            Claims claims = AuthService.getClaimsFromToken(token);
            if (claims != null) {
                // Check if the token is expired
                Date expirationDate = claims.getExpiration();
                if (expirationDate != null && expirationDate.before(new Date())) {
                    return Response.status(Response.Status.UNAUTHORIZED).entity("Token expired").build();
                }

                String username = claims.getSubject();
                String role = (String) claims.get("role");

                // Create a JSON object with the extracted claims
                JsonObject json = Json.createObjectBuilder()
                        .add("username", username)
                        .add("role", role)
                        .build();

                return Response.ok(json).build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
}
