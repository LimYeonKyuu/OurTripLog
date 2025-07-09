package org.example.backend.user.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.*;
import org.example.backend.base.entity.BaseEntity;
import org.example.backend.user.dto.UserDto;
import org.example.backend.user.entity.enums.UserRole;

import java.time.LocalDateTime;

@Entity(name = "users")
@Builder
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class User extends BaseEntity {
    @Id
    private String id;

    private String nickname;

    private String password;

    private String profileImage;

    private LocalDateTime lastLoginTime;

    @Enumerated(value = EnumType.STRING)
    private UserRole role;

    public static User join(UserDto dto) {
        return User.builder()
                .id(dto.getId())
                .nickname(dto.getNickname())
                .password(dto.getPassword())
                .role(UserRole.USER)
                .build();
    }
}