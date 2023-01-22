package ru.fllcker.whynotes.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.fllcker.whynotes.security.JwtAuthentication;
import ru.fllcker.whynotes.services.AuthService;

@RestController
@RequestMapping("api/test")
@RequiredArgsConstructor
public class TestController {
    private final AuthService authService;

    @GetMapping("test")
    public ResponseEntity<String> test() {
        JwtAuthentication info = authService.getAuthInfo();
        return ResponseEntity.ok("Hello " + info.getPrincipal());
    }
}
