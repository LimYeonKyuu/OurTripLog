package org.example.backend.auth.controller.response;

import lombok.Builder;
import lombok.Getter;
import org.example.backend.auth.dto.JwtDto;

@Getter
@Builder
public class JwtResponse {
    private String accessToken;
    private String refreshToken;

    public static JwtResponse from(JwtDto jwtDto) {
        return JwtResponse.builder()
                .accessToken(jwtDto.getAccessToken())
                .refreshToken(jwtDto.getRefreshToken())
                .build();
    }
}
