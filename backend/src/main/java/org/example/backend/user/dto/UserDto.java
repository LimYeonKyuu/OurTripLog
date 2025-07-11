package org.example.backend.user.dto;

import lombok.Builder;
import lombok.Getter;
import org.example.backend.auth.controller.request.JoinRequest;
import org.example.backend.auth.controller.request.LoginRequest;

@Builder
@Getter
public class UserDto {
    private String id;

    private String nickname;

    private String password;

    private String profileImage;

    private String authority;

    public static UserDto from(JoinRequest request, String encodedPassword) {
        return UserDto.builder()
                .id(request.getId())
                .nickname(request.getNickname())
                .password(encodedPassword)
                .build();
    }

    public static UserDto from(LoginRequest request) {
        return UserDto.builder()
                .id(request.getId())
                .password(request.getPassword())
                .build();
    }
}
