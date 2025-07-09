package org.example.backend.user.controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.user.controller.request.JoinRequest;
import org.example.backend.user.dto.UserDto;
import org.example.backend.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping ("/api/ourtriplog/auth/join")
    public ResponseEntity<Void> join(@RequestBody JoinRequest request) {
        userService.join(UserDto.from(request, passwordEncoder.encode(request.getPassword())));
        return ResponseEntity.ok().build();
    }
}
