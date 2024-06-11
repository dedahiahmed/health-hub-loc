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
    public User updateuser(int id,User updatedUser) {
        User existingUser = userRepository.getUser(id);

        if (existingUser == null) {
            throw new IllegalArgumentException("User not found");
        }
        // Update the fields of the existing user
        if (updatedUser.getFullName() != null) {
            existingUser.setFullName(updatedUser.getFullName());
        }
        if (updatedUser.getUsername() != null) {
            existingUser.setUsername(updatedUser.getUsername());
        }
        if (updatedUser.getPassword() != null) {
            existingUser.setPassword(updatedUser.getPassword());
        }
        if (updatedUser.getRole() != null) {
            existingUser.setRole(updatedUser.getRole());
        }

        // Save the updated user back to the database
        return userRepository.update(existingUser);
    }

    @Transactional
    public void delete(int id) {
        User user = getUser(id);
        if (user != null) {
            userRepository.delete(user);
        }
    }
}

