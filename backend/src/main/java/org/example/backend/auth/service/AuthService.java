package org.example.backend.auth.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.auth.dto.JwtDto;
import org.example.backend.auth.util.JwtTokenProvider;
import org.example.backend.base.exception.AlreadyExistException;
import org.example.backend.base.exception.DoNotExistException;
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

    public void join(UserDto dto) {
        if (userRepository.existsById(dto.getId())) {
            log.warn("Attempt to join with existing ID: {}", dto.getId());
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
                .orElseThrow(() -> new DoNotExistException("존재하지 않는 사용자입니다."));

        user.setLastLoginAt(System.currentTimeMillis());
        // 4. 토큰을 반환
        return jwtDto;
    }
}
