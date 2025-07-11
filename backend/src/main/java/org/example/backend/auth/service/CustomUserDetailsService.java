package org.example.backend.auth.service;

import lombok.RequiredArgsConstructor;

import org.example.backend.base.exception.DoNotExistException;
import org.example.backend.user.entity.User;
import org.example.backend.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String id) {
        return userRepository.findById(id)
                .map(this::createUserDetails)
                .orElseThrow(() -> new DoNotExistException("존재하지 않는 사용자입니다."));
    }

    private UserDetails createUserDetails(User user) {
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getId())
                .password(user.getPassword())
                .roles(new String[] {user.getAuthority().name()})
                .build();
    }
}
