package org.example.backend.auth.controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.auth.controller.request.JoinRequest;
import org.example.backend.auth.controller.request.LoginRequest;
import org.example.backend.auth.controller.response.JwtResponse;
import org.example.backend.auth.service.AuthService;
import org.example.backend.user.dto.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;


    @PostMapping("/api/ourtriplog/auth/join")
    public ResponseEntity<Void> join(@RequestBody JoinRequest request) {
        authService.join(UserDto.from(request, passwordEncoder.encode(request.getPassword())));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/ourtriplog/auth/signin")
    public ResponseEntity<JwtResponse> signIn(@RequestBody LoginRequest request) {
        JwtResponse jwtResponse = JwtResponse.from(authService.signIn(UserDto.from(request)));
        return ResponseEntity.ok(jwtResponse);
    }
}
