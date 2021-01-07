package foodwarehouse.core.service;

import foodwarehouse.core.data.user.Permission;
import foodwarehouse.core.data.user.User;
import foodwarehouse.core.data.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public List<User> findAllUsers() {
        return userRepository.findAllUsers();
    }

    public Optional<User> findUserById(int userId) {
        return userRepository.findUserById(userId);
    }

    public Optional<User> createUser(String username, String password, String email, Permission permission) {
        String encryptedPassword = bCryptPasswordEncoder.encode(password);
        return userRepository.createUser(username, encryptedPassword, email, permission);
    }

    public Optional<User> updateUser(int userId, String username, String password, String email, Permission permission) {
        String encryptedPassword = bCryptPasswordEncoder.encode(password);
        return userRepository.updateUser(userId, username, encryptedPassword, email, permission);
    }

    public boolean deleteUser(int userId) {
        return userRepository.deleteUser(userId);
    }

    public Optional<User> findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }
}
