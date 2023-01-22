package ru.fllcker.whynotes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fllcker.whynotes.models.User;

import java.util.Optional;

@Repository
public interface IUsersRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    Optional<Boolean> existsByEmail(String email);
}
