package org.example.backend.auth.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.auth.dto.JwtDto;
import org.example.backend.auth.entity.RefreshToken;
import org.example.backend.auth.exception.UnauthorizedException;
import org.example.backend.auth.repository.RefreshTokenRepository;
import org.example.backend.auth.util.JwtTokenProvider;
import org.example.backend.base.exception.AlreadyExistException;
import org.example.backend.user.dto.UserDto;
import org.example.backend.user.entity.User;
import org.example.backend.user.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    public void signup(UserDto dto) {
        if (userRepository.existsById(dto.getId())) {
            log.warn("Attempt to signup with existing ID: {}", dto.getId());
            throw new AlreadyExistException("이미 존재하는 아이디입니다.");
        }
        userRepository.save(User.join(dto));
    }

    public JwtDto signIn(UserDto dto) {
        // 1. username + password 를 기반으로 Authentication 객체 생성
        // 이때 authentication 은 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(dto.getId(), dto.getPassword());

        // 2. 실제 검증. authenticate() 메서드를 통해 요청된 Member 에 대한 검증 진행
        // authenticate 메서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        JwtDto jwtDto = jwtTokenProvider.generateToken(authentication);
        log.info("User {} signed in successfully", dto.getId());

        User user = userRepository.findById(dto.getId())
                .orElseThrow(() -> new UnauthorizedException("존재하지 않는 사용자입니다."));

        user.setLastLoginAt(System.currentTimeMillis());

        // 4. Refresh Token 저장
        refreshTokenRepository.save(RefreshToken.builder()
                .userId(user.getId())
                .token(jwtDto.getRefreshToken())
                .build()
        );

        // 5. 토큰을 반환
        return jwtDto;
    }

    public JwtDto refreshToken(String refreshToken) {
        // 1. Refresh Token 검증
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            log.warn("Invalid refresh token: {}", refreshToken);
            throw new UnauthorizedException("유효하지 않은 리프레시 토큰입니다.");
        }

        // 2. Refresh Token에서 사용자 정보 추출
        String userId = jwtTokenProvider.getUserId(refreshToken);

        // 3. Refresh Token이 DB에 존재하는지 확인
        RefreshToken existingToken = refreshTokenRepository.findByUserIdAndToken(userId, refreshToken)
                .orElseThrow(() -> {
                    log.warn("Refresh token not found for user: {}", userId);
                    return new UnauthorizedException("존재하지 않는 리프레시 토큰입니다.");
                });

        // 3. db에서 사용자 정보 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.warn("User not found for ID: {}", userId);
                    return new UnauthorizedException("존재하지 않는 사용자입니다.");
                });

        // 4. 새로운 JWT 토큰 생성
        JwtDto jwtDto = jwtTokenProvider.generateToken(user.getId(), user.getAuthority().name());

        // 4. 새로운 Refresh Token 저장
        existingToken.update(jwtDto.getRefreshToken());

        return jwtDto;
    }
}
