package health.hub.controllers;

import health.hub.Secutity.AuthService;
import health.hub.entities.User;
import health.hub.services.UserService;
import jakarta.annotation.security.RolesAllowed;
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

    @GET
    @Path("/all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @Path("/recherche/{id}")
    @GET
    //@PermitAll
    public User getUser(@PathParam("id") int id){
        return userService.getUser(id);
    }

    @Path("/{username}")
    @GET
    public User findByUsername(@PathParam("username") String username){
        return userService.findByUsername(username);
    }

    @POST// Seulement accessible par les administrateurs
    public User add(User user) {
        return userService.add(user);
    }

    @POST
    @Path("/register")
    public User register(User user) {
        return userService.add(user);
    }

    @PUT
    public Response update(User user){
        userService.update(user);
        return Response.ok(user).build();
    }

    @Path("/{id}")
    @DELETE
    public void delete(@PathParam("id") int id){
        userService.delete(id);
    }

//    @POST
//    @Path("/login")
//    @PermitAll
//    @Transactional
//    public Response login() {
//        String username = "Aly";
//        String password = "1234";
//        System.out.println("Attempting login for user: " + username);
//
//        String token = authService.authenticate(username, password);
//
//        if (token != null) {
//            System.out.println("Login successful, returning token");
//            System.out.println("Bearer "+token);
//            JsonObject jsonResponse = Json.createObjectBuilder()
//                    .add("token", token)
//                    .build();
//            //return "Bearer " + token;
//            return Response.ok(jsonResponse)
//                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
//                    .type(MediaType.APPLICATION_JSON)
//                    .build();
//        } else {
//            //return "no Autorize";
//            System.out.println("Login failed, returning 401");
//            return Response.status(Response.Status.UNAUTHORIZED)
//                    .entity("Authentication failed")
//                    .build();
//        }
//    }

}
