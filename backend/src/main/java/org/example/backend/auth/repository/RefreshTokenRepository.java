package org.example.backend.auth.repository;

import org.example.backend.auth.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {

    Optional<RefreshToken> findByUserIdAndToken(String name, String refreshToken);
}
