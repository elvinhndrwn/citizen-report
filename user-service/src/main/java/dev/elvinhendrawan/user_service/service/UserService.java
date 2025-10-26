package dev.elvinhendrawan.user_service.service;

import dev.elvinhendrawan.user_service.dto.UserDTO;
import dev.elvinhendrawan.user_service.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    ResponseEntity<?> analyzeUsers(String domain);

    UserDTO createUser(UserDTO user);

    boolean existsByEmail(String email);
}
