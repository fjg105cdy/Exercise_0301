package com.yian.banking_service_exercise_01.repositories;

import com.yian.banking_service_exercise_01.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);

    Page<User> findByUsernameContainingIgnoreCase(String username , Pageable pageable);

    String email(String email);
}
