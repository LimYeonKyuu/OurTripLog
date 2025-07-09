package org.example.backend.user.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.*;
import org.example.backend.base.entity.BaseEntity;
import org.example.backend.user.dto.UserDto;
import org.example.backend.user.entity.enums.Authority;

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

    private Long lastLoginAt;

    @Enumerated(value = EnumType.STRING)
    private Authority authority;

    public static User join(UserDto dto) {
        return User.builder()
                .id(dto.getId())
                .nickname(dto.getNickname())
                .password(dto.getPassword())
                .authority(Authority.USER)
                .lastLoginAt(System.currentTimeMillis())
                .build();
    }
}