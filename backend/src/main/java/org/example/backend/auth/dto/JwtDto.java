package org.example.backend.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class JwtDto {
    private String accessToken;
    private String refreshToken;
}
