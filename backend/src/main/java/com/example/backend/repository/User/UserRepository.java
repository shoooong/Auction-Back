package com.example.backend.repository.User;

import com.example.backend.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("SELECT u.password From Users u WHERE u.email = :email")
    String findPasswordByUserId(String email);

    boolean existsByUserId(Long userId);
}
