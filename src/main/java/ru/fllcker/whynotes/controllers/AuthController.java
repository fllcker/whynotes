package ru.fllcker.whynotes.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.fllcker.whynotes.security.JwtRequest;
import ru.fllcker.whynotes.security.JwtResponse;
import ru.fllcker.whynotes.services.AuthService;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest jwtRequest) throws Exception {
        JwtResponse tokens = authService.login(jwtRequest);
        return ResponseEntity.ok(tokens);
    }
}
