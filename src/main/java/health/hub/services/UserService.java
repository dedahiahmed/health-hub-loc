package health.hub.services;

import health.hub.entities.User;
import health.hub.repositories.UserRepository;

import java.util.List;

public class UserService {
    UserRepository userRepository = new UserRepository();

    public List<User> getallUsers() {
        return userRepository.getAll();
    }

    public User add(User user) {
        return userRepository.add(user);
    }
}
