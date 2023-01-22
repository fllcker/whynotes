package ru.fllcker.whynotes.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder encoder;

    public JwtResponse login(JwtRequest jwtRequest) throws Exception {
        User user = usersService.findByEmail(jwtRequest.getEmail())
                .orElseThrow(() -> new Exception("User not found!"));

        if (!encoder.matches(jwtRequest.getPassword(), user.getPasswordHashed()))
            throw new Exception("Wrong data!");

        String accessToken = jwtProvider.generateAccessToken(user);
        return new JwtResponse(accessToken);
    }

    public JwtResponse signup(JwtRequest jwtRequest) throws Exception {
        if (usersService.existsByEmail(jwtRequest.getEmail()).orElse(true))
            throw new Exception("User with this email already exists!");

        User user = new User(jwtRequest.getEmail(), encoder.encode(jwtRequest.getPassword()));
        usersService.create(user);

        String accessToken = jwtProvider.generateAccessToken(user);
        return new JwtResponse(accessToken);
    }

    public JwtAuthentication getAuthInfo() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }
}
