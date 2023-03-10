package ru.fllcker.whynotes.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.fllcker.whynotes.dto.SignUpDto;
import ru.fllcker.whynotes.models.Token;
import ru.fllcker.whynotes.models.User;
import ru.fllcker.whynotes.repositories.ITokensRepository;
import ru.fllcker.whynotes.security.JwtAuthentication;
import ru.fllcker.whynotes.security.JwtRequest;
import ru.fllcker.whynotes.security.JwtResponse;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
    private final ITokensRepository tokensRepository;
    private final UsersService usersService;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder encoder;

    public JwtResponse login(JwtRequest jwtRequest) {
        User user = usersService.findByEmail(jwtRequest.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if (!encoder.matches(jwtRequest.getPassword(), user.getPasswordHashed()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong data");

        String accessToken = jwtProvider.generateToken(user, false);
        String refreshToken = jwtProvider.generateToken(user, true);
        tokensRepository.save(new Token(user, refreshToken));

        return new JwtResponse(accessToken, refreshToken);
    }

    public JwtResponse getTokensByRefresh(String refreshToken, boolean refresh) {
        String subject = jwtProvider.getRefreshClaims(refreshToken)
                .getSubject();

        if (!jwtProvider.validateRefreshToken(refreshToken))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        Token token = tokensRepository.findByToken(refreshToken)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN));

        if (!token.getToken().equals(refreshToken) || !token.getOwner().getEmail().equals(subject))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);


        User user = usersService.findByEmail(subject)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!"));

        String accessToken = jwtProvider.generateToken(user, false);
        String newRefreshToken = null;

        if (refresh) {
            newRefreshToken = jwtProvider.generateToken(user, true);
            tokensRepository.save(new Token(user, newRefreshToken));
        }

        return new JwtResponse(accessToken, newRefreshToken);
    }

    public JwtResponse signup(SignUpDto dto) {
        if (usersService.existsByEmail(dto.getEmail()).orElse(true))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with this email already exists!");

        User user = new User(dto.getEmail(), encoder.encode(dto.getPassword()), dto.getName());
        usersService.create(user);

        String accessToken = jwtProvider.generateToken(user, false);
        String refreshToken = jwtProvider.generateToken(user, true);
        tokensRepository.save(new Token(user, refreshToken));

        return new JwtResponse(accessToken, refreshToken);
    }

    public JwtAuthentication getAuthInfo() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }
}
