package org.example.backend.user.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.backend.base.exception.AlreadyExistException;
import org.example.backend.user.dto.UserDto;
import org.example.backend.user.entity.User;
import org.example.backend.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void join(UserDto dto) {
        if (userRepository.existsById(dto.getId())) {
            throw new AlreadyExistException("이미 존재하는 아이디입니다.");
        }
        userRepository.save(User.join(dto));
    }
}
