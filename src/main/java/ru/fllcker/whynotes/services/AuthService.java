package ru.fllcker.whynotes.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.fllcker.whynotes.dto.SignUpDto;
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

    public JwtResponse login(JwtRequest jwtRequest) {
        User user = usersService.findByEmail(jwtRequest.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if (!encoder.matches(jwtRequest.getPassword(), user.getPasswordHashed()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong data");

        String accessToken = jwtProvider.generateAccessToken(user);
        return new JwtResponse(accessToken);
    }

    public JwtResponse signup(SignUpDto dto) {
        if (usersService.existsByEmail(dto.getEmail()).orElse(true))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with this email already exists!");

        User user = new User(dto.getEmail(), encoder.encode(dto.getPassword()), dto.getName());
        usersService.create(user);

        String accessToken = jwtProvider.generateAccessToken(user);
        return new JwtResponse(accessToken);
    }

    public JwtAuthentication getAuthInfo() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }
}
