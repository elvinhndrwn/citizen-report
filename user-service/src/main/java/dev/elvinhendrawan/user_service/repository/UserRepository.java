package dev.elvinhendrawan.user_service.repository;

import dev.elvinhendrawan.user_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);
}
