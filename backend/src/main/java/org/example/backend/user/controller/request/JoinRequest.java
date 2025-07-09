package org.example.backend.user.controller.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class JoinRequest {
    private String id;
    private String nickname;
    private String password;
}
