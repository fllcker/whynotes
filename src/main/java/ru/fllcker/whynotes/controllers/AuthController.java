package ru.fllcker.whynotes.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ru.fllcker.whynotes.dto.SignUpDto;
import ru.fllcker.whynotes.security.JwtRequest;
import ru.fllcker.whynotes.security.JwtResponse;
import ru.fllcker.whynotes.security.RefreshRequest;
import ru.fllcker.whynotes.services.AuthService;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Controller for auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("login")
    @Operation(summary = "login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest jwtRequest) {
        JwtResponse tokens = authService.login(jwtRequest);
        return ResponseEntity.ok(tokens);
    }

    @PostMapping("signup")
    @Operation(summary = "signup")
    public ResponseEntity<JwtResponse> signup(@RequestBody @Valid SignUpDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bindingResult.getAllErrors().get(0).getDefaultMessage());
        JwtResponse tokens = authService.signup(dto);
        return ResponseEntity.ok(tokens);
    }

    @PostMapping("access")
    public ResponseEntity<JwtResponse> getAccessToken(@RequestBody RefreshRequest request) {
        JwtResponse tokens = authService.getTokensByRefresh(request.getRefreshToken(), false);
        return ResponseEntity.ok(tokens);
    }

    @PostMapping("refresh")
    public ResponseEntity<JwtResponse> getRefreshToken(@RequestBody RefreshRequest request) {
        JwtResponse tokens = authService.getTokensByRefresh(request.getRefreshToken(), true);
        return ResponseEntity.ok(tokens);
    }
}
