package ru.fllcker.whynotes.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.fllcker.whynotes.models.User;
import ru.fllcker.whynotes.security.JwtAuthentication;
import ru.fllcker.whynotes.security.JwtRequest;
import ru.fllcker.whynotes.security.JwtResponse;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UsersService usersService;
    private final JwtProvider jwtProvider;

    public JwtResponse login(JwtRequest jwtRequest) throws Exception {
        User user = usersService.findByEmail(jwtRequest.getEmail())
                .orElseThrow(() -> new Exception("User not found!"));

        if (!user.getPasswordHashed().equals(jwtRequest.getPassword()))
            throw new Exception("Wrong data!");

        String accessToken = jwtProvider.generateAccessToken(user);
        return new JwtResponse(accessToken);
    }

    public JwtAuthentication getAuthInfo() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }
}
