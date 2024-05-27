package health.hub.controllers;

import health.hub.entities.User;
import health.hub.services.AuthService;
import health.hub.services.UserService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/users")
@RequestScoped
public class UserController {

    @Inject
    UserService userService;
    @Inject
    private AuthService authService;

    @GET
    @Path("/all")

    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @Path("/recherche/{id}")
    @GET
    //@PermitAll
    public User getUser(@PathParam("id") int id) {
        return userService.getUser(id);
    }

    @Path("/{username}")
    @GET
    public User findByUsername(@PathParam("username") String username) {
        return userService.findByUsername(username);
    }

    @POST// Seulement accessible par les administrateurs
    public User add(User user) {
        return userService.add(user);
    }
    

    @PUT
    public Response update(User user) {
        userService.update(user);
        return Response.ok(user).build();
    }

    @Path("/{id}")
    @DELETE
    public void delete(@PathParam("id") int id) {
        userService.delete(id);
    }


}
