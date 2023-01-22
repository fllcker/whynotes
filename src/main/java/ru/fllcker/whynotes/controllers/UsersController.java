package ru.fllcker.whynotes.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ru.fllcker.whynotes.models.User;
import ru.fllcker.whynotes.security.JwtAuthentication;
import ru.fllcker.whynotes.services.AuthService;
import ru.fllcker.whynotes.services.UsersService;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/users")
public class UsersController {
    private final AuthService authService;
    private final UsersService usersService;

    @GetMapping("profile")
    public ResponseEntity<User> getProfile() {
        JwtAuthentication authentication = authService.getAuthInfo();
        User user = usersService.findByEmail(authentication.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!"));
        return ResponseEntity.ok(user);
    }
}
