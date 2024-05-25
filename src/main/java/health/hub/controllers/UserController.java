package health.hub.controllers;

import health.hub.entities.User;
import health.hub.services.UserService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/users")
public class UserController {
    private static final long serialVersionUID = 1L;

    public UserController() {
        super();
    }

    UserService userService = new UserService();

    @GET
    public List<User> getallUsers() {
        return userService.getallUsers();
    }

    @POST
    public User add(User user) {
        return userService.add(user);
    }
}
