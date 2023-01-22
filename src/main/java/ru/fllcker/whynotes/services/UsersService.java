package ru.fllcker.whynotes.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fllcker.whynotes.models.User;
import ru.fllcker.whynotes.repositories.IUsersRepository;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UsersService {
    private final IUsersRepository usersRepository;

    public Optional<User> findByEmail(String email) {
        return usersRepository.findByEmail(email);
    }

    public Optional<Boolean> existsByEmail(String email) {
        return usersRepository.existsByEmail(email);
    }

    public void create(User user) {
        usersRepository.save(user);
    }
}
