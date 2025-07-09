package org.example.backend.user.dto;

import lombok.Builder;
import lombok.Getter;
import org.example.backend.user.controller.request.JoinRequest;

@Builder
@Getter
public class UserDto {
    private String id;

    private String nickname;

    private String password;

    private String email;

    private String phoneNumber;

    private String profileImage;

    private String introduction;

    private String role;

    public static UserDto from(JoinRequest request, String encodedPassword) {
        return UserDto.builder()
                .id(request.getId())
                .nickname(request.getNickname())
                .password(encodedPassword)
                .build();
    }
}
