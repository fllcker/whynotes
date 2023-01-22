package ru.fllcker.whynotes.utils;

import io.jsonwebtoken.Claims;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.fllcker.whynotes.security.JwtAuthentication;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtUtils { // todo
    public static JwtAuthentication generate(Claims claims) {
        final JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setEmail(claims.getSubject());
        return jwtInfoToken;
    }
}
