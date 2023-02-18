package ru.fllcker.whynotes.security;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshRequest {
    public String refreshToken;
}
