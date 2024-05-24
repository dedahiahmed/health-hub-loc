package health.hub.services;



import health.hub.entities.User;
import health.hub.repositories.UserRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

public class UserService {

    @Inject
    UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.getAll();
    }

    public User getUser(int id) {
        return userRepository.getUser(id);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional
    public User add(User user) {
        return userRepository.add(user);
    }

    @Transactional
    public User update(User user) {
        return userRepository.update(user);
    }

    @Transactional
    public void delete(int id) {
        User user = getUser(id);
        if (user != null) {
            userRepository.delete(user);
        }
    }
}

