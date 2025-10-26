package dev.elvinhendrawan.user_service.service;

import dev.elvinhendrawan.user_service.dto.UserDTO;
import dev.elvinhendrawan.user_service.model.User;
import dev.elvinhendrawan.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    @Override
    public ResponseEntity<?> analyzeUsers(String domain) {
        List<User> users = userRepository.findAll();

        // Grouping jumlah user per domain email
        Map<String, Long> grouped = users.stream()
                .filter(u -> u.getEmail() != null && u.getEmail().contains("@"))
                .collect(Collectors.groupingBy(
                        u -> u.getEmail().substring(u.getEmail().indexOf("@") + 1),
                        Collectors.counting()
                ));

        return ResponseEntity.ok(Map.of(
                "groupedCount", grouped
        ));
    }

    @Override
    public UserDTO createUser(UserDTO request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setActive(Boolean.TRUE);
        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);
        return new UserDTO(user.getName(), user.getEmail());
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}